#pragma once
#include "FileSystem.h"
class SplayTree
{
public:
	struct Node {
		File file;
		Node *left;
		Node *right;
		Node *parent;
		Node(File file, Node *parent) :file(file), parent(parent), left(nullptr), right(nullptr) {}
	};

	SplayTree();
	~SplayTree();
	void addElement(File file);
	void deleteElement(File file);
	File findElement(File file);

private:
	Node *root;

	Node* addElementDfs(File file, Node*curNode);
	void leftRotation(Node *curNode);
	void rightRotation(Node *curNode);
	void doubleLeftRotation(Node *curNode);
	void doubleRightRotation(Node *curNode);

	bool isLeftChild(Node* child);
	bool isRightChild(Node *child);

	Node *getUncle(Node *curNode);
	Node *getGrandParent(Node *curNode);

	Node *getNodeByValue(File file);
	Node *getNodeByValueBinary(Node *curNode, File file);

	Node *getMostRightDfs(Node *node);
	Node *getMostLeftDfs(Node *node);
	int getMostLeftHeightDfs(Node *node);
	int getMostRightHeightDfs(Node *node);

	void splay(Node *node);





};

