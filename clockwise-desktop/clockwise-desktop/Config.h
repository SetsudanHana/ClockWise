#pragma once
#include <string>
#include <unordered_map>
#include <fstream>
#include <algorithm>
#include "StringUtility.h"
#include "Enviroment.h"
#include "Cryptography.h"

class Config
{
public:
	Config() = default;
	Config(const std::wstring& Filename)
	{
		open(Filename);
	}

	virtual bool open(const std::wstring& Filename)
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

	virtual bool save(const std::wstring& Filename) const
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

	std::string get(const std::string& Key, const std::string& DefaultValue = "") const
	{
		return Values.count(Key) > 0 ? Values.at(Key) : DefaultValue;
	}

	std::wstring getWString(const std::string& Key, const std::wstring& DefaultValue = L"") const
	{
		return Values.count(Key) > 0 ? StringUtility::s2ws(Values.at(Key)) : DefaultValue;
	}

	bool getBool(const std::string& Key, const bool DefaultValue = false) const
	{
		return Values.count(Key) > 0 ? std::stoi(Values.at(Key)) != 0 : DefaultValue;
	}

	int getInt(const std::string& Key, const int DefaultValue = 0) const
	{
		return Values.count(Key) > 0 ? std::stoi(Values.at(Key)) : DefaultValue;
	}

	float getFloat(const std::string& Key, const float DefaultValue = 0.0f) const
	{
		return Values.count(Key) > 0 ? std::stof(Values.at(Key)) : DefaultValue;
	}

	void set(const std::string& Key, const std::string& Value)
	{
		Values[Key] = Value;
	}

	void set(const std::string& Key, const std::wstring& Value)
	{
		Values[Key] = StringUtility::ws2s(Value);
	}

	void set(const std::string& Key, const bool Value)
	{
		Values[Key] = Value ? "1" : "0";
	}

	template<typename T>
	void set(const std::string& Key, const T& Value)
	{
		Values[Key] = std::to_string(Value);
	}

	void setEncryptedValue(const std::string& Key, const std::string& Value)
	{
		Values[Key] = Cryptography::encode(Value);
	}

	void setEncryptedValue(const std::string& Key, const std::wstring& Value)
	{
		Values[Key] = Cryptography::encode(StringUtility::ws2s(Value));
	}

	std::string getEncryptedValue(const std::string& Key)
	{
		return Cryptography::decode(Values[Key]);
	}

	std::wstring getEncryptedValueW(const std::string& Key)
	{
		return StringUtility::s2ws(Cryptography::decode(Values[Key]));
	}

private:
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
