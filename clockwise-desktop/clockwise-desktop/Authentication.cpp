#include "stdafx.h"
#include "Authentication.h"
#include "User.h"

#include <cpprest/http_client.h>
#include <cpprest/filestream.h>
#include <string>

using namespace utility;                    // Common utilities like string conversions
using namespace web;                        // Common features like URIs.
using namespace web::http;                  // Common HTTP functionality
using namespace web::http::client;          // HTTP client features
using namespace concurrency::streams;       // Asynchronous streams

Authentication::Authentication() : LoggedUser(nullptr)
{

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
		LoggedUser = new User(Username);
		return LoggedUser;
	}
#endif

	post();

	return nullptr;
}

std::wstring s2ws(const std::string& str)
{
	int size_needed = MultiByteToWideChar(CP_UTF8, 0, &str[0], (int)str.size(), NULL, 0);
	std::wstring wstrTo(size_needed, 0);
	MultiByteToWideChar(CP_UTF8, 0, &str[0], (int)str.size(), &wstrTo[0], size_needed);
	return wstrTo;
}

void Authentication::post()
{
	http_client client(U("http://localhost:8080/"));

	json::value user;
	user[L"Username"] = json::value::string(U("superadmin"));
	user[L"Password"] = json::value::string(U("123234345b"));

	http_request request(methods::POST);
	request.headers().set_content_type(L"application/json");
	request.set_request_uri(U("login"));
	request.set_body(user);

	auto response = client.request(request)
		.then([](http_response response) {
		return response.extract_string();
	});

	try
	{
		response.wait();
	}
	catch (const std::exception &e)
	{
		OutputDebugStringA((std::string("Error exception: ") + e.what() + std::string("\n")).c_str());
	}

	return;
}

void Authentication::logout()
{
	delete LoggedUser;
	LoggedUser = nullptr;
}

bool Authentication::isLogged()
{
	return LoggedUser != nullptr;
}
