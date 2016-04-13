#include "stdafx.h"
#include "Authentication.h"
#include "User.h"
#include "ServiceCommunicator.h"
#include "StringUtility.h"
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
	return LoggedUser;
}

User* Authentication::login(const std::wstring& Username, const std::wstring& Password)
{
#ifdef _DEBUG
	if (Username == L"test" && Password == L"test")
	{
		LoggedUser = new User(Username, L"Username@mail.com", L"TestRole");
		return LoggedUser;
	}
#endif

	json::value user;
	user[L"username"] = json::value::string(Username);
	user[L"password"] = json::value::string(Password);

	try
	{
		auto loginResponse = communicator.unauthenticatedRequest(web::http::methods::POST, user, "api/authenticate");

		if(loginResponse.has_field(L"token"))
		{
			SessionCode = StringUtility::ws2s(loginResponse[L"token"].as_string());
			communicator.registerAuthenticationSystem(*this);

			json::value jsonUsername;
			jsonUsername[L"username"] = json::value::string(Username);
			auto userResponse = communicator.request(web::http::methods::GET, jsonUsername, "api/users");

			if (userResponse.has_field(L"username"))
			{
				return LoggedUser = new User(userResponse);
			}
			else
			{
				return nullptr;
			}
		}
		else
		{
			return nullptr;
		}
	}
	catch (const std::exception& e)
	{
		return nullptr;
	}

	return nullptr;
}

void Authentication::logout()
{
	try
	{
		communicator.request(web::http::methods::POST, web::json::value(), "api/invalidatetoken");
	}
	catch (const std::exception& e)
	{
		OutputDebugStringA("Couldn't logout");
	}

	communicator.unregisterAuthenticationSystem();

	SessionCode = "";
	delete LoggedUser;
	LoggedUser = nullptr;
}

bool Authentication::isLogged()
{
	return LoggedUser != nullptr;
}
