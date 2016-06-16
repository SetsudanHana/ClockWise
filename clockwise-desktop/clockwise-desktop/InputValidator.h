#pragma once
#include <string>

class InputValidator
{
public:

	static bool validateInt(const std::wstring& Input, int Min, int Max)
	{
		try
		{
			auto Value = std::stoi(Input);

			if (Value >= Min && Value <= Max)
			{
				return true;
			}
		}
		catch (const std::exception& Exception)
		{
			return false;
		}

		return false;
	}
};