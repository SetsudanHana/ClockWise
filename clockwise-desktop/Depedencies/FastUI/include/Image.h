#pragma once
#include "export.h"
#include <string>

namespace FastUI
{
	class FASTUI_API Image
	{
	public:
		Image(const std::wstring& Filename);
		virtual ~Image() = default;

		const std::wstring& getFilename() const;

	protected:
		std::wstring ImageFilename;
	};
}

