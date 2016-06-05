#include "stdafx.h"
#include "ApplicationWindow.h"
#include "WorkView.h"
#include "LoginView.h"
#include "SettingsView.h"

ApplicationWindow::ApplicationWindow(Authentication& AuthSystem) : Window(L"ClockWise", 360, 640), AuthenticationSystem(AuthSystem)
{
	setBackgroundColor(FastUI::Color(1.0f, 1.0f, 1.0f, 1.0f));

	AppSettingView = new SettingsView();
	AppLoginView = new LoginView(AuthenticationSystem);
	AppWorkView = new WorkView(AuthenticationSystem, *AppLoginView, *AppSettingView);

	addView(*AppWorkView);
	addView(*AppLoginView);
	addView(*AppSettingView);

	AppSettingView->setVisible(false);
}
