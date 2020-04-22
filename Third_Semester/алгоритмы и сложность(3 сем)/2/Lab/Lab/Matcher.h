#pragma once
#include <iostream>
#include <algorithm>
class Matcher
{
public:
	Matcher(int* bolts,int* nuts ,int n);
	~Matcher();

	void matchArrays();
	void printArrays();
private:
	int* bolts;
	int* nuts;
	int n;

	int sortSubArray(int* arr, int left, int right, int pivot);
	void matchPairs(int *bolts, int* nuts, int left, int right);
};

