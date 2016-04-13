#pragma once
#include <string>
#include <cpprest/http_client.h>
#include <cpprest/filestream.h>

class Authentication;

class ServiceCommunicator
{
public:
	ServiceCommunicator(const std::string& ServiceAddress);

	void registerAuthenticationSystem(Authentication& InitializedAuthentication);
	void unregisterAuthenticationSystem();

	web::json::value request(web::http::method requestMethod, web::json::value& Values, const std::string& UriString);
	web::json::value unauthenticatedRequest(web::http::method requestMethod, web::json::value& Values, const std::string& UriString);

private:
	Authentication* AuthenticationSystem;
	std::string Address;
};