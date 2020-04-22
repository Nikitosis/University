#include <iostream>
#include <string>
#include <vector>

using namespace std;

string operator*(string s1, string s2)
{
	vector<int> Arr1(s1.size());
	vector<int> Arr2(s2.size());

	for (int i = 0; i < s1.size(); i++)
		Arr1[i] = s1[s1.length()-i-1] - '0';
	
	for (int i = 0; i < s2.size(); i++)
		Arr2[i] = s2[s2.length()-i-1] - '0';

	vector<int> Ansv(s1.size() + s2.size() + 1,0);

	for (int i = 0; i < Arr1.size(); i++)
		for (int j = 0; j < Arr2.size(); j++)
			Ansv[i + j] += Arr1[i] * Arr2[j];

	for (int i = 0; i < Ansv.size()-1; i++)
	{
		Ansv[i + 1] += Ansv[i] / 10;
		Ansv[i] %= 10;
	}

	int lastIndex = Ansv.size() - 1;
	while (Ansv[lastIndex] == 0)
		lastIndex--;

	string ansv;
	for (int i = lastIndex; i >= 0; i--)
		ansv = ansv + to_string(Ansv[i]);

	return ansv;
}

string getPow(string s, int powNum)
{
	if (powNum == 1)
		return s;
	if (powNum % 2 == 0)
	{
		string lowerPow = getPow(s, powNum / 2);
		return lowerPow * lowerPow;
	}
	else
	{
		return s * getPow(s,powNum - 1);
	}
}

int main()
{
	int n;
	cin >> n;
	cout << getPow("2", n);
	system("pause");
}