#pragma once
#include "Config.h"

class EncyptedConfig : public Config
{
public:
	EncyptedConfig() = default;
	EncyptedConfig(const std::wstring& Filename) : Config(Filename)
	{
	}

	virtual bool open(const std::wstring& Filename) override
	{
		throw std::logic_error("The method or operation is not implemented.");
	}

	virtual bool save(const std::wstring& Filename) const override
	{
		throw std::logic_error("The method or operation is not implemented.");
	}

};