#pragma once
#include "IDeserializer.h"
#include <string>

namespace FastUI
{
	class FASTUI_API XMLDeserializer : public IDeserializer
	{
	public:
		XMLDeserializer(const std::string& Filename);
	};
}