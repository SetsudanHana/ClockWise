#pragma once
#include "Export.h"
#include "..\clockwise-desktop\HookErrorCodes.h"

class WINDOWSHOOKS_API KeyboardHook
{
public:
	static HookErrorCodes startHook();
	static void stopHook();
	static unsigned int getKeyboardClickCount() { return KeyboardClickCount; }

private:
	static void MessageLoop();
	static DWORD WINAPI KeyboardLogger(LPVOID lpParm);
	static LRESULT CALLBACK KeyboardEvent(int nCode, WPARAM wParam, LPARAM lParam);
	static bool initialized;
	static unsigned int KeyboardClickCount;
};