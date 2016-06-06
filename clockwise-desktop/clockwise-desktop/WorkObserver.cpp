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
	: Communicator(EndpointAddress, AuthSystem), UpdateNotifier([](){}), ScreenshotAppWorker(Communicator), StatisticsAppWorker(Communicator)
{
}

WorkObserver::~WorkObserver()
{
	terminate();
}

void WorkObserver::start()
{
	UpdatesCount = 0;
	ScreenshotAppWorker.createNewScreenshotInterval();
	FirstRun = true;

	StatisticsAppWorker.startHooks();

	using namespace std::chrono_literals;
	ThreadExecutor.start(60s, std::bind(&WorkObserver::run, this));
}

void WorkObserver::run()
{
	StatisticsAppWorker.updateCounters();
	auto ShouldUpdateScreenshots = UpdatesCount == ScreenshotAppWorker.getScreenshotInterval() || FirstRun;

	if (ShouldUpdateScreenshots)
	{
		ScreenshotAppWorker.captureScreenshot();
		ScreenshotAppWorker.createNewScreenshotInterval();
	}

	UpdateNotifier();

	//Safe check for possibility of timing out session
	if (!Communicator.getAuthentication())
	{
		return;
	}

	StatisticsAppWorker.uploadStatistics();
	ScreenshotAppWorker.uploadScreenshots();

	UpdatesCount++;
	FirstRun = false;
}

void WorkObserver::stop()
{
	StatisticsAppWorker.stopHooks();
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

unsigned int WorkObserver::getLastKeybordClickPerMinute()
{
	return StatisticsAppWorker.getLastKeybordClickPerMinute();
}

unsigned int WorkObserver::getLastMouseClickPerMinute()
{
	return StatisticsAppWorker.getLastMouseClickPerMinute();
}

unsigned int WorkObserver::getLastMouseDistancePerMinute()
{
	return StatisticsAppWorker.getLastMouseDistancePerMinute();
}

std::wstring WorkObserver::getLastScreenshotFilename()
{
	return ScreenshotAppWorker.getLastScreenshotFilename();
}

void WorkObserver::setUpdateNotifier(const std::function<void(void)>& Callback)
{
	//Can be only called when thread is not working, as function is not mutex protected
	assert(!isRunning());
	UpdateNotifier = Callback;
}
