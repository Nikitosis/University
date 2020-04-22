#include "SortAlgo.h"
int main() {
	SortAlgo sort;
	SortAlgo::generateRandomFile("input.dat", 100000);
	sort.sort("input.dat","output.dat", 10, 300000);

	SortAlgo::printFile("output.dat");

	system("pause");
}