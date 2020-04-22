#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <fstream>

#include "Expression.h"
using namespace std;

int main()
{
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
	string expStr;
	getline(cin, expStr);
	Expression exp;
	try {
		exp.setExpression(expStr);
		exp.fillPolish();
		cout << exp.getPolish() << endl;
		cout << exp.getPolishCalculated().getValue();
	}
	catch (std::exception e)
	{
		cout << e.what();
	}
	int f = 2;
	f = f++ + f++;
	int k;

	return 0;
}