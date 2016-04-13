#pragma once
#include <locale>
#include <codecvt>
#include <string>

class StringUtility
{
public:
	static std::wstring s2ws(const std::string& str)
	{
		std::wstring_convert<std::codecvt_utf8_utf16<wchar_t>> converter;
		return converter.from_bytes(str);
	}

	static std::string ws2s(const std::wstring& wstr)
	{
		std::wstring_convert<std::codecvt_utf8_utf16<wchar_t>> converter;
		return converter.to_bytes(wstr);
	}
};