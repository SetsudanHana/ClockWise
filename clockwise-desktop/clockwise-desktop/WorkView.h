#pragma once
#include "FastUI.h"
#include "WorkObserver.h"
#include <memory>
#include <chrono>
#include "TimedExecutor.h"

class Authentication;
class LoginView;
class SettingsView;

class WorkView : public FastUI::View
{
public:
	WorkView(Authentication& AuthSystem, LoginView& AppLoginView, SettingsView& NewSettingView);

	void onStartEndWork();
	virtual void onCreate() override;

private:
	WorkObserver* getBackgroundObserver();
	void onCountersUpdate();

	Authentication& AuthenticationSystem;
	LoginView& LogInView;
	SettingsView& AppSettingView;
	std::unique_ptr<WorkObserver> BackgroundWorkObserver; 
	std::chrono::time_point<std::chrono::system_clock> StartTime;
	TimedExecutor TimeLabelUpdate;

	FastUI::Image* ScreenshotImage;

	FastUI::PictureBox* ScreenshotPictureBox;
	FastUI::Rectangle* BottomRect;
	FastUI::Rectangle* WorkBackgroundRect;
	FastUI::Label* InfoLabel;
	FastUI::Label* UsernameLabel;
	FastUI::Label* RoleLabel;
	FastUI::Button* LogoutButton;
	FastUI::Button* StartJobButton;
	FastUI::Button* SettingsButton;
	FastUI::Label* KeyboardClicksLabel;
	FastUI::Label* MouseClicksLabel;
	FastUI::Label* MouseDeltaLabel;
	FastUI::Label* WorkTimeLabel;
};