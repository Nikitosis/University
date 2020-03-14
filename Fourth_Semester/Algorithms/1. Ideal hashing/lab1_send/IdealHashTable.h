#pragma once
#include <vector>
#include <stdlib.h> 
#include <time.h>    
#include "FileSystem.h"

using std::vector;

//Hash table for finding folders by their names
class IdealHashTable
{
public:
	IdealHashTable(const vector<Folder> &folders);
	Folder get(string name);
	~IdealHashTable();
private:
	struct InnerHashTable {
		vector<Folder> table;
		unsigned long hashNumber;
	};
	//hash table contains inner hash tables with their hash function characteristics.
	vector<InnerHashTable> table;
	unsigned long hashNumber;

	unsigned long getHash(string text, unsigned long hashNumber, unsigned long M);
	unsigned long findGoodFirstLevelHash(const vector<Folder> &folders);
	unsigned long findGoodSecondLevelHash(const vector<Folder> &folders);
	InnerHashTable getSecondLevelTable(vector<Folder> &folders);
};

