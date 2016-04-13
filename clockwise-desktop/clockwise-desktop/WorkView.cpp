#include "stdafx.h"
#include "WorkView.h"
#include "Authentication.h"
#include "User.h"
#include "LoginView.h"

using namespace FastUI;

WorkView::WorkView(Authentication& AuthSystem, LoginView& AppLoginView) : AuthenticationSystem(AuthSystem), LogInView(AppLoginView)
{
	
}

void WorkView::onCreate()
{
	auto BottomRect = new FastUI::Rectangle(Rect{ -1.0f, 550.0f, 360.0f, 605.0f });
	BottomRect->setBackgroundColor(Color(0.95f, 0.95f, 0.95f, 1.0f));
	BottomRect->setBorderColor(Color::LightGray());
	BottomRect->setBorderWidth(1);

	auto InfoLabel = new Label(FastUI::Rect{ 0.0f, 120.0f, 360.0f, 135.0f }, L"You are not associated with any team :(");
	InfoLabel->setTextColor(Color::Gray());

	auto UsernameLabel = new Label(FastUI::Rect{ 10.0f, 560.0f, 200.0f, 580.0f }, AuthenticationSystem.getUser()->getUsername());
	UsernameLabel->setTextAlignment(Alignment::Left);
	UsernameLabel->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));

	auto RoleLabel = new Label(FastUI::Rect{ 10.0f, 580.0f, 200.0f, 590.0f }, AuthenticationSystem.getUser()->getRole());
	RoleLabel->setTextAlignment(Alignment::Left);
	RoleLabel->setTextColor(Color(0.25f, 0.25f, 0.25f, 1.0f));

	auto LogoutButton = new Button(FastUI::Rect{ 250.0f, 565.0f, 335.0f, 590.0f }, L"Logout");

	LogoutButton->addEventHandler(Event::Click, [this](const Event& EventData)
	{
		AuthenticationSystem.logout();
		LogInView.setVisible(true);
	});

	addControl(*BottomRect);
	addControl(*UsernameLabel);
	addControl(*LogoutButton);
	addControl(*InfoLabel);
	addControl(*RoleLabel);
}
