#include "stdafx.h"
#include "LoginView.h"
#include "Authentication.h"
#include "Config.h"
#include "Enviroment.h"

using namespace FastUI;

LoginView::LoginView(Authentication& AuthSystem) : AuthenticationSystem(AuthSystem)
{
	LoginTextBox = new TextBox(FastUI::Rect{ 35.0f, 175.0f, 315.0f, 225.0f }, L"Login");
	PasswordTextBox = new PasswordBox(FastUI::Rect{ 35.0f, 240.0f, 315.0f, 290.0f }, L"Password");
	RememberMeCheckBox = new CheckBox(FastUI::Rect{ 95.0f, 400.0f, 315.0f, 415.0f }, L"Remember Me");

	LoginTextBox->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));
	PasswordTextBox->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));
	fillCredentialsFromSettings();

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
			onSuccesfullLogin();
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
	FailedLoginInfo->setVisible(false);
}

void LoginView::onSuccesfullLogin()
{
	if (RememberMeCheckBox->isChecked())
	{
		Config UserSettings;
		UserSettings.set("username", LoginTextBox->getText());
		UserSettings.set("password", PasswordTextBox->getText());
		UserSettings.set("remember_me", true);
		UserSettings.save(getUserConfigPath());	
	}
	else
	{
		Config UserSettings;
		UserSettings.save(Enviroment::getAppDataPath() + UserConfigFilename); //This will truncate settings file
	}

	setVisible(false);
}

void LoginView::fillCredentialsFromSettings()
{
	Config UserSettings;
	if (UserSettings.open(Enviroment::getAppDataPath() + UserConfigFilename))
	{
		LoginTextBox->setText(UserSettings.getWString("username"));
		PasswordTextBox->setText(UserSettings.getWString("password"));
		RememberMeCheckBox->setChecked(UserSettings.getBool("remember_me"));
	}
}

std::wstring LoginView::getUserConfigPath()
{
	return Enviroment::getAppDataPath() + UserConfigFilename;
}
