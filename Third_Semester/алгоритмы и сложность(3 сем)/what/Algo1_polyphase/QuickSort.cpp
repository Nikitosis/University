#include "QuickSort.h"

void QuickSort::Swap(int* a, int* b)
{
	int temp = *a;
	*a = *b;
	*b = temp;
}

int QuickSort::Partition(vector<int> &vec, int low, int high)
{
	int pivot = vec[high];
	int i = (low - 1); 

	for (int j = low; j <= high - 1; j++)
	{
		if (vec[j] < pivot)
		{
			i++; 
			Swap(&vec[i], &vec[j]);
		}
	}
	Swap(&vec[i + 1], &vec[high]);
	return (i + 1);
}

void QuickSort::quickSort(vector<int> &vec, int low, int high)
{

	if (low < high)
	{
		int partitioningIndex = Partition(vec, low, high);
		quickSort(vec, low, partitioningIndex - 1);
		quickSort(vec, partitioningIndex + 1, high);
	}
}
