#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <string>
#include <sstream>
#include <vector>
#include "MyList.h"

using namespace std;
struct Rule {
	string fromText;
	string toText;
	bool isStop;
};

string proceedMarkovAlgo(string mainWord, const MyList<Rule> &rules, bool &isStop)
{
	for (int i = 0; i < rules.getSize(); i++)
	{
		int fromTextIndex = mainWord.find(rules[i].fromText);
		int fromTextLen = rules[i].fromText.length();
		if (fromTextIndex < mainWord.length())
		{
			mainWord.erase(fromTextIndex, fromTextLen);
			mainWord.insert(fromTextIndex, rules[i].toText);
			isStop = rules[i].isStop;
			break;
		}
	}
	return mainWord;
}
int main()
{
	freopen("input.txt", "r", stdin); //open input.txt to read
	freopen("output.txt", "w", stdout); //open output.txt to write
	/*string mainWord;

	getline(cin, mainWord);

	MyList<Rule> rules;

	while(!cin.eof())
	{
		string allStr;
		getline(cin, allStr);
		stringstream ss(allStr);

		Rule curRule;
		string stopWord;
		ss >> curRule.fromText >> curRule.toText >> stopWord;

		if (stopWord == "stop")
			curRule.isStop = true;
		else
			curRule.isStop = false;
		if (curRule.toText == "\"\"")//if toText is empty string ("")
		{
			curRule.toText = "";
		}
		if (curRule.fromText == "\"\"")//if fromText is empty string ("")
		{
			curRule.fromText = "";
		}
		rules.push_back(curRule);
	}

	cout << "Proceeding algorithm:" << endl;
	for (;;)
	{
		bool isStop = false;
		string newMainWord = proceedMarkovAlgo(mainWord, rules, isStop);

		if (newMainWord == mainWord)
			break;
		cout << mainWord << " --> " << newMainWord << endl;
		if (isStop)
			break;
		mainWord = newMainWord;
	}*/

	MyList<int> test;
	for (int i = 0; i < 5; i++)
		test.push_back(i);

	test.deleteIndex(4);

	for (int i = 0; i < test.getSize(); i++)
		cout << test[i] << endl;
	/*
	cout << "Algorithm ended!" << endl;
	*/
	//system("pause");


}

/*
���� ���� F, � ������� ���� �����.����� �� �������������, �� ��������� ���������� ������
�� ������� ����� F1 � F2. � F1 �� ���������� ������ ���������������� ������� ����(����� ���� � ���������. �������)
� ���� F2 ���������� ��������� ������������ �������.

����� � ���� F3 ���������� merge sort ������ ������� �� F1 � F2.
� ���� F4 ���������� merge sort ������ ������� �� F1 � F2.
����� ������� � ��� � ������ ����� ����� ����� ��� ������ ��������� �������. � �����-������ �� ������� � ����� ��������������� �������
*/