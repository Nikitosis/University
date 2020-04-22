#include <string>
#include <fstream>
#include <vector>
#include <algorithm>
#include <iostream>
#include <queue>
#include "FileNumber.h"
#include "BinFile.h"
#pragma once

using namespace std;
class SortAlgo
{
public:
	SortAlgo();
	~SortAlgo();
	static void generateRandomFile(string fName, int numbersAmount);
	static void printFile(string fName);
	void sort(string inputName,string outputName,int filesAm, int freeRam);
private:
	//table[i][j] contains amount of runs in i-th file during j-th iteration(from the end)
	vector<vector<int>> table;
	vector<BinFile> files;

	int fileSize(string fName);
	void generateTable(int runsAmount, int filesAm);
	void createFiles(int filesAm);
	void distibuteIntoFilesInit(string inputName, int curTableColumn, int elementsInRun);
	void distibuteIntoFiles(int inputIndex, int curTableColumn);
	static bool isFileEmpty(string inputName);
	void proceedMergeSort(int columnIndex);
	int getEmptyFileIndex(int columnIndex);
	void mergeSingleRun(int columnIndex, vector<ifstream> &fins, ofstream &fout);


};

