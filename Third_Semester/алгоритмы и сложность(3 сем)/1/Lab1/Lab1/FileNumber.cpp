#include "FileNumber.h"



FileNumber::FileNumber(int fileIndex, int number):fileIndex(fileIndex),
number(number)
{
}

FileNumber::~FileNumber()
{
}

bool FileNumber::operator<(const FileNumber & obj) const
{
	return this->number > obj.number;
}
