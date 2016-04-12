#pragma once
#include "export.h"

namespace FastUI
{
	struct FASTUI_API Color
	{
		Color() : R(0.0f), G(0.0f), B(0.0f), A(1.0f) {};
		Color(float NewRed, float NewGreen, float NewBlue, float NewAlpha) : R(NewRed), G(NewGreen), B(NewBlue), A(NewAlpha) {};
		Color(unsigned int IntColor) {};

		unsigned int toUint() { return 0; }
		Color operator-(const Color& Other)
		{
			return Color(R - Other.R, G - Other.G, B - Other.B, A - Other.A);
		}
		Color operator+(const Color& Other)
		{
			return Color(min(R + Other.R, 1.0f), min(G + Other.G, 1.0f), min(B + Other.B, 1.0f), min(A + Other.A, 1.0f));
		}

		float R;
		float G;
		float B;
		float A;

		static Color CornflowerBlue()
		{
			return Color{ 100.0f / 255.0f, 149.0f / 255.0f, 237.0f / 255.0f, 1.0f };
		}
		static Color LightGray()
		{
			return Color{ 211.0f / 255.0f, 211.0f / 255.0f, 211.0f / 255.0f, 1.0f };
		}
		static Color Gray()
		{
			return Color{ 0.5f, 0.5f, 0.5f, 1.0f };
		}
		static Color DarkGray()
		{
			return Color{ 169.0f / 255.0f, 169.0f / 255.0f, 169.0f / 255.0f, 1.0f };
		}
		static Color Red()
		{
			return Color{ 1.0f, 0.0f, 0.0f, 1.0f };
		}
	};
}