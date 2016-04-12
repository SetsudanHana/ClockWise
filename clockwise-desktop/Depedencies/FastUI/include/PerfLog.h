#pragma once

#include "Timer.h"
#include <string>

namespace FastUI
{
	class FASTUI_API PerfLog : public Timer
	{
	public:
		PerfLog();
		PerfLog(const std::wstring& Message);
		~PerfLog();

	private:
		std::wstring LogMessage;
	};
}