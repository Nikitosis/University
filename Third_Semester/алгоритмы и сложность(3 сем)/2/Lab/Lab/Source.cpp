#define _CRT_SECURE_NO_WARNINGS

#include <iostream>

#include "Matcher.h"

using namespace std;

int main() {
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);

	int n;
	cin >> n;
	int *bolts = new int[n];
	int *nuts = new int[n];
	for (int i = 0; i < n; i++)
		cin >> bolts[i];

	for (int i = 0; i < n; i++)
		cin >> nuts[i];

	Matcher matcher(bolts, nuts, n);

	matcher.matchArrays();
	matcher.printArrays();

	delete[] bolts;
	delete[] nuts;
}