#pragma once
#define NOMINMAX //для того чтоб не переопределило max
#include <Windows.h>// Обязательное подключение файла
#include <iostream>
#include <algorithm>
#include <vector>
#include <string>
#include "FileSystem.h"

class RedBlackTree
{
public:
	enum Color{RED,BLACK};
	struct Node {
		File file;
		int size;
		Node *left;
		Node *right;
		Node *parent;
		Color color;
		Node(File file,Color color,Node *parent):file(file),color(color),parent(parent),left(nullptr),right(nullptr),size(1){}
	};

	RedBlackTree();
	void addElement(File file);
	void printTree();
	void deleteElement(File file);
	File findNth(int n);


private:
	void addElementDfs(File file, Node*curNode);
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

	Node *getNodeByValue(File file);
	Node *getMostRightDfs(Node *node);
	Node *getMostLeftDfs(Node *node);
	int getMostLeftHeightDfs(Node *node);
	int getMostRightHeightDfs(Node *node);

	void deleteNode(Node *node);
	void resolveDeleteConflictsForNode(Node *curNode);

	Node *getNodeByValueBinary(Node *curNode, File file);

	int getTreeHeight();
	int heightDfs(Node *curNode);
	void fillNodesPerHeightDfs(Node *curNode,Node ***arr,int curHeight,int vl,int vr);

	int calculateSize(Node *node);
	Node *findNthDfs(Node *curNode, int n);


	Node *root;
};

