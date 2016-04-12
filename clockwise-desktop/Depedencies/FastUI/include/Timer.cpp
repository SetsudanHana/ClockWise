#include "stdafx.h"
#include "../include/Timer.h"

namespace FastUI
{
	void Timer::start()
	{
		StartTime = Clock::now();
	}

	float Timer::getTime()
	{
		return (float)std::chrono::duration_cast<std::chrono::milliseconds>(Clock::now() - StartTime).count();
	}

}