#include "stdafx.h"
#include "SettingsView.h"
#include "Enviroment.h"

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

	AutoRemovalSlideBox = new SlideCheckBox(FastUI::Rect{ 10.0f, 65.0f, 390.0f, 80.0f }, L"Auto remove screenshots after 30 days");

	addControl(*AutoRemovalSlideBox);
	addControl(*BottomRect);
	addControl(*TopRect);
	addControl(*SettingLabel);
	addControl(*GoBackButton);
}

std::wstring SettingsView::getUserConfigPath()
{
	return Enviroment::getAppDataPath() + UserConfigFilename;
}

bool SettingsView::isAutoRemoveChecked()
{
	return AutoRemovalSlideBox->isChecked();
}
