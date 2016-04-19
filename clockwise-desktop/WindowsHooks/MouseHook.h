#pragma once
#include "Export.h"
#include "..\clockwise-desktop\HookErrorCodes.h"

class WINDOWSHOOKS_API MouseHook
{
public:
	static HookErrorCodes startHook();
	static void stopHook();
	static unsigned int getMouseClickCount() { return MouseClickCount; }
	static unsigned int getMouseDistance() { return MouseDistance; }

private:
	static void MessageLoop();
	static DWORD WINAPI MouseLogger(LPVOID lpParm);
	static LRESULT CALLBACK MouseEvent(int nCode, WPARAM wParam, LPARAM lParam);
	static bool initialized;
	static unsigned int MouseClickCount;
	static unsigned int MouseDistance;

	static unsigned int LastMousePositionX;
	static unsigned int LastMousePositionY;
};