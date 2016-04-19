#include "stdafx.h"
#include "Authentication.h"
#include "User.h"
#include "ServiceCommunicator.h"
#include "StringUtility.h"
#include "Logger.h"
#include <string>
#include <cpprest\json.h>

using namespace web;

Authentication::Authentication(ServiceCommunicator& serviceCommunicator) : LoggedUser(nullptr), communicator(serviceCommunicator)
{

}

Authentication::~Authentication()
{
	logout();
}

User* Authentication::getUser()
{
	return LoggedUser.get();
}

User* Authentication::login(const std::wstring& Username, const std::wstring& Password)
{
#ifdef _DEBUG
	if (Username == L"test" && Password == L"test")
	{
		LoggedUser = std::make_unique<User>(Username, L"Username@mail.com", L"TestRole");
		return LoggedUser.get();
	}
#endif

	try
	{
		json::value Credentials;
		Credentials[L"username"] = json::value::string(Username);
		Credentials[L"password"] = json::value::string(Password);

		auto LoginResponse = communicator.post("api/authenticate", Credentials);

		if(LoginResponse.has_field(L"token"))
		{
			std::unique_lock<std::shared_timed_mutex> lock(SessionMutex);
			Token = StringUtility::ws2s(LoginResponse[L"token"].as_string());
			communicator.registerAuthenticationSystem(*this);
			lock.unlock();

			return loadUser(Username);
		}
		else
		{
			LOG_ERROR("Failed to get user data(and should be able to!)");
			return nullptr;
		}
	}
	catch (const std::exception& e)
	{
		LOG_ERROR(e.what());
		return nullptr;
	}

	return nullptr;
}

void Authentication::logout()
{
	try
	{
		communicator.post("api/invalidatetoken", web::json::value());
	}
	catch (const std::exception& e)
	{
		LOG_ERROR("Couldn't logout: ");
		LOG_ERROR(e.what());
	}
	std::unique_lock<std::shared_timed_mutex> lock(SessionMutex);
	communicator.unregisterAuthenticationSystem();
	lock.unlock();

	Token = "";
	LoggedUser.reset();
}

const std::string& Authentication::getSessionCode()
{
	std::shared_lock<std::shared_timed_mutex> lock(SessionMutex);
	return Token;
}

bool Authentication::isLogged()
{
	return LoggedUser != nullptr;
}

User* Authentication::loadUser(const std::wstring& Username)
{
	std::map<std::string, std::string> Parameters;
	Parameters["username"] = StringUtility::ws2s(Username);
	auto userResponse = communicator.get("api/users", Parameters);

	if (userResponse.has_field(L"username"))
	{
		LoggedUser = std::make_unique<User>(userResponse);
		return LoggedUser.get();
	}
	else
	{
		return nullptr;
	}
}