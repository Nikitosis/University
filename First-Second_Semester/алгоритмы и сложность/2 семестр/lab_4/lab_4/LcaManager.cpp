#include "LcaManager.h"



LcaManager::LcaManager(vector<Node*> nodes)
{
	data.resize(nodes.size() * 4);

	for (int i = 0;i<nodes.size();i++) {
		if (nameToIndex.find(nodes[i]->val) == nameToIndex.end()) {
			nameToIndex.insert({ nodes[i]->val,i });
		}
	}

	nodesAmount = nodes.size();

	build(0, 0, nodes.size() - 1, nodes);
}

void LcaManager::build(int v,int vl,int vr,vector<Node*> &input) {
	if (vl == vr) {
		data[v] = input[vl];
		return;
	}
	int vm = (vl + vr) / 2;
	build(v * 2 + 1, vl, vm, input);
	build(v * 2 + 2, vm + 1, vr, input);

	if (data[v * 2 + 1]->depth >= data[v * 2 + 2]->depth)
		data[v] = data[v * 2 + 2];
	else
		data[v] = data[v * 2 + 1];
}

Node *LcaManager::getMinimalParent(string fNode, string sNode) {
	int fIndex = nameToIndex[fNode];
	int sIndex = nameToIndex[sNode];
	if (fIndex > sIndex)
		std::swap(fIndex, sIndex);

	return getMin(0, 0, nodesAmount - 1,fIndex,sIndex);
}

Node *LcaManager::getMin(int v, int vl, int vr,int l,int r) {
	if (vl >= l && vr <= r) {
		return data[v];
	}

	if (l > vr || r < vl) {
		return nullptr;
	}

	int vm = (vl + vr) / 2;
	Node *lNode = getMin(v * 2 + 1, vl, vm, l, r);
	Node *rNode = getMin(v * 2 + 2, vm + 1, vr, l, r);
	if (lNode == nullptr)
		return rNode;
	if (rNode == nullptr)
		return lNode;

	if (lNode->depth > rNode->depth)
		return rNode;
	return lNode;
}

LcaManager::~LcaManager()
{
}
