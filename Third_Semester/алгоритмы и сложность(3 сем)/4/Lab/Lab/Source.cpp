#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Element {
	int key;
	int val;
};
void memoryLinear(vector<Element> arr) {
	int wall = 0;
	for (int i = 0; i < arr.size(); i++)
		if (arr[i].key == -1)
		{
			swap(arr[i], arr[wall]);
			wall++;
		}

	for (int i = 0; i < arr.size(); i++)
		cout << arr[i].key << " " << arr[i].val << endl;
}

void stableLinear(vector<Element> arr) {
	int minusNums = 0;
	vector<Element> result;
	for (int i = 0; i < arr.size(); i++)
		if (arr[i].key == -1)
			result.push_back(arr[i]);

	for (int i = 0; i < arr.size(); i++)
		if (arr[i].key == 1)
			result.push_back(arr[i]);

	for (int i = 0; i < arr.size(); i++)
		cout << result[i].key << " " << result[i].val << endl;
}

void stableMemory(vector<Element> arr) {
	int wall = 0;
	for(int i=0;i<arr.size();i++)
		if (arr[i].key == -1) {
			Element curElem = arr[i];
			for (int j = i; j >wall; j--)
			{
				arr[j] = arr[j - 1];
			}
			arr[wall] = curElem;
			wall++;
		}
	for (int i = 0; i < arr.size(); i++)
		cout << arr[i].key << " " << arr[i].val << endl;

}

int main() {
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);

	int n;
	cin >> n;
	vector<Element> arr(n);

	for (int i = 0; i < n; i++)
	{
		if (rand() % 2 == 0)
			arr[i].key = -1;
		else
			arr[i].key = 1;
		arr[i].val = rand();
	}
	cout << "Initital array:" << endl;
	for (int i = 0; i < arr.size(); i++)
		cout << arr[i].key << " " << arr[i].val << endl;

	cout << "Stable memory:" << endl;
	stableMemory(arr);

	cout << "Stable linear" << endl;
	stableLinear(arr);

	cout << "Memory linear" << endl;
	memoryLinear(arr);
}