#pragma once

#include <string>

class Cryptography
{
public:
	static std::string encode(const std::string& Input);
	static std::string decode(const std::string& Input);
};