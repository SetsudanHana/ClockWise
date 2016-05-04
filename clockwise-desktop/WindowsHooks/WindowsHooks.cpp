#include "stdafx.h"
#include "WindowsHooks.h"
#include "MouseHook.h"
#include "KeyboardHook.h"

#pragma comment( lib, "user32.lib" )

WindowsSystemHooks::~WindowsSystemHooks()
{
	stopHooks();
}

HookErrorCodes WindowsSystemHooks::startHooks()
{
	auto Error = MouseHook::startHook();
	if (Error != HookErrorCodes::Ok)
	{
		return Error;
	}

	return KeyboardHook::startHook();
}

void WindowsSystemHooks::stopHooks()
{
	MouseHook::stopHook();
	KeyboardHook::stopHook();
}

unsigned int WindowsSystemHooks::getMouseClickCount()
{
	return MouseHook::getMouseClickCount();
}

unsigned int WindowsSystemHooks::getMouseDistance()
{
	return MouseHook::getMouseDistance();
}

unsigned int WindowsSystemHooks::getKeyboardClickCount()
{
	return KeyboardHook::getKeyboardClickCount();
}
