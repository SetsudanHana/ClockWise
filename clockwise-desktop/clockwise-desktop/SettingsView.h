#pragma once
#include <FastUI.h>
#include <string>

class SettingsView : public FastUI::View
{
public:
	SettingsView();

private:
	std::wstring getUserConfigPath();
	bool isAutoRemoveChecked();

	FastUI::Label* SettingLabel;
	FastUI::Button* GoBackButton;
	FastUI::Rectangle* BottomRect;
	FastUI::Rectangle* TopRect;
	FastUI::SlideCheckBox* AutoRemovalSlideBox;

	const std::wstring UserConfigFilename = L"user_settings.cfg";
};