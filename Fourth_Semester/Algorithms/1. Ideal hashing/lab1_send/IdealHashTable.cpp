#include "IdealHashTable.h"

namespace global {
	unsigned int p = 1000000007;
}

//будуємо хеш-таблицю з вектору папок
IdealHashTable::IdealHashTable(const vector<Folder> &folders)
{
	hashNumber = findGoodFirstLevelHash(folders);
	vector<vector<Folder>> tempFolders(folders.size());

	for (Folder folder : folders) {
		tempFolders[getHash(folder.name, hashNumber, folders.size())].push_back(folder);
	}

	table.resize(folders.size());
	for (int i = 0; i < tempFolders.size(); i++) {
		table[i] = getSecondLevelTable(tempFolders[i]);
	}
}

//знаходимо папку по її назві
Folder IdealHashTable::get(string name)
{
	int firstLevelHash = getHash(name, hashNumber, table.size());
	int secondLevelHash = getHash(name, table[firstLevelHash].hashNumber, table[firstLevelHash].table.size());
	
	Folder foundFolder = table[firstLevelHash].table[secondLevelHash];

	// if foundFolder is not our folder
	if (foundFolder.name != name)
		return Folder();

	return foundFolder;
}

IdealHashTable::~IdealHashTable()
{
}

//знаходимо хеш тексту за хеш числом
unsigned long IdealHashTable::getHash(string text, unsigned long hashNumber, unsigned long M)
{
	long long hash = 0;
	long long p_pow = 1;
	for (int i = 0; i < text.size(); i++)
	{
		hash += ((text[i] - 'a' + 1)*hashNumber) * p_pow;
		p_pow *= global::p;
	}
	return abs(hash) % M;
}

//знаходимо хеш число, де сума квадратів розмірів бакета менше за N*2
unsigned long IdealHashTable::findGoodFirstLevelHash(const vector<Folder>& folders)
{
	while (true) {
		srand(time(NULL));
		int hashNumber = rand();
		vector<int> bucketCounter(folders.size());

		for (Folder folder : folders) {
			bucketCounter[getHash(folder.name, hashNumber, folders.size())]++;
		}

		int tablesSize = 0;
		for (int i = 0; i < bucketCounter.size(); i++) {
			tablesSize += bucketCounter[i]*bucketCounter[i];
		}

		if (tablesSize <= folders.size() * 2)
			return hashNumber;
	}
}

//знаходимо хеш число, де всі елементи знаходяться в різних комірках хеш-таблиці другого рівня
unsigned long IdealHashTable::findGoodSecondLevelHash(const vector<Folder>& folders)
{
	while (true) {
		srand(time(NULL));
		int hashNumber = rand();
		vector<int> bucketCounter(folders.size());
		bool isGoodHash = true;

		for (Folder folder : folders) {
			bucketCounter[getHash(folder.name, hashNumber, folders.size())]++;
		}

		for (int i = 0; i < folders.size(); i++) {
			if (bucketCounter[i] > 1)
				isGoodHash = false;
		}

		if (isGoodHash)
			return hashNumber;
	}
}

//знаходимо хеш-таблицю другого рівня
IdealHashTable::InnerHashTable IdealHashTable::getSecondLevelTable(vector<Folder>& folders)
{
	InnerHashTable innerHashTable;
	innerHashTable.table.resize(folders.size());
	innerHashTable.hashNumber = findGoodSecondLevelHash(folders);

	for (Folder folder : folders) {
		innerHashTable.table[getHash(folder.name, innerHashTable.hashNumber, folders.size())] = folder;
	}

	return innerHashTable;
}
