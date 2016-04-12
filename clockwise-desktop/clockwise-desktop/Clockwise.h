#pragma once
#include "Application.h"
#include "Authentication.h"
#include "ApplicationWindow.h"

class Clockwise : public FastUI::Application
{
public:
	Clockwise() : FastUI::Application(), AppWindow(AuthenticationSystem)
	{
	};


private:
	Authentication AuthenticationSystem;
	ApplicationWindow AppWindow;
};