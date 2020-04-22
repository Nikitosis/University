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
			int a;
			fin >> a;
			tree.addElement(a);
			break;
			}
		case '-':
			{
			int a;
			fin >> a;
			tree.deleteElement(a);
			break;
			}
		case '?':
			{
			tree.printTree();
			break;
			}
		}
	}
	system("pause");
	fin.close();
}