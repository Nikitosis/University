#include <iostream>
#include <fstream>
#include <list>
#include <string>
#include <vector>
#include <cmath>
#include <algorithm>

using namespace std;

#define BUCKETS_AMOUNT 255

struct Data {
	string data;
	string length;
	Data(string data, string length) :data(data), length(length) {

	}
};

int getLetterIndex(char l) {
	if(l>='A' && l<='Z')
		return l+100;
}

list<Data> initData(ifstream &fin) {
	list<Data> list;

	while (!fin.eof()) {
		int a;
		string value;
		fin >> value;
		Data data(value, to_string(value.length()));

		list.push_back(data);
	}
	return list;
}

list<Data> sortDataByLength(const list<Data> &inputData) {
	vector<list<Data>> buckets(BUCKETS_AMOUNT+1);

	int maxLength = 0;
	for (Data curData : inputData) {
		maxLength = max(maxLength, (int)curData.length.length());
	}


	list<Data> outputData = inputData;
	for (int i = 0; i <=maxLength-1; i++) {

		for (Data curData : outputData) {
			int curLength = curData.length.length();
			int curIndex = curLength - i - 1;
			if (curIndex>=0) {
				char curSymb= curData.length[curIndex];
				buckets[getLetterIndex(curSymb)].push_back(curData);
			}
			else {   //if the word doesnt have enough length
				buckets[255].push_back(curData);
			}
		}

		outputData.clear();
		outputData.splice(outputData.end(), buckets[BUCKETS_AMOUNT]);
		for (int j = 0; j < buckets.size(); j++)
			outputData.splice(outputData.end(), buckets[j]);   //add current bucket to the end of the list
	}

	return outputData;
}

list<Data> sortDataByValue(list<Data> inputData) {
	vector<list<Data>> buckets(BUCKETS_AMOUNT);

	int maxLength = inputData.back().data.length();

	list<Data> outputData;
	for (int i = maxLength - 1; i >= 0; i--) {
		while (!inputData.empty() && inputData.back().data.length()>i) {
			outputData.push_front(inputData.back());
			inputData.pop_back();
		}

		for (Data curData : outputData) {
			int curLength = curData.data.length();
			char curSymb = curData.data[i];
			buckets[getLetterIndex(curSymb)].push_back(curData);
		}

		outputData.clear();
		for (int j = 0; j < buckets.size(); j++)
			outputData.splice(outputData.end(), buckets[j]);
	}

	return outputData;
}

int main() {
	ifstream fin("input.txt");
	ofstream fout("output.txt");

	list<Data> data = initData(fin);

	data = sortDataByLength(data);

	fout << "SORT DATA BY LENGTH" << endl;
	for (Data curData : data) {
		fout << curData.data << endl;
	}
	fout << endl;

	data = sortDataByValue(data);

	fout << "SORT DATA BY VALUE" << endl;

	for (Data curData : data) {
		fout << curData.data << endl;
	}



}