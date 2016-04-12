#pragma once
#include "Control.h"
#include "Rect.h"
#include <string>

namespace FastUI
{
	class FASTUI_API Rectangle : public Control
	{
	public:
		Rectangle(const Rect& RectSize);
		virtual ~Rectangle() = default;

		virtual void onDraw(Renderer& GraphicsRenderer) override;

		unsigned int getBorderWidth() { return BorderWidth; }
		void setBorderWidth(unsigned int NewBorderWidth) { BorderWidth = NewBorderWidth; }

	protected:
		unsigned int BorderWidth;
	};
}
