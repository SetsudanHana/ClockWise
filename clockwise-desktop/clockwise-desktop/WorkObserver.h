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
	void terminate();
	bool isRunning();

	unsigned int getLastKeybordClickPerMinute();
	unsigned int getLastMouseClickPerMinute();
	unsigned int getLastMouseDistancePerMinute();

	void setUpdateNotifier(const std::function<void(void)>& Callback);

private:
	void run();
	void stop();

	void UpdateCounters();

	WindowsSystemHooks Hooks;
	ServiceCommunicator Communicator;
	TimedExecutor ThreadExecutor;
	std::function<void(void)> UpdateNotifier;


	unsigned int KeyboardClicksPerMinute;
	unsigned int MouseClicksPerMinute;
	unsigned int MouseDistancePerMinute;

	unsigned int LastKeyboardCount;
	unsigned int LastMouseCount;
	unsigned int LastMouseDelta;
};