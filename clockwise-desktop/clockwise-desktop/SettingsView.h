#pragma once
#include <FastUI.h>
#include <string>

class SettingsView : public FastUI::View
{
public:
	SettingsView();

	void ReadSettings();

private:
	std::wstring getUserConfigPath();
	void saveSettings();

	FastUI::Label* SettingLabel;
	FastUI::Label* ErrorLabel;
	FastUI::Button* GoBackButton;
	FastUI::Button* SaveButton;
	FastUI::Rectangle* BottomRect;
	FastUI::Rectangle* TopRect;
	FastUI::SlideCheckBox* AutoRemovalSlideBox;
	FastUI::TextBox* DaysRemovalTextBox;
	FastUI::Label* DaysRemovalLabel;

	FastUI::SlideCheckBox* AutoRemovalSizeSlideBox;
	FastUI::TextBox* SizeRemovalTextBox;
	FastUI::Label* SizeRemovalLabel;

	const std::wstring UserConfigFilename = L"user_settings.cfg";
};