#pragma once
#include <vector>
#include <string>
#include <unordered_map>
#include "Node.h"

using std::string;
using std::pair;
using std::unordered_map;
using std::vector;
class MyTree
{
	std::unordered_map<string, Node*> nameToNode;
	
	Node *head;

	void prepareDfsVector(Node *curNode, vector<Node*> &result);
public:
	MyTree();
	void buildFromListView(vector<pair<string,string>> list);
	void addBridge(pair<string, string>);

	Node* getHeadNode();

	vector<Node*> getDfsVector();

	~MyTree();
};

