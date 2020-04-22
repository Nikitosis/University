#include "SortAlgo.h"



SortAlgo::SortAlgo()
{
}


SortAlgo::~SortAlgo()
{
}

void SortAlgo::generateRandomFile(string fName, int numbersAmount)
{
	ofstream fout;
	fout.open(fName, ofstream::trunc | ofstream::binary);
	for (int i = 0; i < numbersAmount; i++) {
		int val = rand() % 1000000;
		fout.write((char*)&val, sizeof(int));
	}
	fout.close();
}

void SortAlgo::printFile(string fName)
{
	ifstream fin(fName, std::ifstream::binary);
	while (true) {
		int elem;
		fin.read((char*)&elem, sizeof(int));
		if (fin.eof())
			return;
		cout << elem << " ";
	}
}

void SortAlgo::sort(string inputName,string outputName,int filesAm, int freeRam)
{
	int elementsInRam = freeRam / sizeof(int);
	int inputElementsAm = fileSize(inputName) / sizeof(int);
	int runsAmount = inputElementsAm / elementsInRam;
	if (inputElementsAm % elementsInRam != 0)
		runsAmount++;

	//write separate init function for first merge(put run length between runs)
	generateTable(runsAmount, filesAm);
	//distribute from

	//creating merge files
	createFiles(filesAm);

	int tableColumns = table[0].size();
	distibuteIntoFilesInit(inputName, tableColumns -1, elementsInRam);
	for (int i = tableColumns -1; i >= 1; i--) {
		int emptyFileIndex = getEmptyFileIndex(i);
		proceedMergeSort(i);
		if(i>1)
			distibuteIntoFiles(emptyFileIndex,i);
	}

	//write result to outputFile
	ofstream fout(outputName, ofstream::out | ofstream::trunc | ofstream::binary);
	ifstream fin(files[0].name,std::ifstream::binary);
	int runLength;
	fin.read((char*)&runLength, sizeof(int));
	for (int i = 0; i < runLength; i++) {
		int number;
		fin.read((char*)&number, sizeof(int));
		fout.write((char*)&number, sizeof(int));
	}

	fout.close();
	fin.close();
}

int SortAlgo::fileSize(string fName)
{
	ifstream fin(fName,std::ifstream::ate | std::ifstream::binary);
	int size = fin.tellg();
	fin.close();
	return size;
}

void SortAlgo::generateTable(int runsAmount, int filesAm)
{
	table.resize(filesAm);
	table[0].push_back(1);
	for (int i = 1; i < filesAm; i++)
		table[i].push_back(0);

	int column = 1;
	int prevMaxIndex = 0;
	int prevMax = 1;

	while (true) {
		//adding max to next column
		for (int i = 0; i < filesAm; i++) {
			if (i != prevMaxIndex)
				table[i].push_back(table[i][column - 1] + prevMax);
			else
				table[i].push_back(0);
		}

		//counting column sum
		int sum = 0;
		for (int i = 0; i < filesAm; i++)
			sum += table[i][column];

		if (sum >= runsAmount)
			break;

		//finding next max
		prevMax = 0;
		prevMaxIndex = 0;
		for(int i=0;i<filesAm;i++)
			if (table[i][column] > prevMax) {
				prevMax = table[i][column];
				prevMaxIndex = i;
			}
		column++;
	}
}

void SortAlgo::createFiles(int filesAm)
{
	for (int i = 0; i < filesAm; i++) {
		files.push_back(BinFile("File_" + to_string(i) + ".dat"));
	}
}

void SortAlgo::distibuteIntoFilesInit(string inputName,int curTableColumn,int elementsInRam)
{
	int filesAm = table.size();
	ifstream fin(inputName, std::ifstream::binary);
	for (int curFile = 0; curFile < filesAm; curFile++) {
		ofstream curFout(files[curFile].name, ofstream::out | ofstream::trunc | ofstream::binary);
		for (int curRun = 0; curRun < table[curFile][curTableColumn]; curRun++) {
			vector<int> buff;
			//read certain amount of elements from main input
			for (int curElement = 0; curElement < elementsInRam; curElement++) {
				if (fin.eof())
					break;
				int element;
				fin.read((char*)&element, sizeof(int));
				buff.push_back(element);
			}

			//sort buff
			std::sort(buff.begin(), buff.end());

			//write buff to output curFile

			//write size of the current run
			if (!buff.empty()) {
				int buffSize = buff.size();
				curFout.write((char*)&buffSize, sizeof(int));
			}
			//write buff elements
			for (int curElement : buff) {
				curFout.write((char*)&curElement, sizeof(int));
			}
		}
		curFout.close();
	}
}

void SortAlgo::distibuteIntoFiles(int inputIndex, int curTableColumn)
{
	int filesAm = table.size();
	ifstream fin(files[inputIndex].name,std::ifstream::binary);
	for (int curFile = 0; curFile < filesAm; curFile++) {
		if (curFile == inputIndex)
			continue;
		ofstream curFout(files[curFile].name, ofstream::out | ofstream::trunc | ofstream::binary);
		for (int curRun = 0; curRun < table[curFile][curTableColumn - 1]; curRun++) {
			vector<int> buff;
			//read certain amount of elements from main input
			int elementsInRun;
			fin.read((char*)&elementsInRun, sizeof(int));
			for (int curElement = 0; curElement < elementsInRun; curElement++) {
				if (fin.eof())
					break;
				int element;
				fin.read((char*)&element, sizeof(int));
				buff.push_back(element);
			}

			//write size of the current run
			if (!buff.empty()) {
				int buffSize = buff.size();
				curFout.write((char*)&buffSize, sizeof(int));
			}
			//write buff elements
			for (int curElement : buff) {
				curFout.write((char*)&curElement, sizeof(int));
			}
		}
		curFout.close();
	}
	fin.close();
	ofstream curFout(files[inputIndex].name, ofstream::out | ofstream::trunc | ofstream::binary);
	curFout.close();
}

bool SortAlgo::isFileEmpty(string fileName)
{
	ifstream file(fileName,std::ifstream::binary);
	int endSize = file.seekg(0, ios::end).tellg();
	if (endSize<= 4) {
		file.close();
		return true;
	}
	file.close();
	return false;
}

void SortAlgo::proceedMergeSort(int columnIndex)
{
	int filesAmount = table.size();
	int emptyFileIndex = getEmptyFileIndex(columnIndex);
	//calculating mergeRuns amount
	int mergeRuns = 0;
	for (int i = 0; i < filesAmount; i++)
		mergeRuns += table[i][columnIndex - 1];
	//initialize fins
	ofstream fout(files[emptyFileIndex].name, ofstream::out | ofstream::trunc | ofstream::binary);
	vector<ifstream> fins;
	for (int i = 0; i < filesAmount; i++)
		fins.push_back(ifstream(files[i].name,std::ifstream::binary));
	
	for (int i = 0; i < mergeRuns; i++)
		mergeSingleRun(columnIndex, fins,fout);

	//close fout and fins
	fout.close();
	for (int i = 0; i < fins.size(); i++)
		fins[i].close();
}

int SortAlgo::getEmptyFileIndex(int columnIndex)
{
	int filesAmount = files.size();
	//finding file with zero runs in table.This file will be our output file
	for (int i = 0; i < filesAmount; i++)
		if (table[i][columnIndex] == 0)
		{
			return i;
		}
}

//TODO: add ifstream array to parameters(to not open ifstream frequently)
void SortAlgo::mergeSingleRun(int columnIndex,vector<ifstream> &fins,ofstream &fout)
{
	int filesAmount = files.size();

	vector<int> runsLength(filesAmount);

	//calculating runs length in files
	for (int i = 0; i < filesAmount; i++) {
		int runLength;
		fins[i].read((char*)&runLength, sizeof(int));
		if (fins[i].eof())
			continue;
		runsLength[i] = runLength;
	}

	//write merged run length
	int lengthSum = 0;
	for (int i = 0; i < runsLength.size(); i++)
		lengthSum += runsLength[i];

	fout.write((char*)&lengthSum, sizeof(int));

	//filling numbersQueue with starting values
	priority_queue<FileNumber> numbersQueue;

	for (int i = 0; i < filesAmount; i++) {
		if (runsLength[i] > 0) {
			int number;
			fins[i].read((char*)&number, sizeof(int));
			numbersQueue.push(FileNumber(i, number));
			runsLength[i]--;
		}
	}

	//process merging

	while (!numbersQueue.empty()) {
		//get top number
		FileNumber maxNum = numbersQueue.top();
		numbersQueue.pop();
		//push another number from the run,where we get our top number
		if (runsLength[maxNum.fileIndex] > 0) {
			int fileIndex = maxNum.fileIndex;
			int number;
			fins[fileIndex].read((char*)&number, sizeof(int));
			numbersQueue.push(FileNumber(fileIndex, number));
			runsLength[fileIndex]--;
		}
		//put this number into output
		int result = maxNum.number;
		fout.write((char*)&result, sizeof(int));
	}
}


