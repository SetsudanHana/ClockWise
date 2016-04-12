#pragma once
#include "export.h"

namespace FastUI
{
	class FASTUI_API ISerializable
	{
	public:
		virtual void deserialize() = 0;
	};
}