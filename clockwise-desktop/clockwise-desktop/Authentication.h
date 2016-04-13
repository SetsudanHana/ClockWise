#pragma once
#include <string>

class User;
class ServiceCommunicator;

class Authentication
{
public:
	Authentication(ServiceCommunicator& communicator);
	~Authentication();

	User* getUser();
	User* login(const std::wstring& Username, const std::wstring& Password);
	void logout();

	bool isLogged();

	const std::string& getSessionCode() { return SessionCode; }

private:
	ServiceCommunicator& communicator;
	User* LoggedUser;
	std::string SessionCode;
};