#pragma once
#include "export.h"
#include <string>

namespace FastUI
{
	class FASTUI_API Clipboard
	{
	public:
		static std::string getString();
		static std::wstring getWString();
	};
}