#pragma once

namespace FastUI
{
	struct FASTUI_API Point
	{
		Point() : X(0.0f), Y(0.0f) {};
		Point(float NewX, float NewY) : X(NewX), Y(NewY) {};

		float X, Y;
	};
}