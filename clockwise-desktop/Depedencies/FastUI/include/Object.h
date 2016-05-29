#pragma once
#include "export.h"
#include "Renderer.h"
#include "Point.h"
#include "KeyboardEvent.h"
#include "EventHandler.h"
#include <unordered_map>

namespace FastUI
{
	class FASTUI_API Object
	{
	public:
		Object();
		virtual ~Object() = default;

		void addEventHandler(const EventCode Code, const EventHandler& Handler);
		virtual void fireEvent(const EventCode Code, const Event& EventData);

		virtual bool isVisible() const;
		virtual void invalidate();
		virtual bool hasFocus() const;
		virtual const Rect& getSize() const;
		virtual void onDraw(Renderer& GraphicsRenderer);
		virtual void onFocus();
		virtual void onFocusLost();

		virtual void setVisible(bool IsVisible);

	protected:
		virtual void onCreate();

		std::unordered_map<EventCode, EventHandler> HandlersMap;

		Rect Size;
		bool Focused;
		bool Visible;
		bool Created;
	};
}