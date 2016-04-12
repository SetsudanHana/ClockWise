#pragma once
#include "FastUI.h"

class Authentication;
class LoginView;

class WorkView : public FastUI::View
{
public:
	WorkView(Authentication& AuthSystem, LoginView& AppLoginView);

	virtual void onCreate() override;

private:
	Authentication& AuthenticationSystem;
	LoginView& LogInView;
};