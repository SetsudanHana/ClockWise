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

std::string get_date_string()
{
	std::time_t t = std::time(NULL);
	tm tmStruct;
	gmtime_s(&tmStruct, &t);
	char mbstr[100];
	std::strftime(mbstr, sizeof(mbstr), "%FT%T", &tmStruct);
	return std::string(mbstr);
}

void WorkObserver::run()
{
	UpdateCounters();
	UpdateNotifier();

	auto AuthSystem = Communicator.getAuthentication();
	if (!AuthSystem)
	{
		return;
	}

	web::json::value Statistics;
	Statistics[L"keyboardClickedCount"] = KeyboardClicksPerMinute;
	Statistics[L"mouseClickedCount"] = MouseClicksPerMinute;
	Statistics[L"mouseMovementCount"] = MouseDistancePerMinute;
	Statistics[L"date"] = web::json::value::string(StringUtility::s2ws(get_date_string()));

	auto userId = std::to_string(AuthSystem->getUser()->getUserId());
	Communicator.post("api/users/" + userId + "/statistics", Statistics);
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
