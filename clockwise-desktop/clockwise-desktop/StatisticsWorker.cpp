#include "stdafx.h"
#include "StatisticsWorker.h"
#include <fstream>
#include "Enviroment.h"
#include <cpprest\json.h>
#include "ServiceCommunicator.h"
#include "Authentication.h"
#include "StringUtility.h"
#include "User.h"
#include "Logger.h"

StatisticsWorker::StatisticsWorker(ServiceCommunicator& AppCommunicator) 
	: Communicator(AppCommunicator)
{
	loadStatisticsBackup();

	LastKeyboardCount = 0;
	LastMouseCount = 0;
	LastMouseDelta = 0;
}

void StatisticsWorker::startHooks()
{
	//Comment below lines to enable stutter free debugging
	if (Hooks.startHooks() != HookErrorCodes::Ok)
	{
		LOG_ERROR("Failed to create system hooks");
		return;
	}
	///////////////////////////////////////////////////////
}

void StatisticsWorker::stopHooks()
{
	Hooks.stopHooks();
	LastKeyboardCount = 0;
	LastMouseCount = 0;
	LastMouseDelta = 0;
}

void StatisticsWorker::uploadStatistics()
{
	web::json::value Statistics;
	Statistics[L"keyboardClickedCount"] = KeyboardClicksPerMinute;
	Statistics[L"mouseClickedCount"] = MouseClicksPerMinute;
	Statistics[L"mouseMovementCount"] = MouseDistancePerMinute;
	Statistics[L"date"] = web::json::value::string(StringUtility::getDateString());

	auto AuthSystem = Communicator.getAuthentication();
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

void StatisticsWorker::loadStatisticsBackup()
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

void StatisticsWorker::updateCounters()
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

unsigned int StatisticsWorker::getLastKeybordClickPerMinute()
{
	return KeyboardClicksPerMinute;
}

unsigned int StatisticsWorker::getLastMouseClickPerMinute()
{
	return MouseClicksPerMinute;
}

unsigned int StatisticsWorker::getLastMouseDistancePerMinute()
{
	return MouseDistancePerMinute;
}