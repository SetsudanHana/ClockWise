#include "stdafx.h"
#include "TimedExecutor.h"
#include <thread>

TimedExecutor::TimedExecutor() : ExitCondition(true)
{

}

TimedExecutor::~TimedExecutor()
{
	stop();
}

void TimedExecutor::start(std::chrono::seconds Seconds, const std::function<void(void)>& ActionToExecute)
{
	Action = ActionToExecute;
	ExitCondition = false;
	Flag = false;
	Period = Seconds;

	TimerThread = std::thread(&TimedExecutor::run, this);
	TimerThread.detach();
}

void TimedExecutor::stop()
{
	ExitCondition = true;
	{
		std::lock_guard<std::mutex> lock(SleepMutex);
		Flag = true;
		SleepCondition.notify_one();
	}
}

bool TimedExecutor::isRunning()
{
	return !ExitCondition;
}

void TimedExecutor::run()
{
	while (!ExitCondition)
	{
		Action();
		sleepy(Period);
	}
}

void TimedExecutor::sleepy(std::chrono::seconds Seconds)
{
	Flag = false;
	std::unique_lock<std::mutex> lock(SleepMutex);
	SleepCondition.wait_for(lock, Seconds, [&]() { return Flag; });
}
