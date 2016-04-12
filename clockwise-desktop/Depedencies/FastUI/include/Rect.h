#pragma once

namespace FastUI
{
	struct FASTUI_API Rect
	{
		Rect() : Left(0.0f), Top(0.0f), Right(0.0f), Bottom(0.0f) {};
		Rect(float NewLeft, float NewTop, float NewRight, float NewBottom) : Left(NewLeft), Top(NewTop), Right(NewRight), Bottom(NewBottom) {};

		inline float Width() const
		{
			return Right - Left;
		}

		inline float Height() const
		{
			return Bottom - Top;
		}

		float Left, Top, Right, Bottom;
	};
}