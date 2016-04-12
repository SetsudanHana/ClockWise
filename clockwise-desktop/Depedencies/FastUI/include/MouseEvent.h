#pragma once
#include "Event.h"
#include "Point.h"

namespace FastUI
{
	struct FASTUI_API MouseEvent : public Event
	{
		static const EventCode Any = 40;
		static const EventCode Pressed = 41;
		static const EventCode Released = 42;
		static const EventCode Move = 43;
		static const EventCode Entered = 44;
		static const EventCode Leaved = 45;

		MouseEvent() {};
		MouseEvent(const Point& NewPosition) : Position(NewPosition) {};
		Point Position;
	};
}
