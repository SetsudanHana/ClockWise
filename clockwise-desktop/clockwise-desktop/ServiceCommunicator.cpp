#include "stdafx.h"
#include "ServiceCommunicator.h"
#include "Authentication.h"
#include "StringUtility.h"
#include "Logger.h"

using namespace utility;                    
using namespace web;
using namespace web::http;
using namespace web::http::client;
using namespace concurrency::streams;

ServiceCommunicator::ServiceCommunicator(const std::string& ServiceAddress)
	: AuthenticationSystem(nullptr), WebClient(StringUtility::s2ws(ServiceAddress))
{
}

ServiceCommunicator::ServiceCommunicator(const std::string& ServiceAddress, Authentication& InitializedAuthentication)
	: WebClient(StringUtility::s2ws(ServiceAddress))
{
	registerAuthenticationSystem(InitializedAuthentication);
}

void ServiceCommunicator::registerAuthenticationSystem(Authentication& InitializedAuthentication)
{
	AuthenticationSystem = &InitializedAuthentication;
}

void ServiceCommunicator::unregisterAuthenticationSystem()
{
	AuthenticationSystem = nullptr;
}

web::json::value ServiceCommunicator::post(const std::string& UriString, web::json::value& Values, const std::map<std::string, std::string>& Parameters)
{
	http_request ServiceRequest(methods::POST);
	ServiceRequest.headers().set_content_type(L"application/json");
	addAuthenticationToken(ServiceRequest);

	uri_builder UriBuilder(StringUtility::s2ws(UriString));

	ServiceRequest.set_request_uri(UriBuilder.to_uri());
	ServiceRequest.set_body(Values);

	return sendRequest(ServiceRequest);
}

web::json::value ServiceCommunicator::get(const std::string& UriString, const std::map<std::string, std::string>& Parameters)
{
	http_request ServiceRequest(methods::GET);
	ServiceRequest.headers().set_content_type(L"application/json");
	addAuthenticationToken(ServiceRequest);

	uri_builder UriBuilder(StringUtility::s2ws(UriString));

	for (auto& Parameter : Parameters)
	{
		UriBuilder.append_query(StringUtility::s2ws(Parameter.first), StringUtility::s2ws(Parameter.second));
	}

	ServiceRequest.set_request_uri(UriBuilder.to_uri());

	return sendRequest(ServiceRequest);
}

void ServiceCommunicator::addAuthenticationToken(http_request& ServiceRequest) const
{
	if ((Authentication*)AuthenticationSystem != nullptr)
	{
		ServiceRequest.headers().add(L"ClockWise-Token", StringUtility::s2ws(((Authentication*)AuthenticationSystem)->getSessionCode()));
	}
}

web::json::value ServiceCommunicator::request(web::http::method RequestMethod, const std::string& UriString, const std::map<std::string, std::string>& Parameters, web::json::value& Values)
{
	if (RequestMethod == methods::GET)
	{
		return get(UriString, Parameters);
	}
	else if (RequestMethod == methods::POST)
	{
		return post(UriString, Values);
	}

	assert(false); //should not get here;
	return json::value();
}

web::json::value ServiceCommunicator::sendRequest(const web::http::http_request& ServiceRequest)
{
	auto JsonResponse = WebClient.request(ServiceRequest)
		.then([](http_response Response)
	{
		if (Response.status_code() != web::http::status_codes::OK)
		{
			auto ErrorMessage = "Request failed with code: " + std::to_string(Response.status_code()) 
				+ " and message: " + StringUtility::ws2s(Response.to_string());
			throw std::exception(ErrorMessage.c_str());
		}
		return Response.extract_json();
	});

	try
	{
		JsonResponse.wait();
	}
	catch (const std::exception &e)
	{
		LOG_ERROR(e.what());
		throw e;
	}

	return JsonResponse.get();
}
