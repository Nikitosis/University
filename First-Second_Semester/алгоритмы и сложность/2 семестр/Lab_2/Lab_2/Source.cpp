#include <fstream>
#include <iostream>
#include <string>
#include <cmath>
#include <algorithm>
#include <vector>

using namespace std;

bool cmp(string s1, string s2) {
	int minLen = min(s1.length(), s2.length());
	for (int i = 0; i < minLen; i++) {
		if (s1[i] < s2[i])
			return true;
		if (s1[i] > s2[i])
			return false;
	}
	if (s1.length() < s2.length())
		return true;

	return false;
}

void copyDataToFile(string fromFile, string toFile) {
	ifstream input(fromFile);
	ofstream output(toFile);
	while (!input.eof()) {
		string data;
		input >> data;
		if (data == "")
			continue;

		output << data<<endl;
	}
	input.close();
	output.close();
}

void copyDataToFileWithSort(string fromFile, string toFile, bool(*curCmp)(string, string),int batchSize) {
	ifstream input(fromFile);
	ofstream output(toFile);

	vector<string> batch;
	while (!input.eof()) {
		string data;
		input >> data;
		if (data == "")
			continue;

		batch.push_back(data);

		if (batch.size() >= batchSize) {
			sort(batch.begin(), batch.end(),curCmp);
			for (string curData : batch) {
				output << curData<<endl;
			}
			batch.clear();
		}
	}
	if (batch.size() > 0) {
		sort(batch.begin(), batch.end(),curCmp);
		for (string curData : batch) {
			output << curData << endl;
		}
	}
	input.close();
	output.close();
}

/*
* This function separates inputFile data between outputFile1 and outputFile2, so that they contain chains of sorted data.
* These chains are separated by delimiter '0'.
* Every word is in the new string
* Last symbol of each file is '0'
* Function returns 'true' if we still can separate this file. It means, there are more than one sorted chain of data.
* Otherwise returns 'false'
*/
bool separateTwoFiles(string inputFile,string outputFile1, string outputFile2, bool(*curCmp)(string, string)){
	ifstream mainInput(inputFile);
	ofstream output1(outputFile1);
	ofstream output2(outputFile2);
	bool isSeparationExists = false;
	bool isFirstOutput = true;


	string prevStr = "";
	string curStr = "";
	while (!mainInput.eof()) {
		mainInput >> curStr;

		if (curStr == "")
			continue;

		if (!(prevStr == "" || curCmp(prevStr, curStr) || prevStr==curStr)) {   //if current increasing chain is over

			if (isFirstOutput)                               //we put delimiter to show that current chain is over
				output1 << "0" << endl;
			else
				output2 << "0" << endl;

			isFirstOutput = !isFirstOutput;                  //change output file
			isSeparationExists = true;
		}
		if (isFirstOutput)
			output1 << curStr << endl;
		else
			output2 << curStr << endl;
		prevStr = curStr;
		curStr = "";
	}
	output1 << "0"<<endl;
	output2 << "0"<<endl;

	mainInput.close();
	output1.close();
	output2.close();

	return isSeparationExists;
}

/*
* This function merges two files: inputFile1 and inputFile2 in one outputFile, using curCmp(string,string)
* to compare words
* How it works: it merges two chains of sorted data from inputFile1 and inputFile2 and sends output data to outputFile
* Each word begins in a new string
*/
void mergeTwoFiles(string outputFile, string inputFile1, string inputFile2, bool(*curCmp)(string, string)) {
	ofstream output(outputFile);
	ifstream input1(inputFile1);
	ifstream input2(inputFile2);

	string curStr1;                //curStr1 contains words from input1
	string curStr2;				   //curStr2 contains words from input2

	input1 >> curStr1;
	input2 >> curStr2;


	while (true) {
		if (curStr1 == "0" || input1.eof()) {               //if we are at the end of input1 sorted chain
			while (curStr2 != "0" && !input2.eof()) {       //we handle the rest of input2 chain
				output << curStr2 << endl;
				input2 >> curStr2;
			}

			if (input1.eof() && input2.eof())               
				break;

			if (!input1.eof())
				input1 >> curStr1;

			if (!input2.eof())
				input2 >> curStr2;


			continue;
		}

		if (curStr2 == "0" || input2.eof()) {               //if we are at the end of input2 sorted chain
			while (curStr1 != "0" && !input1.eof()) {       //we handle the rest of input1 chain
				output << curStr1 << endl;
				input1 >> curStr1;
			}

			if (input1.eof() && input2.eof())
				break;

			if (!input1.eof())
				input1 >> curStr1;

			if (!input2.eof())
				input2 >> curStr2;

			continue;
		}

		if (curCmp(curStr1, curStr2)) {                     //compare two strings and output appropriate one
			output << curStr1<<endl;
			input1 >> curStr1;
		}
		else {
			output << curStr2<<endl;
			input2 >> curStr2;
		}
	}

	output.close();
	input1.close();
	input2.close();

}


void sortFile(string inputFileName, string outputFileName, bool(*curCmp)(string, string)) {
	string tempFile1 = outputFileName + "_temp_1";
	string tempFile2 = outputFileName + "_temp_2";

	int operationsAmount = 0;

	copyDataToFile(inputFileName, outputFileName);
	//copyDataToFileWithSort(inputFileName, outputFileName, curCmp, 100);


	while (true) {
		if (!separateTwoFiles(outputFileName, tempFile1, tempFile2, curCmp))   //if we cannot separate files
			break;
		mergeTwoFiles(outputFileName, tempFile1, tempFile2, curCmp);
		operationsAmount++;
	}
	cout << "Algorithm ended. It took " << operationsAmount << " loops to complete it";
}

int main() {
	sortFile("bible.txt", "output.txt", cmp);
	system("pause");
}

