#pragma once
#include "export.h"

namespace FastUI
{
	//Codemap for integer event mapping:
	//Event: 0 - 19
	//Keyboard: 20 - 39
	//Mouse: 40 - 59
	//Touch: 60 - 79

	typedef int EventCode;

	struct FASTUI_API Event
	{
		static const EventCode Any = 0;
		static const EventCode Click = 1;
		static const EventCode WindowClose = 2;
		static const EventCode WindowDestroy = 3;
	};
}