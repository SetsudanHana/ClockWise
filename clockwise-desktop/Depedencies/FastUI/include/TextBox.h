#pragma once
#include "Button.h"
#include <string>

namespace FastUI
{
	class FASTUI_API TextBox : public Button
	{
	public:
		TextBox(const Rect& ButtonSize, const std::wstring& DefaultText);
		virtual ~TextBox() = default;

		virtual void onDraw(Renderer& GraphicsRenderer) override;

		void setDefaultText(const std::wstring& NewText);
		const std::wstring& getDefaultText();

		virtual void setText(const std::wstring& NewText) override;
		virtual const std::wstring& getText() override;

		const Color& getDefaultTextColor() { return DefaultTextColor; }
		void setDefaultTextColor(const Color& NewColor) { DefaultTextColor = NewColor; }

	protected:
		virtual void drawText(Renderer& GraphicsRenderer, Color TextColor, Rect TextRect);
		void onKeyboardPressed(const KeyboardEvent& Key);

		std::wstring InputText;
		
		Color DefaultTextColor;
	};
}

