#include "stdafx.h"
#include "MouseHook.h"
#include <synchapi.h>
#include <math.h>

HHOOK hMouseHook;
HANDLE hMouseThread;
bool MouseHook::initialized = false;
unsigned int MouseHook::MouseClickCount = 0;
unsigned int MouseHook::MouseDistance = 0;

unsigned int MouseHook::LastMousePositionX = 0;
unsigned int MouseHook::LastMousePositionY = 0;

__declspec(dllexport) LRESULT CALLBACK MouseHook::MouseEvent(int nCode, WPARAM wParam, LPARAM lParam)
{
	if (wParam == WM_LBUTTONDOWN || wParam == WM_RBUTTONDOWN)
	{
		MouseClickCount++;
	}

	MOUSEHOOKSTRUCT* pMouseStruct = (MOUSEHOOKSTRUCT *)lParam;
	if (pMouseStruct != NULL)
	{
		MouseDistance += abs(pMouseStruct->pt.x - LastMousePositionX) + abs(pMouseStruct->pt.y - LastMousePositionY);
		LastMousePositionX = pMouseStruct->pt.x;
		LastMousePositionY = pMouseStruct->pt.y;
	}

	return CallNextHookEx(hMouseHook, nCode, wParam, lParam);
}
void MouseHook::MessageLoop()
{
	MSG message;
	while (GetMessage(&message, NULL, 0, 0))
	{
		TranslateMessage(&message);
		DispatchMessage(&message);
	}
}
DWORD WINAPI MouseHook::MouseLogger(LPVOID lpParm)
{
	HINSTANCE hInstance = GetModuleHandle(NULL);
	if (!hInstance) hInstance = LoadLibrary((LPWSTR)lpParm);
	if (!hInstance) return 1;
	hMouseHook = SetWindowsHookEx(
		WH_MOUSE_LL,
		(HOOKPROC)MouseEvent,
		hInstance,
		NULL
		);

	MessageLoop();
	UnhookWindowsHookEx(hMouseHook);
	return 0;
}

HookErrorCodes MouseHook::startHook()
{
	if (initialized)
	{
		return HookErrorCodes::AlreadyExists;
	}
	MouseClickCount = 0;
	MouseDistance = 0;
	LastMousePositionX = 0;
	LastMousePositionY = 0;
	initialized = true;

	DWORD dwThread;
	hMouseThread = CreateThread(NULL, NULL, (LPTHREAD_START_ROUTINE)MouseHook::MouseLogger, NULL, NULL, &dwThread);

	if (hMouseThread != NULL)
	{
		return HookErrorCodes::Ok;
	}

	return HookErrorCodes::FailedToCreate;
}

void MouseHook::stopHook()
{
	TerminateThread(hMouseThread, 0);
	initialized = false;
}

