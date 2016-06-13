#pragma once
#include <string>
#include <memory>
#include <shared_mutex>

class User;
class ServiceCommunicator;

class Authentication
{
public:
	Authentication(ServiceCommunicator& communicator);
	~Authentication();

	bool isLogged();

	User* getUser();

	User* login(const std::wstring& Username, const std::wstring& Password);
	void logout();

	const std::string& getSessionCode();

private:
	User* loadUser(const std::wstring& Username);
	std::wstring getCompany(int CompanyId);

	ServiceCommunicator& communicator;
	std::unique_ptr<User> LoggedUser;
	std::string Token;
	std::shared_timed_mutex SessionMutex;
};