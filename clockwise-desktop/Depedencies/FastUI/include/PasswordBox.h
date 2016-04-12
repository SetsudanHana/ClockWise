#pragma once
#include "TextBox.h"

namespace FastUI
{
	class FASTUI_API PasswordBox : public TextBox
	{
	public:
		PasswordBox(const Rect& ButtonSize, const std::wstring& DefaultText);
		virtual ~PasswordBox() = default;

	protected:
		virtual void drawText(Renderer& GraphicsRenderer, Color TextColor, Rect TextRect) override;
	};
}

