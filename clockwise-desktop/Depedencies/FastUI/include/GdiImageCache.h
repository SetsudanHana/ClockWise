#pragma once
#include "export.h"
#include <string>
#include <windef.h>
#include <unordered_map>

namespace FastUI
{
	class FASTUI_API GdiImageCache
	{
	public:
		GdiImageCache();
		~GdiImageCache();
		HBITMAP getBitmap(const std::wstring& Filename);

	private:
		std::unordered_map<std::wstring, HBITMAP> BitmapCache;
		ULONG_PTR GdiplusToken;
	};
}