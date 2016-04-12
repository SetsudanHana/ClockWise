#pragma once
#include "export.h"
#include <string>
#include <windef.h>
#include <unordered_map>

struct FontKey
{
	std::wstring FontName;
	int FontHeight;

	bool operator==(const FontKey &other) const
	{
		return (FontName == other.FontName && FontHeight == other.FontHeight);
	}
};

namespace std
{
	template<>
	struct hash<FontKey>
	{
		std::size_t operator()(const FontKey& k) const
		{
			using std::size_t;
			using std::hash;

			return ((hash<std::wstring>()(k.FontName)
				^ (hash<int>()(k.FontHeight) << 1)) >> 1);
		}
	};
}

namespace FastUI
{
	class FASTUI_API GdiFontCache
	{
	public:
		~GdiFontCache();
		HFONT getFont(const std::wstring& FontName, int FontHeight);

	private:
		std::unordered_map<FontKey, HFONT> FontsCache;
	};
}