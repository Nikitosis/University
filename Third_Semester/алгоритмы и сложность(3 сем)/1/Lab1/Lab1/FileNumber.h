#pragma once
struct FileNumber
{
	int fileIndex;
	int number;
	FileNumber(int fileIndex,int number);
	~FileNumber();
	bool operator<(const FileNumber& obj) const;
};

