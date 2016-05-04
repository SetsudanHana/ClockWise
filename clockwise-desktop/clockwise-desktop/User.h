#pragma once
#include <string>
#include <cpprest\json.h>

class User
{
public:
	User(web::json::value& jsonObject)
	{
		UserId = jsonObject[L"id"].as_integer();
		Username = jsonObject[L"username"].as_string();
		EmailAddress = jsonObject[L"email"].as_string();
		Role = jsonObject[L"role"].as_string();
	}
	User(const std::wstring& NewUsername, const std::wstring& NewEmailAddress, const std::wstring& NewRole)
		: Username(NewUsername), EmailAddress(NewEmailAddress), Role(NewRole) {};

	int getUserId() const { return UserId; }
	const std::wstring& getUsername() const { return Username; }
	const std::wstring& getEmailAddress() const { return EmailAddress; }
	const std::wstring& getRole() const { return Role; }

private:
	int UserId;
	std::wstring Username;
	std::wstring EmailAddress;
	std::wstring Role;
};