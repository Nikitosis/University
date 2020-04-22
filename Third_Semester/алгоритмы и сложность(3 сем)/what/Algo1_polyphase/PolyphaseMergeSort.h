#pragma once
#include <string>
#include <vector>
#include <fstream>
#include <cmath>
#include <queue>
#include "QuickSort.h"
#include "Binfile.h"

using namespace std;

struct NumberFile
{
	int number;
	int file;
	bool operator<(const NumberFile& rhs) const
	{
		return number > rhs.number;
	}
};

class PolyphaseMergeSort
{
private:
	bool fileIsEmpty(string fileName);
	vector<vector<int>> runsTable;
	vector<BinFile> binFiles;
	BinFile input;
	int numberOfFiles;
	int numberOfRuns;
	int runsTableLength;
	void clearFile(string fileName);
	//void refillQueue(priority_queue<NumberFile>& pq, int fileNumber, int &runLength);
	void InitializeRunsTable(int minColumnSum, int numberOfFiles);
	void InitializeFiles(string inputFileName, int numberOfElementsInRAM);
	int GetEmptyFileNumber(int currentColumn);
	void Merge(int currentColumn);
	int fSize(string filename);
public:
	PolyphaseMergeSort();
	void polyphaseMergeSort(string inputFileName, int numberOfElementsInRAM, int numberOfFiles);
	void OutFileInBlocks(int fileNumber);
};

