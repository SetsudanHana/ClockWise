#pragma once
#include <string>

class ScreenCapturer
{
public:
	static void captureScreenAndSave(const std::wstring& Filepath);

private:
	static int GetEncoderClsid(const WCHAR* format, CLSID* pClsid);
	static void BitmapToJpg(HBITMAP HBitmap, const std::wstring& Filename);
	static bool ScreenCapture(int X, int Y, int Width, int Height, const std::wstring& Filename);
};