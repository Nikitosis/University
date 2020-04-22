#include "PolyphaseMergeSort.h"
#include <iostream>



bool PolyphaseMergeSort::fileIsEmpty(string fileName)
{
	ifstream file(fileName, std::ios::binary);
	file.seekg(0, std::ios::end);
	if (file.tellg() == 4) {
		file.close();
		return true;
	}
	file.close();
	return false;
}

void PolyphaseMergeSort::clearFile(string fileName)
{
	fstream f1;
	f1.open(fileName, std::ofstream::out | std::ofstream::trunc);
	f1.close();
}

void PolyphaseMergeSort::InitializeRunsTable(int minColumnSum, int numberOfFiles)
{
	runsTable.resize(numberOfFiles);
	int currentSum = 1;
	int prevMax = 1;
	int currentMax = 1;
	int prevMaxRow = 0;
	int currentMaxRow = 0;
	int currentColumn = 1;

	runsTable[0].push_back(1);
	for (rsize_t i = 1; i < runsTable.size(); i++) {
		runsTable[i].push_back(0);
	}

	while (currentSum + 1 < minColumnSum) {
		prevMax = currentMax;
		prevMaxRow = currentMaxRow;
		currentMax = 0;
		currentMaxRow = 0;
		currentSum = 0;
		for (size_t i = 0; i < runsTable.size(); i++) {
			if (i == prevMaxRow) runsTable[i].push_back(0);
			else {
				runsTable[i].push_back(runsTable[i][currentColumn - 1] + prevMax);
				currentSum += runsTable[i][currentColumn];
				if (runsTable[i][currentColumn] > currentMax) {
					currentMax = runsTable[i][currentColumn];
					currentMaxRow = i;
				}
			}
		}
		currentColumn++;
	}
	numberOfRuns = currentSum;
	runsTableLength = runsTable[0].size() - 1;
}

void PolyphaseMergeSort::InitializeFiles(string inputFileName, int numberOfElementsInRAM) {
	// Initialize
	ifstream f;
	f.open(inputFileName, ios::binary | ios::ate);
	int inputIntCount = f.tellg() / 4;
	f.close();
	int runsNumber = inputIntCount / numberOfElementsInRAM;
	if (inputIntCount - numberOfElementsInRAM * runsNumber > 0) runsNumber++;
	InitializeRunsTable(runsNumber, numberOfFiles);
	input = BinFile(inputFileName, 1, inputIntCount);

	//Initialize binFiles vector
	int lengthOfRun = numberOfElementsInRAM;
	// Maybe add 
	for (int curF = 0; curF < numberOfFiles; curF++) {
		binFiles.push_back(BinFile("F" + to_string(curF) + ".dat", runsTable[curF][runsTableLength - 1], lengthOfRun));
	}

	// Sort and write first iteration runs
	vector<int> buff;
	int element;
	QuickSort sort;
	int runLength = numberOfElementsInRAM;
	int currentRun = 0;

	ifstream in(input.fileName, fstream::binary);
	for (int currFile = 0; currFile < numberOfFiles; currFile++) {
		fstream f(binFiles[currFile].fileName, ofstream::out | ofstream::trunc | ofstream::binary);
		for (int j = runsTable[currFile][runsTableLength]; j > 0; j--) {
			buff.clear();
			for (int k = 0; k < runLength; k++) {
				in.read((char*)& element, sizeof(int));
				if (!in.eof()) buff.push_back(element);
				else break;
			}
			if (buff.size() > 0) sort.quickSort(buff, 0, buff.size() - 1);
			else break;
			int buffSize = buff.size();
			if (buff.empty()) buffSize = 0;
			else {
				f.write((char*)& buffSize, sizeof(int));
				f.write((char*)& buff[0], (buff.size() * sizeof(int)));
			}
			binFiles[currFile].runLength = buffSize;
			currentRun++;
			buff.clear();
			if (currentRun > numberOfRuns) throw out_of_range("Went out of range!");
		}
		element = 0;
		f.write((char*)& element, sizeof(int));
		f.close();
	}
	in.close();
}

int PolyphaseMergeSort::GetEmptyFileNumber(int currentColumn)
{
	int emptyFileNumber = 0;
	for (int i = 0; i < numberOfFiles; i++) {
		if (runsTable[i][currentColumn] == 0) {
			emptyFileNumber = i;
			return emptyFileNumber;
		}
	}
}

//void PolyphaseMergeSort::refillQueue(priority_queue<NumberFile>& pq, int fileNumber, int& runLength)
//{
//	if (runLength < 1) return;
//	int buff;
//	NumberFile numFile;
//	output[fileNumber].read((char*)& buff, sizeof(buff));
//	numFile.number = buff;
//	numFile.file = fileNumber;
//	pq.push(numFile);
//	runLength--;
//}

void PolyphaseMergeSort::Merge(int currentColumn)
{
	priority_queue<NumberFile> currentElements;
	int emptyFileNumber = GetEmptyFileNumber(currentColumn);
	fstream output;
	output.open(binFiles[emptyFileNumber].fileName, std::ofstream::out | std::ofstream::trunc | std::ofstream::binary);
	vector<int> currentRunLength; // How many elements left in a run
	currentRunLength.resize(numberOfFiles);
	int mergedRunLength;

	int mergeRepeats = runsTable[emptyFileNumber][currentColumn - 1];
	int buff;
	NumberFile numFile;

	ifstream* files = new ifstream[numberOfFiles];
	for (int currFile = 0; currFile < numberOfFiles; currFile++) {
		if (currFile != emptyFileNumber) {
			files[currFile].open(binFiles[currFile].fileName, fstream::binary);
			files[currFile].seekg(binFiles[currFile].currentPos);
		}
	}

	for (int m = 0; m < mergeRepeats; m++)
	{
		
		mergedRunLength = 0;
		for (int currFile = 0; currFile < numberOfFiles; currFile++) {
			if (!binFiles[currFile].done && currFile != emptyFileNumber && !fileIsEmpty(binFiles[currFile].fileName)) {
				int len;
				//cout << "file = " << (string)binFiles[currFile].fileName << " ";
				files[currFile].read((char*)& len, sizeof(int));
				//cout << " m = " << m << " len= " << len << endl;
				currentRunLength[currFile] = len;
				/*int a;
				files[currFile].read((char*)& a, sizeof(int));*/
				if (len > 0) {
					mergedRunLength += currentRunLength[currFile];
					files[currFile].read((char*)& buff, sizeof(int));
					numFile.number = buff;
					numFile.file = currFile;
					currentElements.push(numFile);
					currentRunLength[currFile]--;
				}
				else binFiles[currFile].done = true;
			}
		}
		output.write((char*)& mergedRunLength, sizeof(int));
		//cout << "Merged run length = " << mergedRunLength<<endl;
		while (mergedRunLength > 0) {
			if (currentElements.empty()) 
				break;
			numFile.number = currentElements.top().number;
			numFile.file = currentElements.top().file;
			currentElements.pop();
			output.write((char*)& numFile.number, sizeof(int));
			//cout << numFile.number << " ";
			mergedRunLength--;
			// Refill queue
		if (currentRunLength[numFile.file] > 0) {
				files[numFile.file].read((char*)& buff, sizeof(buff));
				numFile.number = buff;
				numFile.file = numFile.file;
				currentElements.push(numFile);
				currentRunLength[numFile.file]--;
			}
		}
		//cout << endl;
	}
	buff = 0;
	output.write((char*)& buff, sizeof(int));
	binFiles[emptyFileNumber].currentPos = 0;
	binFiles[emptyFileNumber].done = false;
	output.close();
	for (int currFile = 0; currFile < numberOfFiles; currFile++) {
		if (currFile != emptyFileNumber) {
			binFiles[currFile].currentPos = files[currFile].tellg();
			files[currFile].close();
		}
	}
}

int PolyphaseMergeSort::fSize(string filename) {
	std::ifstream in(filename, std::ifstream::ate | std::ifstream::binary);
	int size = in.tellg();
	in.close();
	return size;
}

void PolyphaseMergeSort::OutFileInBlocks(int fileNumber)
{
	string fileName = "F" + to_string(fileNumber) + ".dat";
	ifstream  f;
	int inputSize = fSize(fileName);
	f.open(fileName, ios::in | ios::binary);
	f.seekg(0, ios::beg);
	int blockLength, val, totalLength = 0;
	while (f.tellg() < inputSize)
	{
		f.read((char*)& blockLength, sizeof(blockLength));
		totalLength += blockLength;
		//cout << "BLOCK LENGTH=  " << blockLength << endl;
		//cout << endl;
		for (int i = 0; i < blockLength; i++) {
			f.read((char*)& val, sizeof(val));
			//cout << val << " ";
		}
		//cout << endl;
		//cout << endl;
	}
	//cout << "TOTAL LENGTH= " << totalLength << endl;
	//cout << endl;
	f.close();
}

PolyphaseMergeSort::PolyphaseMergeSort()
{

}

void PolyphaseMergeSort::polyphaseMergeSort(string inputFileName, int numberOfElementsInRAM, int numberOfFiles_)
{
	numberOfFiles = numberOfFiles_;
	InitializeFiles(inputFileName, numberOfElementsInRAM);
	for (int i = runsTableLength; i > 0; i--) {
		//cout << "Column " << i<<endl;
		Merge(i);
		//cout << endl;	
	}
	/*int finalRunLength, buff;
	ifstream output(binFiles[0].fileName, fstream::binary);
	output.read((char*)& finalRunLength, sizeof(int));
	cout << finalRunLength;*/
	/*InitializeFiles(inputFileName, numberOfElementsInRAM);
	for (int i = runsTableLength - 1; i >= 0; i--) {
		Merge(i);
	}
	int finalRunLength, buff;
	output[0].read((char*)&finalRunLength, sizeof(finalRunLength));
	fstream res(inputFileName, std::ofstream::out | std::ofstream::trunc);
	res.close();
	res.open(inputFileName, std::ofstream::out);
	for (int i = 0; i < finalRunLength; i++) {
		output[0].read((char*)&buff, sizeof(buff));
		res << buff << " ";
	}*/
}
