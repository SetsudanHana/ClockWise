#pragma once
#include "Object.h"
#include "Renderer.h"
#include "Control.h"
#include "ViewContainer.h"
#include <string>
#include <memory>
#include <vector>

namespace FastUI
{
	class View;

	class FASTUI_API Window : public Object
	{
	public:
		Window(const std::wstring& Title, unsigned int Width, unsigned int Height);
		virtual ~Window();

		template<typename T>
		void addView(T& NewView)
		{
			NewView.Parent = MainViewContainer.get();
			MainViewContainer->add(NewView);
			invalidate();
		}

		const Color& getBackgroundColor();
		void setBackgroundColor(const Color& NewColor);
		virtual void invalidate() override;

	protected:
		virtual void onDraw(Renderer& GraphicsRenderer) override;
		virtual void onClick(const MouseEvent& ClickPoint);
		virtual void onKeyboardPressed(const KeyboardEvent& Key);
		virtual void onMousePressed(const MouseEvent& ClickPoint);

		View* getView();

		virtual void onMouseReleased(const MouseEvent& ClickPoint);
		virtual void onMouseEnter(const MouseEvent& ClickPoint);
		virtual void onMouseLeave(const MouseEvent& ClickPoint);
		virtual void onMouseMove(const MouseEvent& ClickPoint);

		std::unique_ptr<Renderer> WindowRenderer;
		std::unique_ptr<ViewContainer> MainViewContainer;
		Color BackgroundColor;

	private:
		static LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam);

		static HWND hWnd;
		static Window* MainWindow;
	};
}

