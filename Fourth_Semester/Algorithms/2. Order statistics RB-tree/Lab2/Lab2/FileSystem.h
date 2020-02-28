#pragma once

#include <string>
#include <vector>
using std::string;
using std::vector;

struct File {
	string name;
	int size;
	File(string name, int size):name(name),size(size){}
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
		for (int i = 0; i < std::min(left.name.length(), right.name.length()); i++) {
			if (left.name[i] > right.name[i])
				return 1;
			if (left.name[i] < right.name[i])
				return -1;
		}
		if (left.name.length() > right.name.length)
			return 1;
		if (left.name.length() < right.name.length())
			return -1;
		return 0;
	}
};

struct Folder {
	string name;
	vector<File> files;
};