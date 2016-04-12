#pragma once
#include "Control.h"
#include "Rect.h"
#include <string>

namespace FastUI
{
	class FASTUI_API Button : public Control
	{
	public:
		Button(const Rect& ButtonSize, const std::wstring& NewText);
		virtual ~Button() = default;

		virtual void onDraw(Renderer& GraphicsRenderer) override;

		virtual void setText(const std::wstring& NewText);
		virtual const std::wstring& getText() { return Text; }

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
		std::wstring Text;

		Color BorderColor;
		Color BackgroundColor;
		Color HoverColor;
		Color PressColor;
		Color TextColor;
	};
}

