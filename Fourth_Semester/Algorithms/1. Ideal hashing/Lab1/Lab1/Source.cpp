#include <vector>
#include <iostream>
#include "IdealHashTable.h"
#include "FileSystem.h"

using std::endl;
using std::cout;

int main() {
	Folder f1, f2, f3;
	f1.name = "Work";
	f2.name = "New folder";
	f3.name = "Hayyy";

	vector<Folder> folders = { f1, f2, f3 };

	IdealHashTable hashTable(folders);

	cout << hashTable.get("Work").name << endl;
	cout << hashTable.get("New folder").name << endl;
	cout << hashTable.get("Hayyy1").name << endl;

	system("pause");
}