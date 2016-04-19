#pragma once
#include <functional>
#include <atomic>
#include <chrono>
#include <thread>
#include <mutex>

class TimedExecutor
{
public:
	TimedExecutor();
	~TimedExecutor();

	void start(std::chrono::seconds Seconds, const std::function<void(void)>& ActionToExecute);
	void stop();
	bool isRunning();

private:
	void run();
	void sleepy(std::chrono::seconds Seconds);

	std::function<void(void)> Action;
	std::atomic_bool ExitCondition;
	std::chrono::seconds Period;
	std::thread TimerThread;

	std::mutex SleepMutex;
	std::condition_variable SleepCondition;
	bool Flag;
};