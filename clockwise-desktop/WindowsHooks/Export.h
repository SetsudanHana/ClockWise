#pragma once
#ifdef WINDOWSHOOKS_EXPORTS
#define WINDOWSHOOKS_API __declspec(dllexport)
#else
#define WINDOWSHOOKS_API __declspec(dllimport)
#endif
