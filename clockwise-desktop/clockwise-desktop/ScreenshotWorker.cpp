#include "stdafx.h"
#include "ScreenshotWorker.h"
#include "Enviroment.h"
#include "StringUtility.h"
#include "ScreenCapturer.h"
#include "Logger.h"
#include "Config.h"
#include "ServiceCommunicator.h"
#include "Authentication.h"
#include "User.h"
#include <algorithm>
#include <fstream>
#include <cpprest\oauth2.h>
#include "Base64.h"
#include "FileListItem.h"
#include <Windows.h>
#include <vector>

ScreenshotWorker::ScreenshotWorker(ServiceCommunicator& WorkerCommunicator) : Communicator(WorkerCommunicator)
{
	loadScreenshotBackup();
}

std::wstring ScreenshotWorker::getLastScreenshotFilename()
{
	return ScreenshotFilename;
}

void ScreenshotWorker::uploadScreenshots()
{
	try
	{
		uploadSingleScreenshot(ScreenshotFilename);

		for (auto& Iter = std::begin(CachedScreenshotsNames); Iter != std::end(CachedScreenshotsNames);)
		{
			uploadSingleScreenshot(*Iter);
			Iter = CachedScreenshotsNames.erase(Iter);
		}

		removeOldScreenshots();
	}
	catch (const std::exception& e)
	{
		std::fstream backupStream(getScreenshotsBackupPath(), std::ios::out | std::ios::trunc);
		if (!backupStream.is_open())
		{
			LOG_ERROR("Couldn't open backup file!");
			return;
		}

		backupStream << Cryptography::encode(std::to_string(Communicator.getAuthentication()->getUser()->getUserId())) << std::endl;
		auto PathLen = getScreenshotsDirectoryPath().length();

		for (auto& Filename : CachedScreenshotsNames)
		{
			auto& FilenameOnly = Filename.substr(PathLen, Filename.length() - PathLen);
			backupStream << Cryptography::encode(StringUtility::ws2s(FilenameOnly)) << std::endl;
		}
	}
}

void ScreenshotWorker::uploadSingleScreenshot(const std::wstring& Filename)
{
	std::ifstream file(Filename, std::ios::binary | std::ios::ate);
	std::streamsize size = file.tellg();
	file.seekg(0, std::ios::beg);

	if (size == -1)
	{
		return;
	}

	std::vector<char> buffer(size);
	if (!file.read(buffer.data(), size))
	{
		return;
	}

	web::json::value Screenshot;
	Screenshot[L"date"] = web::json::value::string(StringUtility::getDateString());
	Screenshot[L"base64Data"] = web::json::value::string(StringUtility::s2ws(Base64::encode((BYTE*)&buffer[0], buffer.size()).c_str()));

	auto userId = std::to_string(Communicator.getAuthentication()->getUser()->getUserId());

	try
	{
		Communicator.post("api/screenshots", Screenshot);
	}
	catch (const std::exception& e)
	{
		CachedScreenshotsNames.push_back(Filename);
		throw e;
	}
}

std::wstring ScreenshotWorker::getUserConfigPath()
{
	return Enviroment::getAppDataPath() + UserConfigFilename;
}

std::wstring ScreenshotWorker::getScreenshotsBackupPath()
{
	auto UserId = Communicator.getAuthentication()->getUser()->getUserId();
	return Enviroment::getAppDataPath() + L"/backupScreenshots" + std::to_wstring(UserId) + L".clk";
}

void ScreenshotWorker::removeOldScreenshots()
{
	//TODO: Extract partially to external class
	Config UserConfig(getUserConfigPath());
	auto RemoveFilesAfterDaysThreshold = UserConfig.getBool("EnabledAutoRemovalAfterDays");
	auto RemoveFilesAfterSizeThreshold = UserConfig.getBool("EnabledAutoRemovalAfterSize");

	if (!RemoveFilesAfterDaysThreshold && !RemoveFilesAfterSizeThreshold)
	{
		return;
	}

	auto ScreenshotsFiles = Enviroment::getFilesInDirectory(StringUtility::ws2s(getScreenshotsDirectoryPath()));
	auto DaysRemoveThreshold = UserConfig.getInt("AutoRemovalScreenshotDays");

	if (RemoveFilesAfterDaysThreshold)
	{
		for (auto& File : ScreenshotsFiles)
		{

			auto TimeDifference = Enviroment::compareFileTime(File.CreationDate, Enviroment::convertToFileTime(Enviroment::getCurrentSystemTime()));
			TimeDifference = TimeDifference / 10000 / 1000 / 60 / 60 / 24; // Nanoseconds -> Miliseconds -> Seconds -> Minutes -> Hours -> Days

			if (abs(TimeDifference) > DaysRemoveThreshold)
			{
				auto FilenameWithExtension = StringUtility::ws2s(getScreenshotsDirectoryPath()) + File.Filename + File.Extension;
				auto Result = remove(FilenameWithExtension.c_str());

				if (Result != 0)
				{
					LOG_ERROR("Error removing screenshot: " + FilenameWithExtension + ", older than: " + std::to_string(TimeDifference) + " days");
				}
			}
		}
	}	

	if (!RemoveFilesAfterSizeThreshold)
	{
		return;
	}

	unsigned int SizeInKiloBytes = 0;

	for (auto& File : ScreenshotsFiles)
	{
		SizeInKiloBytes += File.Size;
	}

	std::sort(std::begin(ScreenshotsFiles), std::end(ScreenshotsFiles), [](const FileListItem& Left, const FileListItem& Right)
	{
		return Enviroment::compareFileTime(Left.CreationDate, Right.CreationDate) == 0;
	});

	auto SizeRemoveThreshold = UserConfig.getInt("AutoRemovalScreenshotSize");
	if (SizeInKiloBytes / 1024 > SizeRemoveThreshold)
	{
		for (auto& File : ScreenshotsFiles)
		{
			auto FilenameWithExtension = StringUtility::ws2s(getScreenshotsDirectoryPath()) + File.Filename + File.Extension;
			auto Result = remove(FilenameWithExtension.c_str());

			if (Result != 0)
			{
				LOG_ERROR("Error removing screenshot: " + FilenameWithExtension);
			}
			else
			{
				SizeInKiloBytes -= File.Size;
				if (SizeInKiloBytes / 1024 < SizeRemoveThreshold)
				{
					break;
				}
			}
		}
	}
}

void ScreenshotWorker::captureScreenshot()
{
	auto ScreenshotFilepath = getScreenshotsDirectoryPath();
	auto ScreenshotFilenameOnly = L"screenshot" + StringUtility::getDateString() + L".jpg";

	std::replace(ScreenshotFilenameOnly.begin(), ScreenshotFilenameOnly.end(), ':', '-'); // ':' are invalid characters in win32
	Enviroment::createPath(ScreenshotFilepath);
	ScreenshotFilename = ScreenshotFilepath + ScreenshotFilenameOnly;

	ScreenCapturer::captureScreenAndSave(ScreenshotFilename);
}

std::wstring ScreenshotWorker::getScreenshotsDirectoryPath()
{
	return Enviroment::getAppDataPath() + L"images\\";
}

void ScreenshotWorker::createNewScreenshotInterval()
{
	ScreenshotInterval = 8 + rand() % 5;
}

unsigned int ScreenshotWorker::getScreenshotInterval()
{
	return ScreenshotInterval;
}

void ScreenshotWorker::loadScreenshotBackup()
{
	//TODO: Make one with Statistics worker
	std::fstream backupStream(getScreenshotsBackupPath(), std::ios::in);
	if (!backupStream.is_open())
	{
		LOG("Cannot open or find backup screenshot file. This is fine if no backup was done.");
		return;
	}

	std::string Line;
	int UserId = -1;
	int CurrentUserId = Communicator.getAuthentication()->getUser()->getUserId();

	while (std::getline(backupStream, Line))
	{
		if (UserId == -1)
		{
			try
			{
				UserId = std::stoi(Cryptography::decode(Line));
				if (CurrentUserId != UserId)
				{
					LOG_ERROR("Tried open wrong user backup data! File user: " + std::to_string(UserId) + " but logged user is: " + std::to_string(CurrentUserId));
					return;
				}

				continue;
			}
			catch (const std::exception& e)
			{
				return;
			}
		}
		
		CachedScreenshotsNames.push_back(getScreenshotsDirectoryPath() + StringUtility::s2ws(Cryptography::decode(Line)));
	}
}
