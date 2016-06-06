#include "stdafx.h"
#include "Clockwise.h"
#include "Logger.h"

Clockwise::Clockwise() :
	FastUI::Application(),
	AppConfig(L"app_config.cfg"),
	Communicator(AppConfig.get("service_endpoint", "https://localhost:8080")),
	AuthenticationSystem(Communicator),
	AppWindow(AuthenticationSystem)
{
}
