#pragma once
#include <string>

class User;

class Authentication
{
public:
	Authentication();

	User* getUser();
	User* login(const std::wstring& Username, const std::wstring& Password);
	void post();
	void logout();

	bool isLogged();

private:
	User* LoggedUser;
};