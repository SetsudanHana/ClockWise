#include "stdafx.h"
#include "SettingsView.h"
#include "Enviroment.h"
#include "Config.h"
#include "InputValidator.h"

using namespace FastUI;

SettingsView::SettingsView()
{
	GoBackButton = new Button(FastUI::Rect{ 10.0f, 565.0f, 90.0f, 590.0f }, L"Go back");
	GoBackButton->addEventHandler(Event::Click, [this](const Event& EventData)
	{
		setVisible(false);
	});

	BottomRect = new FastUI::Rectangle(Rect{ -1.0f, 550.0f, 360.0f, 605.0f });
	BottomRect->setBackgroundColor(Color(0.95f, 0.95f, 0.95f, 1.0f));
	BottomRect->setBorderColor(Color::LightGray());
	BottomRect->setBorderWidth(1);

	TopRect = new FastUI::Rectangle(Rect{ -1.0f, 1.0f, 360.0f, 46.0f });
	TopRect->setBackgroundColor(Color(0.95f, 0.95f, 0.95f, 1.0f));
	TopRect->setBorderColor(Color::LightGray());
	TopRect->setBorderWidth(1);

	SettingLabel = new FastUI::Label(Rect{ 10.0f, 3.0f, 150.0f, 40.0f }, L"Settings");
	SettingLabel->setTextAlignment(Alignment::Left);
	SettingLabel->setTextColor(Color::Gray());

	ErrorLabel = new FastUI::Label(Rect{ 5.0f, 200.0f, 335.0f, 216.0f }, L"");
	ErrorLabel->setTextAlignment(Alignment::Left);
	ErrorLabel->setTextColor(Color::Red());
	ErrorLabel->setVisible(false);

	DaysRemovalTextBox = new TextBox(FastUI::Rect{ 30.0f, 90.0f, 90.0f, 112.0f }, L"");
	DaysRemovalTextBox->setTextSize(16);

	DaysRemovalLabel = new Label(FastUI::Rect{ 95.0f, 92.0f, 400.0f, 108.0f }, L"Days (must be bigger than 0)");
	DaysRemovalLabel->setTextAlignment(Alignment::Left);
	DaysRemovalLabel->setTextColor(Color::DarkGray());

	AutoRemovalSlideBox = new SlideCheckBox(FastUI::Rect{ 10.0f, 65.0f, 390.0f, 80.0f }, L"Auto remove screenshots after: ");
	AutoRemovalSlideBox->addEventHandler(Event::Click, [this](const Event& EventData)
	{
		AutoRemovalSlideBox->setChecked(!AutoRemovalSlideBox->isChecked());
		DaysRemovalTextBox->setDisabled(!DaysRemovalTextBox->isDisabled());
		DaysRemovalLabel->setDisabled(!DaysRemovalLabel->isDisabled());
	});

	SizeRemovalTextBox = new TextBox(FastUI::Rect{ 30.0f, 150.0f, 90.0f, 172.0f }, L"");
	SizeRemovalTextBox->setTextSize(16);

	SizeRemovalLabel = new Label(FastUI::Rect{ 95.0f, 152.0f, 400.0f, 168.0f }, L"Megabytes (must be bigger than 0)");
	SizeRemovalLabel->setTextAlignment(Alignment::Left);
	SizeRemovalLabel->setTextColor(Color::DarkGray());

	AutoRemovalSizeSlideBox = new SlideCheckBox(FastUI::Rect{ 10.0f, 125.0f, 390.0f, 140.0f }, L"Auto remove screenshots after it exceeds: ");
	AutoRemovalSizeSlideBox->addEventHandler(Event::Click, [this](const Event& EventData)
	{
		AutoRemovalSizeSlideBox->setChecked(!AutoRemovalSizeSlideBox->isChecked());
		SizeRemovalTextBox->setDisabled(!SizeRemovalTextBox->isDisabled());
		SizeRemovalLabel->setDisabled(!SizeRemovalLabel->isDisabled());
	});

	SaveButton = new Button(FastUI::Rect{ 250.0f, 565.0f, 335.0f, 590.0f }, L"Save");
	SaveButton->addEventHandler(Event::Click, [this](const Event& EventData)
	{
		saveSettings();
	});

	ReadSettings();

	addControl(*AutoRemovalSlideBox);
	addControl(*BottomRect);
	addControl(*TopRect);
	addControl(*ErrorLabel);
	addControl(*SettingLabel);
	addControl(*GoBackButton);
	addControl(*SaveButton);
	addControl(*DaysRemovalTextBox);
	addControl(*DaysRemovalLabel);
	addControl(*SizeRemovalTextBox);
	addControl(*SizeRemovalLabel);
	addControl(*AutoRemovalSizeSlideBox);
}

std::wstring SettingsView::getUserConfigPath()
{
	return Enviroment::getAppDataPath() + UserConfigFilename;
}

void SettingsView::saveSettings()
{
	Config UserConfig(getUserConfigPath());
	std::wstring ErrorText;

	if (AutoRemovalSlideBox->isChecked())
	{
		if (InputValidator::validateInt(DaysRemovalTextBox->getText(), 1, 1024 * 1024))
		{
			UserConfig.set("EnabledAutoRemovalAfterDays", true);
			UserConfig.set("AutoRemovalScreenshotDays", DaysRemovalTextBox->getText());
		}
		else
		{
			ErrorText += L"Invalid value of 'Auto remove screenshots: " + DaysRemovalTextBox->getText() + L"'. ";
		}
	}
	else 
	{
		UserConfig.set("EnabledAutoRemovalAfterDays", false);
	}

	if (AutoRemovalSizeSlideBox->isChecked())
	{
		if (InputValidator::validateInt(SizeRemovalTextBox->getText(), 1, 1024 * 1024))
		{
			UserConfig.set("EnabledAutoRemovalAfterSize", true);
			UserConfig.set("AutoRemovalScreenshotSize", SizeRemovalTextBox->getText());
		}
		else
		{
			ErrorText += L"Invalid value of 'Auto remove screenshots size: " + SizeRemovalTextBox->getText() + L"'. ";
		}
	} 
	else
	{
		UserConfig.set("EnabledAutoRemovalAfterSize", false);
	}
	
	UserConfig.save(getUserConfigPath());

	if (!ErrorText.empty())
	{
		ErrorLabel->setText(ErrorText);
		ErrorLabel->setVisible(true);
	}
	else
	{
		ErrorLabel->setVisible(false);
		ReadSettings();
	}
}

void SettingsView::ReadSettings()
{
	Config UserSetting(getUserConfigPath());
	DaysRemovalTextBox->setText(StringUtility::s2ws(UserSetting.get("AutoRemovalScreenshotDays", "30")));
	SizeRemovalTextBox->setText(StringUtility::s2ws(UserSetting.get("AutoRemovalScreenshotSize", "100")));

	if (UserSetting.getBool("EnabledAutoRemovalAfterDays", false))
	{
		AutoRemovalSlideBox->setChecked(true);
		DaysRemovalTextBox->setDisabled(false);
		DaysRemovalLabel->setDisabled(false);
	}
	else
	{
		AutoRemovalSlideBox->setChecked(false);
		DaysRemovalTextBox->setDisabled(true);
		DaysRemovalLabel->setDisabled(true);
	}

	if (UserSetting.getBool("EnabledAutoRemovalAfterSize", false))
	{
		AutoRemovalSizeSlideBox->setChecked(true);
		SizeRemovalTextBox->setDisabled(false);
		SizeRemovalLabel->setDisabled(false);
	}
	else
	{
		AutoRemovalSizeSlideBox->setChecked(false);
		SizeRemovalTextBox->setDisabled(true);
		SizeRemovalLabel->setDisabled(true);
	}
}
