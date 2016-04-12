#include "stdafx.h"
#include "LoginView.h"
#include "Authentication.h"

using namespace FastUI;

LoginView::LoginView(Authentication& AuthSystem) : AuthenticationSystem(AuthSystem)
{
	LoginTextBox = new TextBox(FastUI::Rect{ 35.0f, 175.0f, 315.0f, 225.0f }, L"Login");
	PasswordTextBox = new PasswordBox(FastUI::Rect{ 35.0f, 240.0f, 315.0f, 290.0f }, L"Password");
	LoginTextBox->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));
	PasswordTextBox->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));

	RememberMeCheckBox = new CheckBox(FastUI::Rect{ 95.0f, 400.0f, 315.0f, 415.0f }, L"Remember Me");
	FailedLoginInfo = new Label(FastUI::Rect{ 35.0f, 120.0f, 315.0f, 140.0f }, L"Login failed!");
	SignInButton = new Button(FastUI::Rect{ 35.0f, 335.0f, 315.0f, 380.0f }, L"Sign In");
	SignInButton->addEventHandler(Event::Click, [this](const Event& EventData)
	{
		hideFailedLoginInfo();

		auto loggedUser = AuthenticationSystem.login(LoginTextBox->getText(), PasswordTextBox->getText());
		if (!loggedUser)
		{
			displayFailedLoginInfo();
		}
		else
		{
			setVisible(false);
		}
	});

	FailedLoginInfo->setVisible(false);

	addControl(*LoginTextBox);
	addControl(*PasswordTextBox);
	addControl(*RememberMeCheckBox);
	addControl(*FailedLoginInfo);
	addControl(*SignInButton);
}

void LoginView::displayFailedLoginInfo()
{
	LoginTextBox->setBorderColor(FastUI::Color::Red());
	PasswordTextBox->setBorderColor(FastUI::Color::Red());
	FailedLoginInfo->setVisible(true);
	FailedLoginInfo->setTextColor(FastUI::Color::Red());
}

void LoginView::hideFailedLoginInfo()
{
	LoginTextBox->setBorderColor(Color(0.85f, 0.85f, 0.85f, 1.0f));
	PasswordTextBox->setBorderColor(Color(0.85f, 0.85f, 0.85f, 1.0f));
	//LoginTextBox->resetColors();
	//PasswordTextBox->resetColors();
	FailedLoginInfo->setVisible(false);
}
