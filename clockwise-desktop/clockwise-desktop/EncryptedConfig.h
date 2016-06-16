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
		std::fstream File(Filename);
		if (!File.is_open())
		{
			return false;
		}

		std::string Line;
		while (std::getline(File, Line))
		{
			Line.erase(std::remove_if(Line.begin(), Line.end(), isspace), Line.end());
			removeComments(Line);

			auto assigmentPosition = Line.find('=');

			if (assigmentPosition == std::string::npos)
			{
				continue;
			}

			auto Key = Line.substr(0, assigmentPosition);
			auto Value = Line.substr(assigmentPosition + 1, Line.length() - assigmentPosition - 1);

			Values[Key] = Value;
		}

		File.close();
		return true;
	}

	virtual bool save(const std::wstring& Filename) const override
	{
		std::fstream File(Filename, std::ios::out | std::ios::trunc);
		if (!File.is_open())
		{
			Enviroment::createPath(Enviroment::getPath(Filename));
			File.open(Filename, std::ios::out | std::ios::trunc);

			if (!File.is_open())
			{
				OutputDebugStringW((L"Failed to open config file: " + Filename).c_str());
				return false;
			}
		}

		for (auto& MapItem : Values)
		{
			File << MapItem.first << "=" << MapItem.second << std::endl;
		}

		File.close();
		return true;
	}
private:
	virtual std::wstring encryptDecrypt(const std::wstring& text)
	{
		char key = 'C';
		std::wstring output = text;

		for (int i = 0; i < text.size(); i++) {
			output[i] = text[i] ^ key;
		}

		return output;
	}

	void removeComments(std::string& Line) const
	{
		auto DoubleSlash = Line.find("#");

		if (DoubleSlash != std::string::npos)
		{
			Line = Line.substr(0, DoubleSlash);
		}
	}

	std::unordered_map<std::string, std::string> Values;
};