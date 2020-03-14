#include "BinTree.h"



BinTree::BinTree()
{
}

BinTree BinTree::addFile(File file)
{
	if (root == nullptr) {
		return BinTree(new Node(nullptr, file));
	}
	Node * copyRoot = addFileDfs(root, file);
	return BinTree(copyRoot);
}


File BinTree::getFile(string name)
{
	Node *foundNode = findNodeDfs(root, File(name, 0));
	if (foundNode == nullptr) {
		return File();
	}

	return foundNode->file;
}

BinTree::~BinTree()
{
}

BinTree::Node * BinTree::findNodeDfs(Node * curNode, File file)
{
	if (curNode == nullptr) {
		return nullptr;
	}

	if (curNode->file == file) {
		return curNode;
	}

	if (curNode->file < file) {
		return findNodeDfs(curNode->right, file);
	}
	else {
		return findNodeDfs(curNode->left, file);
	}

}

BinTree::Node * BinTree::addFileDfs(Node * curOrigNode, File file)
{
	if (curOrigNode->file < file) {
		Node *copyChildNode;
		if (curOrigNode->right != nullptr) {
			copyChildNode = addFileDfs(curOrigNode->right, file);
		}
		else {
			copyChildNode = new Node(nullptr, file);
		}
		Node *copyCurNode = new Node(nullptr, curOrigNode->file);
		copyCurNode->right = copyChildNode;
		copyCurNode->left = curOrigNode->left;
		copyChildNode->parent = copyCurNode;
		return copyCurNode;
	}
	else {
		Node *copyChildNode;
		if (curOrigNode->left != nullptr) {
			copyChildNode = addFileDfs(curOrigNode->left, file);
		}
		else {
			copyChildNode = new Node(nullptr, file);
		}
		Node *copyCurNode = new Node(nullptr, curOrigNode->file);
		copyCurNode->left = copyChildNode;
		copyCurNode->right = curOrigNode->right;
		copyChildNode->parent = copyCurNode;
		return copyCurNode;
	}
}
