#pragma once
#include <string>
#include <vector>
using std::string;
using std::vector;
struct Node {
	string val;
	vector<Node*> children;
	Node* parent;
	int depth;
	Node(string val) :val(val), parent(nullptr),depth(0)
	{}
	void addChild(Node *child) {
		children.push_back(child);
	}
};
