#pragma once
#include <string>
#include <wtypes.h>
#include <vector>

class Base64
{
public:
	static std::string encode(BYTE const* buf, unsigned int bufLen);
	static std::vector<BYTE> decode(std::string const&);

private:
	static inline bool isBase64(BYTE c);
};
