#pragma once
#include "FileListItem.h"
#include <string>
#include <vector>

class Enviroment
{
public:
	static std::wstring getAppDataPath();
	static void createFolder(const std::wstring& Path);
	static void createPath(const std::wstring& Path);
	static std::wstring getPath(const std::wstring& Filepath);
	static std::vector<FileListItem> getFilesInDirectory(const std::string& directory);
	static __int64 compareFileTime(const FILETIME& Left, const FILETIME& Right);
	static SYSTEMTIME getCurrentSystemTime();
	static FILETIME convertToFileTime(const SYSTEMTIME& SystemTime);
};