#pragma once
#include <string>

class Enviroment
{
public:
	static std::wstring getAppDataPath();
	static void createFolder(const std::wstring& Path);
	static void createPath(const std::wstring& Path);
	static std::wstring getPath(const std::wstring& Filepath);
};