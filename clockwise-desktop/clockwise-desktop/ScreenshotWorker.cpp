#include "stdafx.h"
#include "ScreenshotWorker.h"
#include "Enviroment.h"
#include "StringUtility.h"
#include "ScreenCapturer.h"
#include "Logger.h"
#include "ServiceCommunicator.h"
#include "Authentication.h"
#include "User.h"
#include <algorithm>
#include <fstream>
#include <cpprest\oauth2.h>
#include "Base64.h"

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

		for (auto& Filename : CachedScreenshotsNames)
		{
			uploadSingleScreenshot(Filename);
		}
	}
	catch (const std::exception& e)
	{
		std::fstream backupStream(Enviroment::getAppDataPath() + L"/backupScreenshots.clk", std::ios::out | std::ios::trunc);
		if (!backupStream.is_open())
		{
			LOG_ERROR("Couldn't open backup file!");
			return;
		}

		for (auto& Filename : CachedScreenshotsNames)
		{
			backupStream << StringUtility::ws2s(Filename) << std::endl;
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

void ScreenshotWorker::captureScreenshot()
{
	auto ScreenshotFilepath = Enviroment::getAppDataPath() + L"images\\";
	auto ScreenshotFilenameOnly = L"screenshot" + StringUtility::getDateString() + L".jpg";

	std::replace(ScreenshotFilenameOnly.begin(), ScreenshotFilenameOnly.end(), ':', '-'); // ':' are invalid characters in win32
	Enviroment::createPath(ScreenshotFilepath);
	ScreenshotFilename = ScreenshotFilepath + ScreenshotFilenameOnly;

	ScreenCapturer::captureScreenAndSave(ScreenshotFilename);
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
	std::fstream backupStream(Enviroment::getAppDataPath() + L"/backupScreenshots.clk", std::ios::in);
	if (!backupStream.is_open())
	{
		LOG("Cannot open or find backup screenshot file. This is fine if no backup was done.");
		return;
	}

	std::string Line;

	while (std::getline(backupStream, Line))
	{
		CachedScreenshotsNames.push_back(StringUtility::s2ws(Line));
	}
}
