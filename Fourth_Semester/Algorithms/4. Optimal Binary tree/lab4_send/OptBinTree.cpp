#include "OptBinTree.h"

#define INF 2e9

OptBinTree::OptBinTree()
{
}

int OptBinTree::minimizeTree()
{
	vector<Data> data;
	vector<int> partSums;
	vector<vector<DpData>> dp;

	fillDataDfs(root, data);
	initDp(data, dp);
	calculatePartSums(data, partSums);
	calculateDp(0, data.size() - 1, partSums, dp);

	root = rebuildTree(0, data.size() - 1, data, dp);

	return dp[0][data.size() - 1].totalCost;
}

File OptBinTree::getFile(string name)
{
	Node *foundNode = getNodeDfs(root, File(name, 0));
	if (foundNode == nullptr) {
		return File();
	}

	foundNode->data.frequency++;

	return foundNode->data.file;
}

void OptBinTree::addFile(File file)
{
	addDataDfs(root, Data(file, 1));
}

OptBinTree::~OptBinTree()
{
}

void OptBinTree::addDataDfs(Node *curNode, Data data)
{
	if (root == nullptr) {
		root = new Node(nullptr, data);
		return;
	}

	if (curNode->data.file < data.file) {
		if (curNode->right == nullptr) {
			curNode->right = new Node(curNode, data);
		}
		else {
			addDataDfs(curNode->right, data);
		}
	}
	else {
		if (curNode->left == nullptr) {
			curNode->left = new Node(curNode, data);
		}
		else {
			addDataDfs(curNode->left, data);
		}
	}
}

OptBinTree::Node *OptBinTree::getNodeDfs(Node * curNode, const File &file)
{
	if (curNode->data.file == file) {
		return curNode;
	}

	if (curNode->data.file < file) {
		return getNodeDfs(curNode->right, file);
	}
	else {
		return getNodeDfs(curNode->left, file);
	}

	return nullptr;
}

void OptBinTree::initDp(const vector<Data> &data,vector<vector<DpData>> &dp)
{
	int n = data.size();
	dp.resize(n);

	for (int i = 0; i < n; i++) {
		dp[i].resize(n);
		for (int j = 0; j < n; j++) {
			dp[i][j] = DpData(INF, -1);
		}
		
		dp[i][i] = DpData(0, i);
	}
}

void OptBinTree::calculatePartSums(const vector<Data>& data, vector<int> &partSums)
{
	partSums.resize(data.size());

	partSums[0] = data[0].frequency;
	for (int i = 1; i < data.size(); i++) {
		partSums[i] = partSums[i - 1] + data[i].frequency;
	}
}

int OptBinTree::calculateDp(int l, int r, vector<int> partSums, vector<vector<DpData>>& dp)
{
	if (l >= r) 
		return 0;
	if (dp[l][r].totalCost != INF) 
		return dp[l][r].totalCost;

	for (int k = l; k <= r; k++) {
		int cost = calculateDp(l, k - 1, partSums, dp) + sum(l, k - 1, partSums) + calculateDp(k + 1, r, partSums, dp) + sum(k + 1, r, partSums);
		if (cost < dp[l][r].totalCost) 
			dp[l][r] = DpData(cost, k);
	}
	return dp[l][r].totalCost;
}

int OptBinTree::sum(int l, int r, const vector<int>& partSums)
{
	if (l > r) {
		return 0;
	}
	return (partSums[r] - (l == 0 ? 0 : partSums[l - 1]));
}

OptBinTree::Node *OptBinTree::rebuildTree(int l, int r, vector<Data> &data, vector<vector<DpData>>& dp)
{
	if (l > r) 
		return nullptr;

	int optRoot = dp[l][r].dataIndex;

	Node *newRoot = new Node(nullptr, data[optRoot]);

	Node *leftChild = rebuildTree(l, optRoot - 1, data, dp);
	if (leftChild) {
		leftChild->parent = newRoot;
	}

	Node *rightChild = rebuildTree(optRoot + 1, r, data, dp);
	if (rightChild) {
		rightChild->parent = newRoot;
	}

	newRoot->left = leftChild;
	newRoot->right = rightChild;

	return newRoot;
}

void OptBinTree::fillDataDfs(Node * curNode, vector<Data>& data)
{
	if (curNode == nullptr) {
		return;
	}

	fillDataDfs(curNode->left, data);
	data.push_back(curNode->data);
	fillDataDfs(curNode->right, data);
}
