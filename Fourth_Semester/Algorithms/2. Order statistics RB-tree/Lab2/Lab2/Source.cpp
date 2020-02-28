#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <fstream>
#include "RedBlackTree.h"

using namespace std;

int main() {
	std::ifstream fin("input.txt");
	//freopen("output.txt", "w", stdout);
	RedBlackTree tree;

	while (!fin.eof()) {
		char sym=0;
		fin >> sym;
		switch (sym) {
		case '+':
			{
			string name;
			int size;
			fin >> name >> size;
			tree.addElement(File(name,size));
			break;
			}
		case '-':
			{
			string name;
			int size;
			fin >> name >> size;
			tree.deleteElement(File(name,size));
			break;
			}
		case '?':
			{
			tree.printTree();
			break;
			}
		case 'n':
			{
			int a;
			fin >> a;
			cout << tree.findNth(a).name << endl;
			break;
			}
		}
	}
	system("pause");
	fin.close();
}