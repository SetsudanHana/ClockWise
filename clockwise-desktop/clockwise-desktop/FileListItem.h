#pragma once
#include <string>
#include <minwinbase.h>

struct FileListItem
{
	std::string Filename;
	std::string Extension;
	unsigned int Size;
	FILETIME CreationDate;
};