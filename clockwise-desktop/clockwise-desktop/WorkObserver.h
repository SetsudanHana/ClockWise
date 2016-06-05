#pragma once
#include "ServiceCommunicator.h"
#include "TimedExecutor.h"
#include "ScreenshotWorker.h"
#include "StatisticsWorker.h"

class Authentication;

class WorkObserver
{
public:
	WorkObserver(const std::string& EndpointAddress, Authentication& AuthSystem);
	~WorkObserver();

	void start();

	void terminate();
	bool isRunning();

	unsigned int getLastKeybordClickPerMinute();
	unsigned int getLastMouseClickPerMinute();
	unsigned int getLastMouseDistancePerMinute();
	std::wstring getLastScreenshotFilename();

	void setUpdateNotifier(const std::function<void(void)>& Callback);

private:	
	void run();
	void stop();

	ServiceCommunicator Communicator;
	TimedExecutor ThreadExecutor;
	ScreenshotWorker ScreenshotAppWorker;
	StatisticsWorker StatisticsAppWorker;
	std::function<void(void)> UpdateNotifier;

	bool FirstRun;
	unsigned int UpdatesCount;
};