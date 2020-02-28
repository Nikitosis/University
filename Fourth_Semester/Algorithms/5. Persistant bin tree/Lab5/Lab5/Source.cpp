#include "BinTree.h"
#include "FileSystem.h"
#include <iostream>

using namespace std;

int main() {
	File f1("afirst", 10);
	File f2("zlast", 12121);
	File f3("cmiddle", 2321);

	BinTree t1;
	t1 = t1.addFile(f1);
	
	cout << t1.getFile(f1.name).name<<endl;

	t1.addFile(f2);

	cout << t1.getFile(f2.name).name<<endl;
	

	BinTree t2 = t1.addFile(f2);
	cout << t2.getFile(f1.name).name << endl;
	cout << t2.getFile(f2.name).name << endl;

	system("pause");
}