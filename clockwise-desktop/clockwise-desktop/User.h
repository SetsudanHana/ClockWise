#pragma once
#include <string>

class User
{
public:
	User(const std::wstring& Username) : Name(Username) {};

	const std::wstring& getUsername() { return Name; }

private:
	std::wstring Name;
};