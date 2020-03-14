#include "FileSystem.h"
#include "OptBinTree.h"
#include <iostream>

using namespace std;

int main() {
	File f1("zz", 10);
	File f2("aa", 232);
	File f3("cc", 4312);

	OptBinTree tree;
	tree.addFile(f1);
	tree.addFile(f2);
	tree.addFile(f3);

	cout << tree.minimizeTree()<<endl;

	//increase zz cost
	cout << tree.getFile("zz").name << endl;
	cout << tree.getFile("zz").name << endl;
	cout << tree.getFile("zz").name << endl;

	//rebuilds tree and places zz as root
	cout << tree.minimizeTree() << endl;

	system("pause");
}