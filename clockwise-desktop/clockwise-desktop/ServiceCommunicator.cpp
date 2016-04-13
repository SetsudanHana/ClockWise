#include "stdafx.h"
#include "ServiceCommunicator.h"
#include "Authentication.h"
#include "StringUtility.h"

//#include <cpprest/http_client.h>
//#include <cpprest/filestream.h>

using namespace utility;                    
using namespace web;
using namespace web::http;
using namespace web::http::client;
using namespace concurrency::streams;

ServiceCommunicator::ServiceCommunicator(const std::string& ServiceAddress)
	: Address(ServiceAddress), AuthenticationSystem(nullptr)
{

}

void ServiceCommunicator::registerAuthenticationSystem(Authentication& InitializedAuthentication)
{
	AuthenticationSystem = &InitializedAuthentication;
}

void ServiceCommunicator::unregisterAuthenticationSystem()
{
	AuthenticationSystem = nullptr;
}

web::json::value ServiceCommunicator::request(web::http::method requestMethod, web::json::value& Values, const std::string& UriString)
{
	if (!AuthenticationSystem)
	{
		throw std::exception("Authentication system is not initialized. Cannot build authorized request.");
	}

	http_client httpClient(StringUtility::s2ws(Address));

	if (requestMethod == methods::GET)
	{
		http_request service_request(requestMethod);
		//service_request.headers().set_content_type(L"application/json");

		uri_builder builder(StringUtility::s2ws(UriString));
		builder.append_query(L"token", StringUtility::s2ws(AuthenticationSystem->getSessionCode()));
		builder.append_query(L"username", Values[L"username"].as_string());

		service_request.set_request_uri(builder.to_uri());

		auto jsonResponse = httpClient.request(service_request)
			.then([](http_response response)
		{
			return response.extract_json();
		});

		try
		{
			jsonResponse.wait();
		}
		catch (const std::exception &e)
		{
			OutputDebugStringA(e.what());
			throw e;
		}

		return jsonResponse.get();
	}
	else
	{
		http_request service_request(requestMethod);
		service_request.headers().set_content_type(L"application/json");
		service_request.set_request_uri(
			uri_builder(StringUtility::s2ws(UriString))
			.append_query(L"token", StringUtility::s2ws(AuthenticationSystem->getSessionCode()))
			.to_uri());

		service_request.set_body(Values);

		auto jsonResponse = httpClient.request(service_request)
			.then([](http_response response)
		{
			return response.extract_json();
		});

		try
		{
			jsonResponse.wait();
		}
		catch (const std::exception &e)
		{
			OutputDebugStringA(e.what());
			throw e;
		}

		return jsonResponse.get();
	}

}

web::json::value ServiceCommunicator::unauthenticatedRequest(web::http::method requestMethod, web::json::value& Values, const std::string& UriString)
{
	http_client httpClient(StringUtility::s2ws(Address));

	http_request service_request(requestMethod);
	service_request.headers().set_content_type(L"application/json");
	service_request.set_request_uri(uri_builder(StringUtility::s2ws(UriString)).to_uri());
	service_request.set_body(Values);

	auto jsonResponse = httpClient.request(service_request)
		.then([](http_response response) 
	{
		return response.extract_json();
	});

	try
	{
		jsonResponse.wait();
	}
	catch (const std::exception &e)
	{
		OutputDebugStringA(e.what());
		throw e;
	}

	return jsonResponse.get();
}
