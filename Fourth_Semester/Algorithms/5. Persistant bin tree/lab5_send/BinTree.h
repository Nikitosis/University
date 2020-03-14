#pragma once
#include "FileSystem.h"
#include <vector>
#include <string>

using std::string;
using std::vector;
class BinTree
{
public:
	BinTree();
	BinTree addFile(File file);
	File getFile(string name);
	~BinTree();
private:
	struct Node {
		Node(Node *parent, File file) :parent(parent), file(file) {}
		File file;
		Node *left = nullptr;
		Node *right = nullptr;
		Node *parent;
	};

	BinTree(Node *root) :root(root){}
	Node *findNodeDfs(Node *curNode, File file);
	Node *addFileDfs(Node *curOrigNode, File file);

	Node *root;
};

