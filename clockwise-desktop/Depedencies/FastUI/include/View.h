#pragma once

#include "Object.h"
#include "Control.h"
#include "MouseEvent.h"
#include <string>
#include <vector>
#include <memory>

namespace FastUI
{
	class Window;
	class ViewContainer;

	class FASTUI_API View : public Object
	{
	public:
		View();
		View(const View& Other) = delete;
		virtual ~View() = default;
		void operator=(const View& Other) = delete;

		template<typename T>
		void addControl(T& NewControl)
		{
			NewControl.Parent = this;
			std::unique_ptr<T> ControlPointer;
			ControlPointer.reset(&NewControl);
			Controls.push_back(std::move(ControlPointer));
			invalidate();
		}	

		void close();
		void invalidate() override;
		void setParent(ViewContainer* NewParent);
		virtual void setVisible(bool IsVisible) override;

	protected:
		virtual void onDraw(Renderer& GraphicsRenderer) override;

		void onClick(const MouseEvent& ClickPoint);
		void onKeyboardPressed(const KeyboardEvent& Key);
		void onMousePressed(const MouseEvent& ClickPoint);
		void onMouseReleased(const MouseEvent& ClickPoint);
		void onMouseEnter(const MouseEvent& ClickPoint);
		void onMouseLeave(const MouseEvent& ClickPoint);
		void onMouseMove(const MouseEvent& ClickPoint);


	private:
		std::vector<std::unique_ptr<Control>> Controls;
		Control* FocusedControl;
		ViewContainer* Parent;

		friend class ViewContainer;
		friend class Window;
	};
}