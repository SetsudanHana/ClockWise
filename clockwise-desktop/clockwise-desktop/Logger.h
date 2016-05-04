#pragma once

#include <string>
#include <fstream>
#include <memory>

enum class LOG_TYPE
{
	TYPE_INFO,
	TYPE_WARNING,
	TYPE_ERROR
};

class Logger
{
public:
	Logger(const std::string& Path);

	void print(const std::string& Info, LOG_TYPE Type);

private:
	void printDate();
	std::ofstream FileStream;
};

extern std::unique_ptr<Logger> gLogger;

#define LOG(x) gLogger->print(x, LOG_TYPE::TYPE_INFO);
#define LOG_WARNING(x) gLogger->print(x, LOG_TYPE::TYPE_WARNING);
#define LOG_ERROR(x) gLogger->print(x, LOG_TYPE::TYPE_ERROR);
