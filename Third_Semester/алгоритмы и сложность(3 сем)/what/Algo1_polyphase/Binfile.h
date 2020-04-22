#pragma once
#include <fstream>

using namespace std;

class BinFile
{
public:
	string fileName;
	int numberOfRuns;
	int runLength;
	bool done;
	streampos currentPos;
	BinFile();
	BinFile(string fName, int runsNumber, int lengthOfRun);
};

