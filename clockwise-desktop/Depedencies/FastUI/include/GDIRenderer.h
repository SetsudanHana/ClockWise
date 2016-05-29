#pragma once
#include "Renderer.h"
#include <string>
#include "GdiPenCache.h"
#include "GdiBrushCache.h"
#include "GdiImageCache.h"
#include "GdiFontCache.h"

namespace FastUI
{
	class FASTUI_API GDIRenderer : public Renderer
	{
	public:
		GDIRenderer(HDC Hdc);
		virtual ~GDIRenderer();

		virtual void beginDraw(const Rect& DrawArea) override;
		virtual void endDraw() override;

		virtual void drawLine(const Point& StartPoint, const Point& EndPoint, int LineWidth, const Color& LineColor);

		virtual void drawRectangle(float X, float Y, float Width, float Height, int LineWidth, const Color& RectangleColor, const Color& BackgroundColor);
		virtual void drawRectangle(const Rect& RectangleSize, int LineWidth, const Color& RectangleColor, const Color& BackgroundColor);

		virtual void drawText(const std::wstring& Text, const Rect& RectSize, const Color& RectangleColor, unsigned int FontSize, Alignment TextAlignment = Alignment::Left) override;
		virtual void drawImage(const Image& Bitmap, const Rect& BoundingBox) override;
		virtual void drawRoundedRectangle(const Rect& RectangleSize, int LineWidth, int Round, const Color& RectangleColor, const Color& BackgroundColor) override;

	private:
		HDC Hdc;
		GdiPenCache PenCache;
		GdiBrushCache BrushCache;
		GdiImageCache ImageCache;
		GdiFontCache FontCache;

		HDC BackBufferHdc;
		HBITMAP BackBufferBitmap;
		HBITMAP FrontBufferBitmap;
		Rect DrawRect;
	};
}