#pragma once

#ifdef FASTUI_EXPORTS
#define FASTUI_API __declspec(dllexport)
#else
#define FASTUI_API __declspec(dllimport)
#endif

#pragma warning(disable: 4251)