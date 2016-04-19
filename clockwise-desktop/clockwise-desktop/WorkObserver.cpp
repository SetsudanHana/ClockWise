#include "stdafx.h"
#include "WorkObserver.h"
#include "Logger.h"
#include <thread>

WorkObserver::WorkObserver(const std::string& EndpointAddress, Authentication& AuthSystem)
	: Communicator(EndpointAddress, AuthSystem), UpdateNotifier([](){})
{
	LastKeyboardCount = 0;
	LastMouseCount = 0;
	LastMouseDelta = 0;
}

WorkObserver::~WorkObserver()
{
	terminate();
}

void WorkObserver::start()
{
	if (Hooks.startHooks() != HookErrorCodes::Ok)
	{
		LOG_ERROR("Failed to create system hooks");
		return;
	}

	using namespace std::chrono_literals;
	ThreadExecutor.start(60s, std::bind(&WorkObserver::run, this));
}

void WorkObserver::run()
{
	UpdateCounters();
	UpdateNotifier();
}

void WorkObserver::UpdateCounters()
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

void WorkObserver::stop()
{
	Hooks.stopHooks();
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

void WorkObserver::setUpdateNotifier(const std::function<void(void)>& Callback)
{
	//Can be only called when thread is not working, as function is not mutex protected
	assert(!isRunning());
	UpdateNotifier = Callback;
}
