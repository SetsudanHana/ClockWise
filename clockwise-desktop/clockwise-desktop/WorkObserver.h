#pragma once
#include "..\WindowsHooks\WindowsHooks.h"
#include "ServiceCommunicator.h"
#include "TimedExecutor.h"

class Authentication;

class WorkObserver
{
public:
	WorkObserver(const std::string& EndpointAddress, Authentication& AuthSystem);
	~WorkObserver();

	void start();

	void CreateNewScreenshotInterval();

	void terminate();
	bool isRunning();

	unsigned int getLastKeybordClickPerMinute();
	unsigned int getLastMouseClickPerMinute();
	unsigned int getLastMouseDistancePerMinute();
	std::wstring getLastScreenshotFilename();

	void setUpdateNotifier(const std::function<void(void)>& Callback);

private:
	void captureScreenshot();
	void run();
	void stop();

	void loadScreenshotBackup();
	void loadStatisticsBackup();

	void updateCounters();
	void uploadScreenshots();
	void uploadSingleScreenshot(const std::wstring& Filename);
	void uploadStatistics(Authentication * AuthSystem);

	WindowsSystemHooks Hooks;
	ServiceCommunicator Communicator;
	TimedExecutor ThreadExecutor;
	std::function<void(void)> UpdateNotifier;
	std::vector<web::json::value> CachedValues;
	std::vector<std::wstring> CachedScreenshotsNames;

	bool FirstRun;
	unsigned int KeyboardClicksPerMinute;
	unsigned int MouseClicksPerMinute;
	unsigned int MouseDistancePerMinute;
	std::wstring ScreenshotFilename;

	unsigned int LastKeyboardCount;
	unsigned int LastMouseCount;
	unsigned int LastMouseDelta;

	unsigned int ScreenshotInterval;
	unsigned int UpdatesCount;
};