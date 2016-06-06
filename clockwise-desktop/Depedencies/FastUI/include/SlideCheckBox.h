#pragma once
#include "CheckBox.h"

namespace FastUI
{
	class FASTUI_API SlideCheckBox : public CheckBox
	{
	public:
		SlideCheckBox(const Rect& ButtonSize, const std::wstring& NewText);

		virtual void onDraw(Renderer& GraphicsRenderer) override;
	};
}