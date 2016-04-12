#pragma once
#include "Event.h"

namespace FastUI
{
	struct FASTUI_API KeyboardEvent : public Event
	{
		static const EventCode Any = 20;
		static const EventCode Pressed = 21;
		static const EventCode Released = 22;

		KeyboardEvent(wchar_t EventKey, bool EventShift, bool EventAlt, bool EventBackspace)
			: Key(EventKey), Shift(EventShift), Alt(EventAlt), Backspace(EventBackspace) {};

		wchar_t Key;
		bool Shift;
		bool Alt;
		bool Backspace;
	};
}