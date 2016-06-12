#pragma once
#include <string>
#include <cpprest\json.h>

class User
{
public:
	User(web::json::value& jsonObject, const std::wstring& companyName)
	{
		UserId = jsonObject[L"id"].as_integer();
		Username = jsonObject[L"username"].as_string();
		EmailAddress = jsonObject[L"email"].as_string();
		Role = jsonObject[L"role"].as_string();
		CompanyName = companyName;
	}
	User(const std::wstring& NewUsername, const std::wstring& NewEmailAddress, const std::wstring& NewRole)
		: Username(NewUsername), EmailAddress(NewEmailAddress), Role(NewRole) {};

	int getUserId() const { return UserId; }
	const std::wstring& getUsername() const { return Username; }
	const std::wstring& getEmailAddress() const { return EmailAddress; }
	const std::wstring& getRole() const { return Role; }
	const std::wstring& getCompany() const { return CompanyName; }

private:
	int UserId;
	std::wstring Username;
	std::wstring EmailAddress;
	std::wstring Role;
	std::wstring CompanyName;
};