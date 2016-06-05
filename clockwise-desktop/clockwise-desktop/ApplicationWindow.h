#pragma once
#include "Window.h"

class Authentication;
class WorkView;
class LoginView;
class SettingsView;

class ApplicationWindow : public FastUI::Window
{
public:
	ApplicationWindow(Authentication& AuthSystem);

private:
	Authentication& AuthenticationSystem;
	WorkView* AppWorkView;
	LoginView* AppLoginView;
	SettingsView* AppSettingView;
};