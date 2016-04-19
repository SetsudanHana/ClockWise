#pragma once
#include <string>
#include <cpprest/http_client.h>
#include <cpprest/filestream.h>
#include <map>

class Authentication;

class ServiceCommunicator
{
public:
	ServiceCommunicator(const std::string& ServiceAddress);
	ServiceCommunicator(const std::string& ServiceAddress, Authentication& InitializedAuthentication);

	void registerAuthenticationSystem(Authentication& InitializedAuthentication);
	void unregisterAuthenticationSystem();

	web::json::value post(const std::string& UriString, web::json::value& Values, const std::map<std::string, std::string>& Parameters = std::map<std::string, std::string>());
	web::json::value get(const std::string& UriString, const std::map<std::string, std::string>& Parameters);

	web::json::value request(web::http::method RequestMethod, const std::string& UriString, const std::map<std::string, std::string>& Parameters, web::json::value& Values);

private:
	void addAuthenticationToken(web::http::http_request& ServiceRequest) const;
	web::json::value sendRequest(const web::http::http_request& ServiceRequest);

	Authentication* AuthenticationSystem;
	web::http::client::http_client WebClient;
};