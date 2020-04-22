#include <iostream>
#include <vector>
#include <string>
using namespace std;

bool couldFindVariant(int curIndex, const vector<int> &numbers, int curSum, string &ansver)
{
	if (curIndex >= numbers.size() && curSum==0)
		return true;

	if (curIndex >= numbers.size())
		return false;

	if (couldFindVariant(curIndex + 1, numbers, curSum + numbers[curIndex], ansver) == true)
	{
		ansver = "+" + to_string(numbers[curIndex])+ansver;
		return true;
	}

	if (couldFindVariant(curIndex + 1, numbers, curSum - numbers[curIndex], ansver) == true)
	{
		ansver = "-" + to_string(numbers[curIndex])+ansver;
		return true;
	}

	return false;
}

int main()
{
	int n;
	cin >> n;
	vector<int> numbers(n);
	for (int i = 0; i < n; i++)
		cin >> numbers[i];

	string ansv = "";
	if (couldFindVariant(1, numbers, numbers[0], ansv)==true)
	{
		cout << "Ansver is:" << endl;
		cout << to_string(numbers[0]) + ansv << endl;
	}
	else
	{
		cout << "Can't find ansver" << endl;
	}

	system("pause");

}