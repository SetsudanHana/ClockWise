#pragma once
#include "export.h"
#include <string>
#include <unordered_map>

struct PenKey
{
	COLORREF Color;
	unsigned int Width;

	bool operator==(const PenKey &other) const
	{
		return (Color == other.Color && Width == other.Width);
	}
};

namespace std
{
	template<>
	struct hash<PenKey>
	{
		std::size_t operator()(const PenKey& k) const
		{
			using std::size_t;
			using std::hash;

			return ((hash<unsigned long>()(k.Color)
				^ (hash<unsigned int>()(k.Width) << 1)) >> 1);
		}
	};
}

namespace FastUI
{
	class FASTUI_API GdiPenCache
	{
	public:
		~GdiPenCache();
		HPEN getPen(const COLORREF& Color, unsigned int Width);

	private:
		std::unordered_map<PenKey, HPEN> PenCache;
	};
}

