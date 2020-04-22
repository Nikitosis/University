#define _CRT_SECURE_NO_WARNINGS

#include <iostream>

#include "MyHeap.h"

using namespace std;

int main() {
	//freopen("input.txt", "r", stdin);
	//freopen("output.txt", "w", stdout);

	MyHeap myHeap(5);
	int n;
	cin >> n;
	for (int i = 0; i < n; i++)
		myHeap.insert(rand());

	vector<int> arr;

	while (!myHeap.isEmpty()) {
		arr.push_back(myHeap.pop());
	}


	for (int i = 0; i < arr.size(); i++)
		cout << arr[i] << " ";

	system("pause");
}