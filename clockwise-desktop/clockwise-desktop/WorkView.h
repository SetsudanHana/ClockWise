#pragma once
#include "FastUI.h"
#include "WorkObserver.h"
#include <memory>
#include <chrono>
#include "TimedExecutor.h"

class Authentication;
class LoginView;

class WorkView : public FastUI::View
{
public:
	WorkView(Authentication& AuthSystem, LoginView& AppLoginView);

	void onStartEndWork();

	virtual void onCreate() override;

private:
	WorkObserver* getBackgroundObserver();
	void onCountersUpdate();

	Authentication& AuthenticationSystem;
	LoginView& LogInView;
	std::unique_ptr<WorkObserver> BackgroundWorkObserver; 
	std::chrono::time_point<std::chrono::system_clock> StartTime;
	TimedExecutor TimeLabelUpdate;

	FastUI::Rectangle* BottomRect;
	FastUI::Label* InfoLabel;
	FastUI::Label* UsernameLabel;
	FastUI::Label* RoleLabel;
	FastUI::Button* LogoutButton;
	FastUI::Button* StartJobButton;
	FastUI::Label* KeyboardClicksLabel;
	FastUI::Label* MouseClicksLabel;
	FastUI::Label* MouseDeltaLabel;
	FastUI::Label* WorkTimeLabel;
};