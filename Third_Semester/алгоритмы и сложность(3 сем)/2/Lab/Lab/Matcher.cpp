#include "Matcher.h"



Matcher::Matcher(int * bolts, int * nuts, int n):bolts(bolts),nuts(nuts),n(n)
{
}

Matcher::~Matcher()
{
}

void Matcher::matchArrays()
{
	matchPairs(bolts, nuts, 0, n - 1);
}

void Matcher::printArrays()
{
	for (int i = 0; i < n; i++)
		std::cout << bolts[i] << " ";

	std::cout << std::endl;

	for (int i = 0; i < n; i++)
		std::cout << nuts[i] << " ";
}

int Matcher::sortSubArray(int * arr, int left, int right, int pivot)
{
	//replace pivot element to the right
	for(int i=left;i<=right;i++)
		if (arr[i] == pivot) {
			std::swap(arr[i], arr[right]);
			break;
		}

	//place smaller elements left to the wall
	int wall = left;
	for (int i = left; i <= right; i++) {
		if (arr[i] < pivot) {
			std::swap(arr[wall], arr[i]);
			wall++;
		}
	}

	//place pivot element to the right from the wall
	std::swap(arr[right], arr[wall]);

	return wall; //returns index of pivot
}

void Matcher::matchPairs(int * bolts, int * nuts, int left, int right)
{
	if (left < right) {
		//take right element as pivotElement
		int pivotNuts = nuts[right];

		//sort first array based on pivotNuts element
		int pivotIndex = sortSubArray(bolts, left, right, pivotNuts);

		//sort second array based on pivotBolts element
		int pivotBolts = bolts[pivotIndex];
		sortSubArray(nuts, left, right, pivotBolts);

		//make the same for left and right subarrays
		matchPairs(bolts, nuts, left, pivotIndex - 1);
		matchPairs(bolts, nuts, pivotIndex + 1, right);
	}
}
