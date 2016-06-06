#pragma once
#include "export.h"
#include "Event.h"
#include <functional>

namespace FastUI
{
	typedef std::function<void(const Event&)> EventHandler;
}
