#pragma once
#include "Application.h"
#include "Authentication.h"
#include "ApplicationWindow.h"
#include "ServiceCommunicator.h"
#include "Config.h"
#include "..\WindowsHooks\WindowsHooks.h"

class Clockwise : public FastUI::Application
{
public:
	Clockwise();;

private:
	Config AppConfig;
	ServiceCommunicator Communicator;
	Authentication AuthenticationSystem;
	ApplicationWindow AppWindow;
};