#pragma once

#include <string>
#include <vector>
using std::string;
using std::vector;

struct File {
	string name;
	int size;
};

struct Folder {
	string name;
	vector<File> files;
};