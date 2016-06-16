#include "stdafx.h"
#include "Cryptography.h"
#include <stdio.h>
#include <windows.h>
#include <Wincrypt.h>
#include "Logger.h"
#define MY_ENCODING_TYPE  (PKCS_7_ASN_ENCODING | X509_ASN_ENCODING)

#pragma comment(lib, "crypt32.lib")

std::string Cryptography::encode(const std::string& toEncrypt)
{
	char key = 'K'; //Any char will work
	std::string output = toEncrypt;

	for (int i = 0; i < toEncrypt.size(); i++)
		output[i] = toEncrypt[i] ^ key;

	return output;
}

std::string Cryptography::decode(const std::string& toEncrypt)
{
	char key = 'K'; //Any char will work
	std::string output = toEncrypt;

	for (int i = 0; i < toEncrypt.size(); i++)
		output[i] = toEncrypt[i] ^ key;

	return output;
}

/*

std::string Cryptography::encode(const std::string& Input)
{
	HCRYPTMSG hMsg;
	BYTE* pbContent;     // a byte pointer to the message
	DWORD cbContent;     // the size of message
	DWORD cbEncodedBlob;
	BYTE *pbEncodedBlob;

	pbContent = (BYTE*) "Security is our only business";;
	cbContent = strlen((char *)pbContent) + 1;

	if (!(cbEncodedBlob = CryptMsgCalculateEncodedLength(
		MY_ENCODING_TYPE,       // message encoding type
		0,                      // flags
		CMSG_DATA,              // message type
		NULL,                   // pointer to structure
		NULL,                   // inner content object ID
		cbContent)))             // size of content
	{
		LOG_ERROR("Getting cbEncodedBlob length failed");
	}

	if (!(pbEncodedBlob = (BYTE *)malloc(cbEncodedBlob)))
	{
		LOG_ERROR("Memory allocation failed");
	}

	if (!(hMsg = CryptMsgOpenToEncode(
		MY_ENCODING_TYPE,        // encoding type
		0,                       // flags
		CMSG_DATA,               // message type
		NULL,                    // pointer to structure
		NULL,                    // inner content object ID
		NULL)))                  // stream information (not used)
	{
		LOG_ERROR("OpenToEncode failed");
	}

	if (!CryptMsgUpdate(
		hMsg,         // handle to the message
		pbContent,    // pointer to the content
		cbContent,    // size of the content
		TRUE))        // last call
	{
		LOG_ERROR("MsgUpdate failed");
	}

	if (!CryptMsgGetParam(
		hMsg,                      // handle to the message
		CMSG_BARE_CONTENT_PARAM,   // parameter type
		0,                         // index
		pbEncodedBlob,             // pointer to the BLOB
		&cbEncodedBlob))           // size of the BLOB
	{
		LOG_ERROR("MsgGetParam failed");
	}

	if (hMsg)
	{
		CryptMsgClose(hMsg);
	}

	auto Output = std::string((char*) pbEncodedBlob, cbEncodedBlob);
	if (pbEncodedBlob)
	{
		free(pbEncodedBlob);
	}

	return Output;
}

std::string Cryptography::decode(const std::string& Input)
{
	HCRYPTMSG hMsg;
	DWORD cbEncodedBlob;
	BYTE *pbEncodedBlob;

	DWORD cbDecoded;
	BYTE *pbDecoded;

	pbEncodedBlob = (BYTE*)Input.c_str();
	cbEncodedBlob = strlen((char *)pbEncodedBlob) + 1;

	if (hMsg = CryptMsgOpenToDecode(
		MY_ENCODING_TYPE,      // encoding type.
		0,                     // flags.
		CMSG_DATA,             // look for a data message.
		NULL,                  // cryptographic provider.
		NULL,                  // recipient information.
		NULL))                 // stream information.
	{
	}
	else
	{
		LOG_ERROR("OpenToDecode failed");
	}

	if (CryptMsgUpdate(
		hMsg,                 // handle to the message
		pbEncodedBlob,        // pointer to the encoded BLOB
		cbEncodedBlob,        // size of the encoded BLOB
		TRUE))                // last call
	{
	}
	else
	{
		LOG_ERROR("Decode MsgUpdate failed");
	}

	if (CryptMsgGetParam(
		hMsg,                  // handle to the message
		CMSG_CONTENT_PARAM,    // parameter type
		0,                     // index
		NULL,                  // address for returned 
							   // information
		&cbDecoded))           // size of the returned
							   // information
	{
	}
	else
	{
		LOG_ERROR("Decode CMSG_CONTENT_PARAM failed");
	}

	if (pbDecoded = (BYTE *)malloc(cbDecoded))
	{
	}
	else
	{
		LOG_ERROR("Decoding memory allocation failed.");
	}

	if (CryptMsgGetParam(
		hMsg,                  // handle to the message
		CMSG_CONTENT_PARAM,    // parameter type
		0,                     // index
		pbDecoded,             // address for returned 
							   // information
		&cbDecoded))           // size of the returned 
							   // information
	{
	}
	else
	{
		LOG_ERROR("Decode CMSG_CONTENT_PARAM #2 failed");
	}

	auto Output = std::string((char*)pbDecoded, cbDecoded);
	if (hMsg)
	{
		CryptMsgClose(hMsg);
	}
	if (pbDecoded)
	{
		free(pbDecoded);
	}

	return Output;
}
*/
