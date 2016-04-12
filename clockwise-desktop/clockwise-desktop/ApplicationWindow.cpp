#include "stdafx.h"
#include "ApplicationWindow.h"
#include "WorkView.h"
#include "LoginView.h"

ApplicationWindow::ApplicationWindow(Authentication& AuthSystem) : Window(L"ClockWise", 360, 640), AuthenticationSystem(AuthSystem)
{
	setBackgroundColor(FastUI::Color(1.0f, 1.0f, 1.0f, 1.0f));

	AppLoginView = new LoginView(AuthenticationSystem);
	AppWorkView = new WorkView(AuthenticationSystem, *AppLoginView);
	addView(*AppWorkView);
	addView(*AppLoginView);
}
