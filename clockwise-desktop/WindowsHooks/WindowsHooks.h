#include "Export.h"
#include "..\clockwise-desktop\ISystemHooks.h"

class WINDOWSHOOKS_API WindowsSystemHooks : public ISystemHooks
{
public:
	~WindowsSystemHooks();
	HookErrorCodes startHooks() override;
	void stopHooks() override;

	unsigned int getMouseClickCount() override;
	unsigned int getMouseDistance() override;
	unsigned int getKeyboardClickCount() override;
};

