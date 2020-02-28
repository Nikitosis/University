#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <fstream>
#include "SplayTree.h"

using namespace std;

int main() {
	std::ifstream fin("input.txt");
	//freopen("output.txt", "w", stdout);
	SplayTree tree;

	while (!fin.eof()) {
		char sym = 0;
		fin >> sym;
		switch (sym) {
		case '+':
		{
			string name;
			int size;
			fin >> name >> size;
			tree.addElement(File(name, size));
			break;
		}
		case '-':
		{
			string name;
			int size;
			fin >> name >> size;
			tree.deleteElement(File(name, size));
			break;
		}
		case '?':
		{
			string name;
			int size;
			fin >> name >> size;
			cout<<tree.findElement(File(name, size)).name<<endl;
			break;
		}
		}
	}
	system("pause");
	fin.close();
}