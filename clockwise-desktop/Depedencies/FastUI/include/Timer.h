#pragma once

#include "export.h"
#include <chrono>

namespace FastUI
{
	class FASTUI_API Timer
	{
	public:
		void start();
		float getTime();

	private:
		typedef std::chrono::high_resolution_clock Clock;
		Clock::time_point StartTime;
	};
}