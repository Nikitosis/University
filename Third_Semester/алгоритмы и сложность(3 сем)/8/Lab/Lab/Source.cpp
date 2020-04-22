#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <string>
#include<vector>
#include <fstream>
#include <chrono>

using namespace std;
int naiveSearch(string source, string target) {
	for (int i = 0; i <= source.length()-target.length(); i++) {
		if (source[i] == target[0]) {
			bool isEqual = true;
			for (unsigned int j = 0; j < target.length(); j++) {
				if (source[j + i] != target[j]) {
					isEqual = false;
					break;
				}
			}
			if (isEqual)
				return i;
		}
	}
	return -1;
}

int horspul(string source, string target) {
	const int LETTERS = 256;
	int shifts[LETTERS];
	for (int i = 0; i < LETTERS; i++)
		shifts[i] = target.length();

	//build shift table
	for (unsigned int i = 0; i < target.length()-1; i++)
		shifts[target[i]] = target.length() - i - 1;

	//begin search
	unsigned int index = 0;
	while(index<=source.length()-target.length()) {
		bool isEqual = true;
		int shift = 0;
		for (int j = target.length()-1; j >= 0; j--) {
			
			if (target[j] != source[index + j]) {
				isEqual = false;
				if (j == target.length() - 1)
					shift = shifts[source[index + j]];
				else
					shift = shifts[target[target.length() - 1]];
				break;
			}
		}
		if (isEqual)
			return index;
		index += shift;
	}
	return -1;
}

int boerMur(string source, string target) {
	const int LETTERS = 256;
	int shifts[LETTERS];
	for (int i = 0; i < LETTERS; i++)
		shifts[i] = target.length();

	//build shift table
	for (unsigned int i = 0; i < target.length() - 1; i++)
		shifts[target[i]] = target.length() - i - 1;

	//build suffixShift
	vector<int> suffixShift(target.length(), target.length()-1);

	for (int i = 1; i <= target.length(); i++) {
		string toFind = target.substr(target.length() - i);
		string toTarget = target.substr(0, target.length() - 1);
		int index = toTarget.rfind(toFind);
		if (index == -1)
			break;

		suffixShift[i] = target.length() - index-i;
	}

	//begin search
	unsigned int index = 0;
	while (index <= source.length() - target.length()) {
		bool isEqual = true;
		int shift = 0;
		for (int j = target.length() - 1; j >= 0; j--) {

			if (target[j] != source[index + j]) {
				isEqual = false;
				if (j == target.length() - 1)
					shift = shifts[source[index + j]];
				else
					shift = suffixShift[target.length()-j-1];
				break;
			}
		}
		if (isEqual)
			return index;
		index += shift;
	}
	return -1;
}

int kmp(string source, string target) {
	string str = target + "@" + source;
	vector<int> M(str.length(), 0);
	M[0] = 0;
	for (int i = 1; i < str.length(); i++) {
		int curIndex = i;
		int prefIndex = M[curIndex - 1];
		while (true) {
			if (prefIndex == 0 && str[i] == str[0]) {
				M[i] = 1;
				break;
			}
			if (prefIndex == 0 && str[i] != str[0]) {
				M[i] = 0;
				break;
			}
			if (str[prefIndex] == str[i] && prefIndex!=i) {
				M[i] = prefIndex + 1;
				break;
			}
			curIndex = prefIndex - 1;
			prefIndex = M[curIndex];
		}
	}
	
	for (int i = target.length()+1; i < str.length(); i++) {
		if (M[i] == target.length()) {
			return i - target.length()*2;
		}
	}
	return -1;
}

//PK algo
int robinKarp(string source, string target) {
	if (source.length() < target.length())
		return false;

	const int p = 31;
	vector<long long> pows(source.length());
	pows[0] = 1;
	for (int i = 1; i < pows.size(); i++)
		pows[i] = pows[i - 1] * p;

	vector<long long> sourceHash(source.length());
	long long targetHash = 0;

	for (int i = 0; i < source.length(); i++) {
		sourceHash[i] = (source[i] - 'a' + 1)*pows[i];
		if (i != 0)
			sourceHash[i] += sourceHash[i - 1];
	}


	for (int i = 0; i < target.length(); i++) {
		targetHash += (target[i] - 'a' + 1)*pows[i];
	}

	for (int i = target.length() - 1; i < source.length(); i++) {
		long long subHash = sourceHash[i];
		if (int(i - target.length()) >= 0)
			subHash -= sourceHash[i - target.length()];
		if (subHash == targetHash * pows[i + 1 - target.length()])
			if(target==source.substr(i-target.length()+1,target.length()))
				return i-target.length()+1;
	}

	return -1;
}



int main() {
	ifstream inputText("bible.txt");
	ifstream searchText("search.txt");
	ofstream outputText("output.txt");
	string source, target;
 	while (!inputText.eof()) {
		string line;
		getline(inputText, line);
		source += line;
	}
	getline(searchText, target);
	auto time = chrono::high_resolution_clock::now();

	outputText << "Naive search" << endl;
	time = chrono::high_resolution_clock::now();
	outputText << "Position: " << naiveSearch(source, target) << endl;
	outputText << "Time: " << chrono::duration_cast<chrono::nanoseconds>(chrono::high_resolution_clock::now() - time).count() << " nanosec" << endl;

	outputText << "Boer-Mur search" << endl;
	time = chrono::high_resolution_clock::now();
	outputText << "Position: " << boerMur(source, target) << endl;
	outputText << "Time: " << chrono::duration_cast<chrono::nanoseconds>(chrono::high_resolution_clock::now() - time).count() << " nanosec" << endl;

	outputText << "Horspul search" << endl;
	time = chrono::high_resolution_clock::now();
	outputText << "Position: " << horspul(source, target) << endl;
	outputText << "Time: " << chrono::duration_cast<chrono::nanoseconds>(chrono::high_resolution_clock::now() - time).count() << " nanosec" << endl;

	outputText << "KMP search" << endl;
	time = chrono::high_resolution_clock::now();
	outputText << "Position: " << kmp(source, target) << endl;
	outputText << "Time: " << chrono::duration_cast<chrono::nanoseconds>(chrono::high_resolution_clock::now() - time).count() << " nanosec" << endl;

	outputText << "Robin-Karp search" << endl;
	time = chrono::high_resolution_clock::now();
	outputText << "Position: " << robinKarp(source, target) << endl;
	outputText << "Time: " << chrono::duration_cast<chrono::nanoseconds>(chrono::high_resolution_clock::now() - time).count() << " nanosec" << endl;
}