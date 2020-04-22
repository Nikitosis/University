#pragma once
#include <vector>
#include <algorithm>

class MyHeap
{
public:
	MyHeap(int d);
	~MyHeap();
	bool isEmpty();
	int top();
	int pop();
	void insert(int val);
	void increaseKey(int index,unsigned int val);
private:
	std::vector<int> heap;
	int d;

	void tryPushUp(int curIndex);
	void tryPushDown(int curIndex);
};

