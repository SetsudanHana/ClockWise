#pragma once
#include "Object.h"

namespace FastUI
{
	struct MouseEvent;

	class FASTUI_API Control : public Object
	{
	public:
		Control();
		virtual ~Control();

		virtual void invalidate() override;
		bool isMouseOver() { return MouseHover; }

		const Color& getBorderColor() { return BorderColor; }
		const Color& getBackgroundColor() { return BackgroundColor; }
		const Color& getHoverColor() { return HoverColor; }
		const Color& getPressColor() { return PressColor; }
		const Color& getTextColor() { return TextColor; }

		void setBorderColor(const Color& NewColor) { BorderColor = NewColor; }
		void setBackgroundColor(const Color& NewColor) { BackgroundColor = NewColor; }
		void setHoverColor(const Color& NewColor) { HoverColor = NewColor; }
		void setPressColor(const Color& NewColor) { PressColor = NewColor; }
		void setTextColor(const Color& NewColor) { TextColor = NewColor; }

	protected:
		void onClick(const MouseEvent& ClickPoint);

		virtual void onMousePressed(const MouseEvent& ClickPoint);
		virtual void onMouseReleased(const MouseEvent& ClickPoint);

		virtual void onMouseEnter(const MouseEvent& ClickPoint);
		virtual void onMouseLeave(const MouseEvent& ClickPoint);

		Object* Parent;

		bool Pressed;
		bool MouseHover;
		bool Disabled;

		Color BorderColor;
		Color BackgroundColor;
		Color HoverColor;
		Color PressColor;
		Color TextColor;

		friend class View;
	};
}

