#pragma once
#include <FastUI.h>

class Authentication;

class LoginView : public FastUI::View
{
public:
	LoginView(Authentication& AuthSystem);

private:
	void displayFailedLoginInfo();
	void hideFailedLoginInfo();
	void onSuccesfullLogin();
	void fillCredentialsFromSettings();

	std::wstring getUserConfigPath();

	Authentication& AuthenticationSystem;

	FastUI::Button* SignInButton;
	FastUI::TextBox* LoginTextBox;
	FastUI::PasswordBox* PasswordTextBox;
	FastUI::CheckBox* RememberMeCheckBox;
	FastUI::Label* FailedLoginInfo;

	const std::wstring UserConfigFilename = L"user_settings.cfg";
};