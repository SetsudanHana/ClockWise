#pragma once
#include "ViewContainer.h"
#include <vector>

namespace FastUI
{
	class View;

	class FASTUI_API ViewStackContainer : public ViewContainer
	{
	public:
		virtual void add(View& NewView) override;
		void push(View& PushedView);
		View* pop();
		View* top();

		virtual View* getCurrent() override;

	private:
		std::vector<View*> ViewStack;
	};
}
