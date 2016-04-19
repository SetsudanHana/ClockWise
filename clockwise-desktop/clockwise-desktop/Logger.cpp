#include "stdafx.h"
#include "Logger.h"
#include <chrono>
#include <iomanip>
#include <cassert>

Logger::Logger(const std::string& Path)
{
	FileStream.open(Path);
}

void Logger::print(const std::string& Info, LOG_TYPE Type)
{
	if (!FileStream.is_open())
	{
		assert(false);
		return;
	}

	printDate();

	switch (Type)
	{
	case LOG_TYPE::TYPE_INFO:
		FileStream << "[INFO]: ";
		break;

	case LOG_TYPE::TYPE_WARNING:
		FileStream << "[WARNING]: ";
		break;

	case LOG_TYPE::TYPE_ERROR:
		FileStream << "[ERROR]: ";
		break;

	}

	FileStream << Info << std::endl;
	OutputDebugStringA((Info + "\n").c_str());
}

void Logger::printDate()
{
	std::time_t Time = std::time(nullptr);
	struct tm timeinfo;
	localtime_s(&timeinfo, &Time);

	FileStream << std::put_time(&timeinfo, "%c") << " ";
}

std::unique_ptr<Logger> gLogger = std::unique_ptr<Logger>(new Logger("Log.txt"));
