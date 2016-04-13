#pragma once
#include "Application.h"
#include "Authentication.h"
#include "ApplicationWindow.h"
#include "ServiceCommunicator.h"
#include "Config.h"

class Clockwise : public FastUI::Application
{
public:
	Clockwise() : 
		FastUI::Application(), 
		AppConfig("app_config.cfg"),
		Communicator(AppConfig.get("service_endpoint", "http://localhost:8080")),
		AuthenticationSystem(Communicator),
		AppWindow(AuthenticationSystem)
	{
	};


private:
	Config AppConfig;
	ServiceCommunicator Communicator;
	Authentication AuthenticationSystem;
	ApplicationWindow AppWindow;
};