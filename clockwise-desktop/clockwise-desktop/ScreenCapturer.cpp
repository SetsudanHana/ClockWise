#include "stdafx.h"
#include "ScreenCapturer.h"
#include "Logger.h"
#include "StringUtility.h"

#pragma comment (lib,"Gdiplus.lib")

using namespace Gdiplus;

int ScreenCapturer::GetEncoderClsid(const WCHAR* format, CLSID* pClsid)
{
	UINT num = 0;          // number of image encoders
	UINT size = 0;         // size of the image encoder array in bytes

	ImageCodecInfo* pImageCodecInfo = nullptr;

	GetImageEncodersSize(&num, &size);
	if (size == 0)
	{
		return -1;  // Failure
	}

	pImageCodecInfo = (ImageCodecInfo*)(malloc(size));
	if (pImageCodecInfo == nullptr)
	{
		return -1;  // Failure
	}

	GetImageEncoders(num, size, pImageCodecInfo);

	for (UINT j = 0; j < num; ++j)
	{
		if (wcscmp(pImageCodecInfo[j].MimeType, format) == 0)
		{
			*pClsid = pImageCodecInfo[j].Clsid;
			free(pImageCodecInfo);
			return j;  // Success
		}
	}

	free(pImageCodecInfo);
	return -1;  // Failure
}

void ScreenCapturer::BitmapToJpg(HBITMAP HBitmap, const std::wstring& Filename)
{
	Bitmap* bmp = Bitmap::FromHBITMAP(HBitmap, NULL);

	CLSID pngClsid;
	int result = GetEncoderClsid(L"image/jpeg", &pngClsid);
	if (result == -1)
	{
		LOG_ERROR("Failed to convert bitmap to jpeg");
	}

	auto status = bmp->Save(Filename.c_str(), &pngClsid, NULL);

	delete bmp;
}

bool ScreenCapturer::ScreenCapture(int X, int Y, int Width, int Height, const std::wstring& Filename)
{
	HDC hDc = CreateCompatibleDC(0);
	HBITMAP hBmp = CreateCompatibleBitmap(GetDC(0), Width, Height);
	SelectObject(hDc, hBmp);
	BitBlt(hDc, 0, 0, Width, Height, GetDC(0), X, Y, SRCCOPY);
	BitmapToJpg(hBmp, Filename);
	DeleteObject(hBmp);
	return true;
}

void ScreenCapturer::captureScreenAndSave(const std::wstring& Filename)
{
	GdiplusStartupInput gdiplusStartupInput;
	ULONG_PTR gdiplusToken;
	GdiplusStartup(&gdiplusToken, &gdiplusStartupInput, NULL);

	int X1 = 0;
	int Y1 = 0;
	int X2 = GetSystemMetrics(SM_CXSCREEN);
	int Y2 = GetSystemMetrics(SM_CYSCREEN);
	ScreenCapture(X1, Y1, X2 - X1, Y2 - Y1, Filename);

	GdiplusShutdown(gdiplusToken);
}
