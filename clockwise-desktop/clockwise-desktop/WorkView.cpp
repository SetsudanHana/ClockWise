#include "stdafx.h"
#include "WorkView.h"
#include "Authentication.h"
#include "User.h"
#include "LoginView.h"
#include "Config.h"
#include <string>

using namespace FastUI;

WorkView::WorkView(Authentication& AuthSystem, LoginView& AppLoginView) 
	: AuthenticationSystem(AuthSystem), LogInView(AppLoginView)
{
	BottomRect = new FastUI::Rectangle(Rect{ -1.0f, 550.0f, 360.0f, 605.0f });
	BottomRect->setBackgroundColor(Color(0.95f, 0.95f, 0.95f, 1.0f));
	BottomRect->setBorderColor(Color::LightGray());
	BottomRect->setBorderWidth(1);

	InfoLabel = new Label(FastUI::Rect{ 0.0f, 120.0f, 360.0f, 135.0f }, L"You are not associated with any team :(");
	InfoLabel->setTextColor(Color::Gray());

	UsernameLabel = new Label(FastUI::Rect{ 10.0f, 560.0f, 200.0f, 580.0f });
	UsernameLabel->setTextAlignment(Alignment::Left);
	UsernameLabel->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));

	RoleLabel = new Label(FastUI::Rect{ 10.0f, 580.0f, 200.0f, 590.0f });
	RoleLabel->setTextAlignment(Alignment::Left);
	RoleLabel->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));

	LogoutButton = new Button(FastUI::Rect{ 250.0f, 565.0f, 335.0f, 590.0f }, L"Logout");

	LogoutButton->addEventHandler(Event::Click, [this](const Event& EventData)
	{
		getBackgroundObserver()->terminate();
		AuthenticationSystem.logout();
		LogInView.setVisible(true);
		Created = false;
	});

	KeyboardClicksLabel = new Label(FastUI::Rect{ 20.0f, 450.0f, 330.0f, 465.0f }, L"Keyboard click per minute: 0");
	KeyboardClicksLabel->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));

	MouseClicksLabel = new Label(FastUI::Rect{ 20.0f, 480.0f, 330.0f, 495.0f }, L"Mouse click per minute: 0");
	MouseClicksLabel->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));

	MouseDeltaLabel = new Label(FastUI::Rect{ 20.0f, 510.0f, 330.0f, 525.0f }, L"Mouse travelled distance per minute: 0");
	MouseDeltaLabel->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));

	WorkTimeLabel = new Label(FastUI::Rect{ 20.0f, 430.0f, 330.0f, 445.0f }, L"Work time: ");
	WorkTimeLabel->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));

	StartJobButton = new Button(FastUI::Rect{ 130.0f, 405.0f, 230.0f, 430.0f }, L"Start Work");
	StartJobButton->addEventHandler(Event::Click, [this](const Event& EventData)
	{
		onStartEndWork();
	});

	WorkBackgroundRect = new FastUI::Rectangle(Rect{ 20.0f, 400.0f, 330.0f, 530.0f });
	WorkBackgroundRect->setBackgroundColor(Color(0.95f, 0.95f, 0.95f, 1.0f));
	WorkBackgroundRect->setBorderColor(Color::LightGray());
	WorkBackgroundRect->setBorderWidth(1);

	addControl(*WorkBackgroundRect);
	addControl(*WorkTimeLabel);
	addControl(*KeyboardClicksLabel);
	addControl(*MouseClicksLabel);
	addControl(*MouseDeltaLabel);
	addControl(*StartJobButton);
	addControl(*BottomRect);
	addControl(*UsernameLabel);
	addControl(*LogoutButton);
	addControl(*InfoLabel);
	addControl(*RoleLabel);
}

void WorkView::onCreate()
{
	UsernameLabel->setText(AuthenticationSystem.getUser()->getUsername());
	RoleLabel->setText(AuthenticationSystem.getUser()->getRole());

	KeyboardClicksLabel->setVisible(false);
	MouseClicksLabel->setVisible(false);
	MouseDeltaLabel->setVisible(false);
	WorkTimeLabel->setVisible(false);
	TimeLabelUpdate.stop();
	StartJobButton->setText(L"Start Work");
}

WorkObserver* WorkView::getBackgroundObserver()
{
	if (!BackgroundWorkObserver)
	{
		Config AppConfig(L"app_config.cfg");
		BackgroundWorkObserver = std::make_unique<WorkObserver>(AppConfig.get("service_endpoint", "http://localhost:8080"), AuthenticationSystem);
		BackgroundWorkObserver->setUpdateNotifier(std::bind(&WorkView::onCountersUpdate, this));
	}
	
	return BackgroundWorkObserver.get();
}

void WorkView::onCountersUpdate()
{
	KeyboardClicksLabel->setText(L"Keyboard click per minute: " + std::to_wstring(BackgroundWorkObserver->getLastKeybordClickPerMinute()));
	MouseClicksLabel->setText(L"Mouse click per minute: " + std::to_wstring(BackgroundWorkObserver->getLastMouseClickPerMinute()));
	MouseDeltaLabel->setText(L"Mouse travelled distance per minute: " + std::to_wstring(BackgroundWorkObserver->getLastMouseDistancePerMinute()));
}

void WorkView::onStartEndWork()
{
	if (getBackgroundObserver()->isRunning())
	{
		getBackgroundObserver()->terminate();
		TimeLabelUpdate.stop();
		StartJobButton->setText(L"Start Work");
		KeyboardClicksLabel->setVisible(false);
		MouseClicksLabel->setVisible(false);
		MouseDeltaLabel->setVisible(false);
		WorkTimeLabel->setVisible(false);
	}
	else
	{
		getBackgroundObserver()->start();
		StartJobButton->setText(L"End Work");
		KeyboardClicksLabel->setVisible(true);
		MouseClicksLabel->setVisible(true);
		MouseDeltaLabel->setVisible(true);
		WorkTimeLabel->setVisible(true);
		StartTime = std::chrono::system_clock::now();

		using namespace std::chrono_literals;
		TimeLabelUpdate.start(1s, [&]()
		{
			auto WorkTime = std::chrono::system_clock::now() - StartTime;
			auto Seconds = std::chrono::duration_cast<std::chrono::seconds>(WorkTime).count();
			auto Hours = Seconds / 3600;
			auto Minutes = (Seconds % 3600) / 60;
			auto LastSeconds = (Seconds % 60);
			auto WorkTimeString = L"Work time: " + std::to_wstring(Hours) + L"h " + std::to_wstring(Minutes) + L"min " + std::to_wstring(LastSeconds) + L"s";

			WorkTimeLabel->setText(WorkTimeString);
		});
	}
}
