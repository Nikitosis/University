#pragma once

#include <string>
#include <vector>
#include <algorithm>
using std::string;
using std::vector;

struct File {
	string name;
	int size;
	File(string name, int size):name(name),size(size){}
	File(){}
	bool operator<(const File& right) {
		return compare(*this, right) == -1;
	}
	bool operator>(const File&right) {
		return compare(*this, right) == 1;
	}
	bool operator<=(const File& right) {
		return compare(*this, right) <= 0;
	}
	bool operator>=(const File& right) {
		return compare(*this, right) >= 0;
	}
	bool operator==(const File& right) {
		return compare(*this, right) == 0;
	}
private:
	int compare(const File& left,const File& right) {
		if (left.size < right.size) {
			return -1;
		}
		if (left.size > right.size) {
			return 1;
		}
		return 0;
	}
};

struct Folder {
	string name;
	vector<File> files;
};