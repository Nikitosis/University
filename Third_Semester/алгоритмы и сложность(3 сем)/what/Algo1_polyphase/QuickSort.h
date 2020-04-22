#pragma once
#include <vector>

using namespace std;

class QuickSort
{
private:
	void Swap(int* a, int* b);
	int Partition(vector<int> &vec, int low, int high);
public:
	void quickSort(vector<int> &vec, int low, int high);
};

