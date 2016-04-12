#pragma once
#include "export.h"
#include <string>
#include <unordered_map>
#include <windef.h>

namespace FastUI
{
	struct BrushKey
	{
		unsigned long Color;
	};

	class FASTUI_API GdiBrushCache
	{
	public:
		~GdiBrushCache();
		HBRUSH getBrush(const unsigned long& color);

	private:
		std::unordered_map<unsigned long, HBRUSH> BrushCache;
	};
}