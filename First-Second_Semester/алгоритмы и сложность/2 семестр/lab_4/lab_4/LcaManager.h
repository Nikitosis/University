#pragma once
#include <vector>
#include <unordered_map>
#include "Node.h"

using std::vector;
using std::unordered_map;
class LcaManager
{
	vector<Node *>data;

	void build(int v,int vl,int vr,vector<Node*> &input);
	Node *getMin(int v, int vl, int vr,int l,int r);

	unordered_map<string, int> nameToIndex;
	int nodesAmount;
public:
	LcaManager(vector<Node*> nodes);

	Node *getMinimalParent(string fNode, string sNode);
	~LcaManager();
};

