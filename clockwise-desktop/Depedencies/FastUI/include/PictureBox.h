#pragma once
#include "Control.h"
#include "Rect.h"
#include <string>

namespace FastUI
{
	class FASTUI_API PictureBox : public Control
	{
	public:
		PictureBox(const Image& Bitmap, const Point& NewPosition);
		virtual ~PictureBox() = default;

		virtual void onDraw(Renderer& GraphicsRenderer) override;

	protected:
		const Image& PictureBitmap;
		Point Position;
	};
}

