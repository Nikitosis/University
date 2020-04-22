#include "Binfile.h"

BinFile::BinFile()
{
	fileName = "";
	int numberOfRuns = 0;
	int runLength = 0;
	done = false;
	currentPos = 0;
}

BinFile::BinFile(string fName, int runsNumber, int lengthOfRun)
{
	fileName = fName;
	int numberOfRuns = runsNumber;
	int runLength = lengthOfRun;
	done = false;
	currentPos = 0;
}
