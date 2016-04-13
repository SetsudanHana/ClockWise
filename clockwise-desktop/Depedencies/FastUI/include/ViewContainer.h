#pragma once
#include "export.h"

namespace FastUI
{
	class View;
	class Window;

	class FASTUI_API ViewContainer
	{
	public:
		virtual ~ViewContainer() = default;

		virtual void add(View& NewView) = 0;
		virtual View* getCurrent() = 0;
		virtual void invalidate();

		void setParent(Window* ParentWindow);
		void notifyChangeVisibility();
	private:
		Window* Parent;
	};
}