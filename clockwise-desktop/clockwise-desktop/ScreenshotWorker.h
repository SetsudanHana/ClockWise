#pragma once
#include <vector>
#include <string>

class ServiceCommunicator;

class ScreenshotWorker
{
public:
	ScreenshotWorker(ServiceCommunicator& WorkerCommunicator);
	
	void captureScreenshot();
	void createNewScreenshotInterval();
	unsigned int getScreenshotInterval();
	void uploadScreenshots();

	std::wstring getLastScreenshotFilename();

private:
	void uploadSingleScreenshot(const std::wstring& Filename);
	void loadScreenshotBackup();

	std::wstring ScreenshotFilename;
	ServiceCommunicator& Communicator;

	std::vector<std::wstring> CachedScreenshotsNames;
	unsigned int ScreenshotInterval;
};