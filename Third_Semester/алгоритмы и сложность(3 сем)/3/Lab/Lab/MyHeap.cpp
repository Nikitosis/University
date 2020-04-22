#include "MyHeap.h"



MyHeap::MyHeap(int d):d(d)
{
}


MyHeap::~MyHeap()
{
}

bool MyHeap::isEmpty()
{
	return heap.size() == 0;
}

int MyHeap::top()
{
	if (isEmpty())
		throw std::out_of_range("heap is empty");

	return heap[0];
}

int MyHeap::pop()
{
	if(isEmpty())
		throw std::out_of_range("heap is empty");
	if (heap.size() == 1)
	{
		int elem = heap[0];
		heap.pop_back();
		return elem;
	}
	int topElem = heap[0];

	heap[0] = heap[heap.size() - 1];
	heap.pop_back();

	tryPushDown(0);

	return topElem;
}

void MyHeap::insert(int val)
{
	heap.push_back(val);
	tryPushUp(heap.size() - 1);
}

void MyHeap::increaseKey(int index, unsigned int val)
{
	if(index<0 || index>=heap.size())
		throw std::out_of_range("index is out of range");
	heap[index] += val;

	tryPushUp(index);
}

void MyHeap::tryPushUp(int curIndex)
{
	if (curIndex == 0)
		return;
	if (heap[(curIndex - 1) / d] < heap[curIndex]) {
		std::swap(heap[(curIndex - 1) / d], heap[curIndex]);
		tryPushUp((curIndex - 1) / d);
	}
}

void MyHeap::tryPushDown(int curIndex)
{
	int maxChildIndex = -1;
	int maxChildValue = heap[curIndex];
	for (int i = 1; i <= d; i++) {
		int curChildIndex = curIndex * d + i;

		if (heap.size() <= curChildIndex)
			break;

		if (heap[curChildIndex] >= maxChildValue) {
			maxChildIndex = curChildIndex;
			maxChildValue = heap[curChildIndex];
		}
	}
	//if no more children bigger than current
	if (maxChildIndex == -1)
		return;

	std::swap(heap[curIndex], heap[maxChildIndex]);

	tryPushDown(maxChildIndex);
}
