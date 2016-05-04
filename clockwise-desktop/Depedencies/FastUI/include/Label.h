#pragma once
#include "Control.h"
#include "Alignment.h"
#include <string>

namespace FastUI
{
	class FASTUI_API Label : public Control
	{
	public:
		Label(const Rect& ButtonSize, const std::wstring& NewText = L"Label");
		virtual ~Label() = default;

		virtual void onDraw(Renderer& GraphicsRenderer) override;

		virtual void setText(const std::wstring& NewText);
		const std::wstring& getText() { return Text; }

		Alignment GetTextAlignment() { return TextAlignment; }
		void setTextAlignment(Alignment Align) { TextAlignment = Align; }

	protected:
		std::wstring Text;
		Alignment TextAlignment;
	};
}

