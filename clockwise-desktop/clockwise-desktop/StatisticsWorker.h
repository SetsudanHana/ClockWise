#pragma once
#include "..\WindowsHooks\WindowsHooks.h"
#include <cpprest\json.h>
#include <vector>

class ServiceCommunicator;

class StatisticsWorker
{
public:
	StatisticsWorker(ServiceCommunicator& AppCommunicator);

	void startHooks();
	void stopHooks();

	void uploadStatistics();
	void updateCounters();

	unsigned int getLastKeybordClickPerMinute();
	unsigned int getLastMouseClickPerMinute();
	unsigned int getLastMouseDistancePerMinute();

private:
	void loadStatisticsBackup();
	std::wstring getStatisticsBackupPath();

	WindowsSystemHooks Hooks;
	ServiceCommunicator& Communicator;
	std::vector<web::json::value> CachedValues;

	unsigned int KeyboardClicksPerMinute;
	unsigned int MouseClicksPerMinute;
	unsigned int MouseDistancePerMinute;

	unsigned int LastKeyboardCount;
	unsigned int LastMouseCount;
	unsigned int LastMouseDelta;
};