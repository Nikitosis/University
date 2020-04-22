#include "MyTree.h"



void MyTree::prepareDfsVector(Node * curNode, vector<Node*>& result)
{
	result.push_back(curNode);

	for (Node *node : curNode->children) {
		prepareDfsVector(node, result);
		result.push_back(curNode);
	}

}

MyTree::MyTree()
{
	head = nullptr;
}

void MyTree::buildFromListView(vector<pair<string, string>> list) {
	unordered_map<string, bool> wasNodeChild;
	for (pair<string, string> curPair : list) {
		addBridge(curPair);
	}
}

void MyTree::addBridge(pair<string, string> curPair) {
	string firstName = curPair.first;
	string secondName = curPair.second;
	Node *firstNode;
	Node *secondNode;
	if (nameToNode.find(firstName) != nameToNode.end()) {
		firstNode = nameToNode[firstName];
	}
	else {
		firstNode = new Node(firstName);
		nameToNode.insert({ firstName,firstNode });
	}

	if (nameToNode.find(secondName) != nameToNode.end()) {
		secondNode = nameToNode[secondName];
	}
	else {
		secondNode = new Node(secondName);
		nameToNode.insert({ secondName,secondNode });
	}

	firstNode->addChild(secondNode);
	secondNode->parent = firstNode;

	secondNode->depth = firstNode->depth + 1;



	head = getHeadNode();
}

Node *MyTree::getHeadNode() {
	Node *curNode = (*nameToNode.begin()).second;
	while (curNode->parent != nullptr) {
		curNode = curNode->parent;
	}
	return curNode;
}

vector<Node*> MyTree::getDfsVector()
{
	vector<Node*> result;
	prepareDfsVector(getHeadNode(), result);
	return result;
}



MyTree::~MyTree()
{
}
