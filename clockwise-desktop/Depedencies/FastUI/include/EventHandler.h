#pragma once
#include "export.h"
#include "Event.h"
#include <functional>

namespace FastUI
{
	typedef std::function<void(const Event&)> EventHandler;

	/*class FASTUI_API EventHandler : public std::function<void(const Event&)>
	{
	public:
		EventHandler() : handlingFunction([](const Event& data) {})
		{
		}

		EventHandler(const std::function<void(const Event&)>& handler) : handlingFunction(handler)
		{
		}

		EventHandler(const EventHandler& RValue) : handlingFunction(RValue.handlingFunction) 
		{
		}

		void operator=(const std::function<void(const Event&)>& handler)
		{
			handlingFunction = handler;
		}

		virtual void handle(const Event& Event)
		{
			handlingFunction(Event);
		}

	private:
		std::function<void(const Event&)> handlingFunction;
	};*/
}
