#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <string>
#include <sstream>
#include <vector>

using namespace std;
struct Rule {
	string fromText;
	string toText;
	bool isStop;
};

string proceedMarkovAlgo(string mainWord, const vector<Rule> &rules,bool &isStop)
{
	for (int i = 0; i < rules.size(); i++)
	{
		int fromTextIndex = mainWord.find(rules[i].fromText);
		int fromTextLen = rules[i].fromText.length();
		if (fromTextIndex <mainWord.length())
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
	freopen("input2.txt", "r", stdin); //open input.txt to read
	freopen("output.txt", "w", stdout); //open output.txt to write
	string mainWord;
	cout << "Enter your word:" << endl;

	cin >> mainWord;

	vector<Rule> rules;

	cout << "Enter amount of rules:" << endl;
	int rulesA;
	cin >> rulesA;
	rules.resize(rulesA);
	cin.ignore();
	for (int i = 0; i < rulesA; i++)
	{
		string allStr;
		getline(cin, allStr);
		stringstream ss(allStr);

		string stopWord;
		ss >> rules[i].fromText >> rules[i].toText>>stopWord;

		if (stopWord == "stop")
			rules[i].isStop = true;
		else
			rules[i].isStop = false;
		if (rules[i].toText == "\"\"")//if toText is empty string ("")
		{
			rules[i].toText = "";
		}
		if (rules[i].fromText == "\"\"")//if fromText is empty string ("")
		{
			rules[i].fromText = "";
		}
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
	}

	cout << "Algorithm ended!" << endl;

	//system("pause");

	
}