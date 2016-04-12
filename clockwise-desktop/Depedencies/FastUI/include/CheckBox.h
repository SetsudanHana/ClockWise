#pragma once
#include "Button.h"

namespace FastUI
{
	class FASTUI_API CheckBox : public Button
	{
	public:
		CheckBox(const Rect& ButtonSize, const std::wstring& NewText);
		virtual ~CheckBox() = default;

		bool isChecked() { return Checked; }

		virtual void onDraw(Renderer& GraphicsRenderer) override;

		virtual void setText(const std::wstring& NewText);
		const std::wstring& getText() { return Text; }

	protected:
		bool Checked;
	};
}

