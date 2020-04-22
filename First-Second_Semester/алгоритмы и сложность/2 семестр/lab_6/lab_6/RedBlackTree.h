#pragma once
#define NOMINMAX //для того чтоб не переопределило max
#include <Windows.h>// Обязательное подключение файла
#include <iostream>
#include <algorithm>
#include <vector>
#include <string>

class RedBlackTree
{
public:
	enum Color{RED,BLACK};
	struct Node {
		int value;
		Node *left;
		Node *right;
		Node *parent;
		Color color;
		Node(int value,Color color,Node *parent):value(value),color(color),parent(parent),left(nullptr),right(nullptr){}
	};

	RedBlackTree();
	void addElement(int value);
	void printTree();
	void deleteElement(int value);


private:
	void addElementDfs(int value, Node*curNode);
	void resolveAddConflictsForNode(Node *curNode);
	bool isLeftChild(Node* child);
	bool isRightChild(Node *child);

	void leftRotation(Node *curNode);
	void rightRotation(Node *curNode);
	void doubleLeftRotation(Node *curNode);
	void doubleRightRotation(Node *curNode);
	Node *getUncle(Node *curNode);
	Node *getGrandParent(Node *curNode);
	Node *getSibling(Node *curNode);

	Node *getNodeByValue(int value);
	Node *getMostRightDfs(Node *node);
	Node *getMostLeftDfs(Node *node);
	int getMostLeftHeightDfs(Node *node);
	int getMostRightHeightDfs(Node *node);

	void deleteNode(Node *node);
	void resolveDeleteConflictsForNode(Node *curNode);

	Node *getNodeByValueBinary(Node *curNode, int value);

	int getTreeHeight();
	int heightDfs(Node *curNode);
	void fillNodesPerHeightDfs(Node *curNode,Node ***arr,int curHeight,int vl,int vr);


	Node *root;
};

