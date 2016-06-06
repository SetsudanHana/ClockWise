#pragma once
#include "export.h"
#include "Rect.h"
#include "Color.h"
#include "Point.h"
#include "Image.h"
#include "Alignment.h"
#include <string>

namespace FastUI
{
	class FASTUI_API Renderer
	{
	public:
		virtual ~Renderer() = default;

		virtual void beginDraw(const Rect& DrawArea) = 0;
		virtual void endDraw() = 0;
		virtual void drawImage(const Image& Bitmap, const Rect& BoundingBox) = 0;

		virtual void drawLine(const Point& StartPoint, const Point& EndPoint, int LineWidth, const Color& LineColor) = 0;
		virtual void drawRectangle(float X, float Y, float Width, float Height, int LineWidth, const Color& RectangleColor, const Color& BackgroundColor) = 0;
		virtual void drawRectangle(const Rect& RectangleSize, int LineWidth, const Color& RectangleColor, const Color& BackgroundColor) = 0;

		virtual void drawRoundedRectangle(const Rect& RectangleSize, int LineWidth, int Round, const Color& RectangleColor, const Color& BackgroundColor) = 0;
		virtual void drawText(const std::wstring& Text, const Rect& RectSize, const Color& RectangleColor, unsigned int FontSize, Alignment TextAlignment = Alignment::Left) = 0;
	};
}