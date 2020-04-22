#include <iostream>
#include "PolyphaseMergeSort.h"
#include <time.h>

using std::cout;

void GenerateFile(string fname, int count) {
	srand(time(0));
	/*int nums[1000000];
	for (int i = 0; i < count; i++)
		nums[i] = i;
	for (int i = 0; i < count; i++) {
		int a = rand() % count;
		int b = rand() % count;
		if (a != b)
			swap(nums[a], nums[b]);
	}*/
	fstream f;
	f.open(fname, std::ofstream::out | std::ofstream::trunc | std::ofstream::binary);
	int32_t val;
	for (int i = 0; i < count; i++)
	{
		//val = nums[i];
		val = rand() % 10000000;
		f.write((char*)& val, sizeof(val));
	}
	f.close();
}

int fSize(string filename) {
	std::ifstream in(filename, std::ifstream::ate | std::ifstream::binary);
	int size = in.tellg();
	in.close();
	return size;
}

void OutFile(string fname) {
	ifstream  f;
	int inputSize = fSize(fname);
	f.open(fname, ios::in | ios::binary);
	f.seekg(0, ios::beg);
	int32_t val;
	while (f.tellg() < inputSize)
	{
		f.read((char*)& val, sizeof(val));
		cout << val << " ";
	}
	//while (f)
	//{
	//	f.read((char*)& val, sizeof(val));
	//	//if (f.eof()) break;
	//	cout << val << ";";
	//}
	f.close();
}

int main()
{
	//int i;
	//const char* a = "Input.txt";
	//fstream input(a);
	//while (input >> i) {
	//	cout << i << " "; 
	//}
	GenerateFile("inp.dat", 10000000);
	//OutFile("inp.dat");
	cout << "Start" << endl;
	PolyphaseMergeSort sort;
	int numberOfFiles = 5;
	sort.polyphaseMergeSort("inp.dat", 10000, numberOfFiles);
	OutFile("F0.dat");

}
