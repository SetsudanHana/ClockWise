#include "stdafx.h"
#include "Enviroment.h"
#include <shlobj.h>

std::wstring Enviroment::getAppDataPath()
{
	TCHAR szPath[MAX_PATH];
	if (SUCCEEDED(SHGetFolderPath(NULL, CSIDL_LOCAL_APPDATA, NULL, 0, szPath)))
	{
		return std::wstring(szPath) + L"\\Clockwise\\";
	}

	throw std::exception("Couln't get application data path");
}

void Enviroment::createFolder(const std::wstring& Path)
{
	CreateDirectory(Path.c_str(), NULL);
}

void Enviroment::createPath(const std::wstring& Path)
{
	SHCreateDirectoryEx(NULL, Path.c_str(), NULL);
}

std::wstring Enviroment::getPath(const std::wstring& Filepath)
{
	if (Filepath.length() == 0)
	{
		return L"";
	}

	auto found = Filepath.find_last_of(L"/\\");
	return Filepath.substr(0, found);
}
