#pragma once
#include "Control.h"
#include "Rect.h"
#include <string>

namespace FastUI
{
	class FASTUI_API PictureBox : public Control
	{
	public:
		PictureBox(const Rect& NewBoundingRect);
		PictureBox(Image& Bitmap, const Rect& NewBoundingRect);
		virtual ~PictureBox() = default;

		virtual void onDraw(Renderer& GraphicsRenderer) override;

		Image* getImage();
		void setImage(Image& NewImage);

	protected:
		Image* PictureBitmap;
		Rect BoundingBox;
	};
}

