#include "stdafx.h"
#include "WorkObserver.h"
#include "Logger.h"
#include "User.h"
#include "Authentication.h"
#include <thread>
#include <chrono>
#include <ctime>
#include "StringUtility.h"
#include <time.h>
#include "ScreenCapturer.h"
#include "Enviroment.h"
#include "Base64.h"

WorkObserver::WorkObserver(const std::string& EndpointAddress, Authentication& AuthSystem)
	: Communicator(EndpointAddress, AuthSystem), UpdateNotifier([](){})
{
	LastKeyboardCount = 0;
	LastMouseCount = 0;
	LastMouseDelta = 0;

	loadScreenshotBackup();
	loadStatisticsBackup();
}

WorkObserver::~WorkObserver()
{
	terminate();
}

void WorkObserver::start()
{
	UpdatesCount = 0;
	CreateNewScreenshotInterval();
	FirstRun = true;

	/*if (Hooks.startHooks() != HookErrorCodes::Ok)
	{
		LOG_ERROR("Failed to create system hooks");
		return;
	}*/

	using namespace std::chrono_literals;
	ThreadExecutor.start(60s, std::bind(&WorkObserver::run, this));
}

void WorkObserver::run()
{
	updateCounters();
	bool ShouldUpdateScreenshots = UpdatesCount == ScreenshotInterval || FirstRun;

	if (ShouldUpdateScreenshots)
	{
		captureScreenshot();
		CreateNewScreenshotInterval();
	}

	UpdateNotifier();
	auto AuthSystem = Communicator.getAuthentication();

	if (!AuthSystem)
	{
		return;
	}

	uploadStatistics(AuthSystem);
	uploadScreenshots();

	UpdatesCount++;
	FirstRun = false;
}

void WorkObserver::stop()
{
	Hooks.stopHooks();
	LastKeyboardCount = 0;
	LastMouseCount = 0;
	LastMouseDelta = 0;
}

void WorkObserver::terminate()
{
	ThreadExecutor.stop();
	stop();
}

bool WorkObserver::isRunning()
{
	return ThreadExecutor.isRunning();
}

void WorkObserver::uploadScreenshots()
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

void WorkObserver::uploadSingleScreenshot(const std::wstring& Filename)
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

void WorkObserver::uploadStatistics(Authentication * AuthSystem)
{
	web::json::value Statistics;
	Statistics[L"keyboardClickedCount"] = KeyboardClicksPerMinute;
	Statistics[L"mouseClickedCount"] = MouseClicksPerMinute;
	Statistics[L"mouseMovementCount"] = MouseDistancePerMinute;
	Statistics[L"date"] = web::json::value::string(StringUtility::getDateString());

	auto userId = std::to_string(AuthSystem->getUser()->getUserId());
	std::fstream backupStream(Enviroment::getAppDataPath() + L"/backup.clk", std::ios::out | std::ios::trunc);

	try
	{
		auto statisticsPath = "api/users/" + userId + "/statistics";

		for (auto& CachedValue : CachedValues)
		{
			Communicator.post(statisticsPath, CachedValue);
		}

		Communicator.post(statisticsPath, Statistics);
	}
	catch (const std::exception& e)
	{
		CachedValues.push_back(Statistics);

		if (!backupStream.is_open())
		{
			LOG_ERROR("Couldn't open backup file!");
			return;
		}

		for (auto& CachedValue : CachedValues)
		{
			CachedValue.serialize(backupStream);
			backupStream << std::endl;
		}
	}
}

void WorkObserver::updateCounters()
{
	auto CurrentKeyboardCount = Hooks.getKeyboardClickCount();
	auto CurrentMouseCount = Hooks.getMouseClickCount();
	auto CurrentDistanceCount = Hooks.getMouseDistance();

	KeyboardClicksPerMinute = CurrentKeyboardCount - LastKeyboardCount;
	MouseClicksPerMinute = CurrentMouseCount - LastMouseCount;
	MouseDistancePerMinute = CurrentDistanceCount - LastMouseDelta;

	LastKeyboardCount = CurrentKeyboardCount;
	LastMouseCount = CurrentMouseCount;
	LastMouseDelta = CurrentDistanceCount;
}

void WorkObserver::captureScreenshot()
{
	auto ScreenshotFilepath = Enviroment::getAppDataPath() + L"images\\";
	auto ScreenshotFilenameOnly = L"screenshot" + StringUtility::getDateString() + L".jpg";

	std::replace(ScreenshotFilenameOnly.begin(), ScreenshotFilenameOnly.end(), ':', '-'); // ':' are invalid characters in win32
	Enviroment::createPath(ScreenshotFilepath);
	ScreenshotFilename = ScreenshotFilepath + ScreenshotFilenameOnly;

	ScreenCapturer::captureScreenAndSave(ScreenshotFilename);
}

void WorkObserver::CreateNewScreenshotInterval()
{
	ScreenshotInterval = 8 + rand() % 5;
}

void WorkObserver::loadScreenshotBackup()
{
	std::fstream backupStream(Enviroment::getAppDataPath() + L"/backupScreenshots.clk", std::ios::in);
	std::string Line;

	while (std::getline(backupStream, Line))
	{
		CachedScreenshotsNames.push_back(StringUtility::s2ws(Line));
	}
}

void WorkObserver::loadStatisticsBackup()
{
	std::fstream backupStream(Enviroment::getAppDataPath() + L"/backup.clk", std::ios::in);
	std::string Line;

	while (std::getline(backupStream, Line))
	{
		auto WideLine = StringUtility::s2ws(Line);

		auto Statistics = web::json::value::parse(WideLine);
		CachedValues.push_back(Statistics);
	}
}

unsigned int WorkObserver::getLastKeybordClickPerMinute()
{
	return KeyboardClicksPerMinute;
}

unsigned int WorkObserver::getLastMouseClickPerMinute()
{
	return MouseClicksPerMinute;
}

unsigned int WorkObserver::getLastMouseDistancePerMinute()
{
	return MouseDistancePerMinute;
}

std::wstring WorkObserver::getLastScreenshotFilename()
{
	return ScreenshotFilename;
}

void WorkObserver::setUpdateNotifier(const std::function<void(void)>& Callback)
{
	//Can be only called when thread is not working, as function is not mutex protected
	assert(!isRunning());
	UpdateNotifier = Callback;
}
