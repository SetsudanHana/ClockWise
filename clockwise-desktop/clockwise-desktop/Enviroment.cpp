#include "stdafx.h"
#include "Enviroment.h"
#include <shlobj.h>
#include <shlwapi.h>

#pragma comment(lib, "Shlwapi.lib")

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

std::vector<FileListItem> Enviroment::getFilesInDirectory(const std::string& directory)
{
	HANDLE dir;
	WIN32_FIND_DATAA file_data;
	std::vector<FileListItem> out;

	if ((dir = FindFirstFileA((directory + "/*").c_str(), &file_data)) == INVALID_HANDLE_VALUE)
	{
		return std::vector<FileListItem>();
	}

	do
	{
		const std::string file_name = file_data.cFileName;
		const std::string full_file_name = file_name;
		const bool is_directory = (file_data.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) != 0;

		if (file_name[0] == '.')
		{
			continue;
		}

		FileListItem item;
		size_t lastindex = full_file_name.find_last_of(".");
		if (lastindex != std::string::npos)
		{
			item.Filename = full_file_name.substr(0, lastindex);
		}
		else
		{
			item.Filename = full_file_name;
		}

		if (is_directory)
		{
			item.Filename = "[" + item.Filename + "]";
		}

		item.Extension = is_directory ? "" : PathFindExtensionA(file_data.cFileName);
		item.Size = is_directory ? 0 : (file_data.nFileSizeHigh * (MAXDWORD + 1) + file_data.nFileSizeLow) / 1024 ; //To kilobytes
		item.CreationDate = file_data.ftCreationTime;

		out.push_back(item);
	} while (FindNextFileA(dir, &file_data));

	FindClose(dir);
	return out;
}

__int64 Enviroment::compareFileTime(const FILETIME& Left, const FILETIME& Right)
{
	ULARGE_INTEGER v_ui;
	__int64 v_right, v_left;
	v_ui.LowPart = Left.dwLowDateTime;
	v_ui.HighPart = Left.dwHighDateTime;
	v_right = v_ui.QuadPart;

	v_ui.LowPart = Right.dwLowDateTime;
	v_ui.HighPart = Right.dwHighDateTime;
	v_left = v_ui.QuadPart;

	return v_right - v_left;
}

SYSTEMTIME Enviroment::getCurrentSystemTime()
{
	SYSTEMTIME SystemTime;
	GetSystemTime(&SystemTime);
	
	return SystemTime;
}

FILETIME Enviroment::convertToFileTime(const SYSTEMTIME& SystemTime)
{
	FILETIME FileTime;
	SystemTimeToFileTime(&SystemTime, &FileTime);

	return FileTime;
}
