#include "SplayTree.h"



SplayTree::SplayTree()
{
}


SplayTree::~SplayTree()
{
}

void SplayTree::addElement(File file)
{
	if (root == nullptr) {
		root = new Node(file, nullptr);
	}
	else {
		Node *newNode = addElementDfs(file, root);
		splay(newNode);
	}
}

File SplayTree::findElement(File file)
{
	Node * node = getNodeByValue(file);

	if (node == nullptr)
		return File();

	splay(node);
	return node->file;
}

SplayTree::Node *SplayTree::addElementDfs(File file, Node * curNode)
{
	if (curNode->file <= file) {
		if (curNode->right == nullptr) {                    //if right child is empty, then add our Node to the right
			Node *newNode = new Node(file, curNode);
			curNode->right = newNode;
			newNode->parent = curNode;
			return newNode;
		}
		else {
			return addElementDfs(file, curNode->right);           //else go recursion
		}
	}

	if (curNode->file > file) {
		if (curNode->left == nullptr) {						//if left child is empty, then add our Node to the left
			Node *newNode = new Node(file, curNode);
			curNode->left = newNode;
			newNode->parent = curNode;
			return newNode;
		}
		else {												//else go recursion
			return addElementDfs(file, curNode->left);
		}
	}
}

bool SplayTree::isLeftChild(Node * child)
{
	Node *parent = child->parent;
	return parent->left == child;
}

bool SplayTree::isRightChild(Node * child)
{
	return !isLeftChild(child);
}

void SplayTree::leftRotation(Node * curNode)
{
	Node *parent = curNode->parent;
	Node *grandParent = getGrandParent(curNode);

	//лівий поворот
	if (grandParent != nullptr) {
		if (isLeftChild(parent))
			grandParent->left = curNode;
		else
			grandParent->right = curNode;
	}

	if (root == parent) {
		root = curNode;
	}

	Node *wasCurNodeLeft = curNode->left;

	curNode->left = parent;
	curNode->parent = grandParent;

	parent->right = wasCurNodeLeft;
	parent->parent = curNode;
}

void SplayTree::rightRotation(Node * curNode)
{
	Node *parent = curNode->parent;
	Node *grandParent = getGrandParent(curNode);


	//правий поворот
	if (grandParent != nullptr) {
		if (isRightChild(parent))
			grandParent->right = curNode;
		else
			grandParent->left = curNode;
	}
	if (root == parent) {
		root = curNode;
	}

	Node *wasCurNodeRight = curNode->right;

	curNode->right = parent;
	curNode->parent = grandParent;

	parent->left = wasCurNodeRight;
	parent->parent = curNode;
}

void SplayTree ::doubleLeftRotation(Node * curNode)
{
	leftRotation(curNode->parent);
}

void SplayTree::doubleRightRotation(Node * curNode)
{
	rightRotation(curNode->parent);
}

SplayTree::Node * SplayTree::getUncle(Node * curNode)
{
	Node *parent = curNode->parent;
	Node* grandParent = getGrandParent(curNode);

	if (parent == nullptr || grandParent == nullptr)
		return nullptr;

	if (parent == grandParent->left)
		return grandParent->right;
	return grandParent->left;
}

SplayTree::Node * SplayTree::getGrandParent(Node * curNode)
{
	if (curNode->parent == nullptr)
	{
		//std::cout << "Error in getGrandParent";
		return nullptr;
	}

	return curNode->parent->parent;
}

SplayTree::Node * SplayTree::getNodeByValue(File file)
{
	return getNodeByValueBinary(root, file);
}

SplayTree::Node * SplayTree::getNodeByValueBinary(Node * curNode, File file)
{
	if (curNode == nullptr)
		return nullptr;
	if (curNode->file == file)
		return curNode;

	if (curNode->file < file)
		return getNodeByValueBinary(curNode->right, file);
	return getNodeByValueBinary(curNode->left, file);
}

void SplayTree::splay(Node * node)
{
	while (node != root) {
		if (getGrandParent(node) != nullptr) {
			if (isLeftChild(node->parent) && isRightChild(node)) {
				leftRotation(node);
				rightRotation(node);
				continue;
			}
			if (isRightChild(node->parent) && isLeftChild(node)) {
				rightRotation(node);
				leftRotation(node);
				continue;
			}

			if (isLeftChild(node->parent) && isLeftChild(node)) {
				doubleRightRotation(node);
				rightRotation(node);
				continue;
			}

			if (isRightChild(node->parent) && isRightChild(node)) {
				doubleLeftRotation(node);
				leftRotation(node);
				continue;
			}
		}
		else {
			if (isLeftChild(node)) {
				rightRotation(node);
				continue;
			}
			if (isRightChild(node)) {
				rightRotation(node);
				continue;
			}
		}
	}
}

void SplayTree::deleteElement(File file)
{

	Node *deletedNode = getNodeByValue(file);
	if (deletedNode == nullptr) {
		return;
	}

	//заміняємо вершину
	Node *replacedNode;

	if (getMostLeftHeightDfs(deletedNode->right) > getMostRightHeightDfs(deletedNode->left)) {
		replacedNode = getMostLeftDfs(deletedNode->right);
	}
	else {
		replacedNode = getMostRightDfs(deletedNode->left);
	}

	if (replacedNode == nullptr)
		replacedNode = deletedNode;

	deletedNode->file = replacedNode->file;

	if (isLeftChild(replacedNode)) {
		replacedNode->parent->left = nullptr;
	}
	if (isRightChild(replacedNode)) {
		replacedNode->parent->right = nullptr;
	}
	delete replacedNode;

	if (deletedNode->parent != nullptr) {
		splay(deletedNode->parent);
	}
}

SplayTree::Node * SplayTree::getMostRightDfs(Node * node)
{
	if (node != nullptr && node->right != nullptr)
		return getMostRightDfs(node->right);
	return node;
}

SplayTree::Node * SplayTree::getMostLeftDfs(Node * node)
{
	if (node != nullptr && node->left != nullptr)
		return getMostLeftDfs(node->left);
	return node;
}

int SplayTree::getMostRightHeightDfs(Node * node)
{
	if (node != nullptr && node->right != nullptr)
		return getMostRightHeightDfs(node->right) + 1;
	return 0;
}

int SplayTree::getMostLeftHeightDfs(Node * node)
{
	if (node != nullptr && node->left != nullptr)
		return getMostLeftHeightDfs(node->left) + 1;
	return 0;
}





