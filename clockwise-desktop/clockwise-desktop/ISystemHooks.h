#pragma once
#include "HookErrorCodes.h"

class ISystemHooks
{
public:
	virtual HookErrorCodes startHooks() = 0;
	virtual void stopHooks() = 0;

	virtual unsigned int getMouseClickCount() = 0;
	virtual unsigned int getMouseDistance() = 0;
	virtual unsigned int getKeyboardClickCount() = 0;
};