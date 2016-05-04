#include "stdafx.h"
#include "KeyboardHook.h"
#include <synchapi.h>
#include <math.h>

HHOOK hKeyboardHook;
HANDLE hKeyboardThread;

bool KeyboardHook::initialized = false;
unsigned int KeyboardHook::KeyboardClickCount = 0;

__declspec(dllexport) LRESULT CALLBACK KeyboardHook::KeyboardEvent(int nCode, WPARAM wParam, LPARAM lParam)
{
	KeyboardClickCount++;
	return CallNextHookEx(hKeyboardHook, nCode, wParam, lParam);
}
void KeyboardHook::MessageLoop()
{
	MSG message;
	while (GetMessage(&message, NULL, 0, 0))
	{
		TranslateMessage(&message);
		DispatchMessage(&message);
	}
}
DWORD WINAPI KeyboardHook::KeyboardLogger(LPVOID lpParm)
{
	HINSTANCE hInstance = GetModuleHandle(NULL);
	if (!hInstance) hInstance = LoadLibrary((LPWSTR)lpParm);
	if (!hInstance) return 1;
	hKeyboardHook = SetWindowsHookEx(
		WH_KEYBOARD_LL,
		(HOOKPROC)KeyboardEvent,
		hInstance,
		NULL
		);

	MessageLoop();
	UnhookWindowsHookEx(hKeyboardHook);
	return 0;
}

HookErrorCodes KeyboardHook::startHook()
{
	if (initialized)
	{
		return HookErrorCodes::AlreadyExists;
	}
	KeyboardClickCount = 0;
	initialized = true;

	DWORD dwThread;
	hKeyboardThread = CreateThread(NULL, NULL, (LPTHREAD_START_ROUTINE)KeyboardHook::KeyboardLogger, (LPVOID)"ClockwiseHooks", NULL, &dwThread);

	if (hKeyboardThread != NULL)
	{
		return HookErrorCodes::Ok;
	}

	return HookErrorCodes::FailedToCreate;
}

void KeyboardHook::stopHook()
{
	TerminateThread(hKeyboardThread, 0);
	initialized = false;
}