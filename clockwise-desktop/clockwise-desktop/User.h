#pragma once
#include <string>
#include <cpprest\json.h>

class User
{
public:
	User(web::json::value& jsonObject)
	{
		Username = jsonObject[L"username"].as_string();
		EmailAddress = jsonObject[L"email"].as_string();
		Role = jsonObject[L"role"].as_string();
	}
	User(const std::wstring& NewUsername, const std::wstring& NewEmailAddress, const std::wstring& NewRole)
		: Username(NewUsername), EmailAddress(NewEmailAddress), Role(NewRole) {};

	const std::wstring& getUsername() { return Username; }
	const std::wstring& getEmailAddress() { return EmailAddress; }
	const std::wstring& getRole() { return Role; }

private:
	std::wstring Username;
	std::wstring EmailAddress;
	std::wstring Role;
};