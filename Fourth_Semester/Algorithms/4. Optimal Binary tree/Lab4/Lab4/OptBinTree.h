#pragma once
#include <vector>
#include <string>
#include "FileSystem.h"
using std::string;
using std::vector;
class OptBinTree
{
public:
	OptBinTree();
	int minimizeTree();
	File getFile(string name);
	void addFile(File file);
	~OptBinTree();
private:


	struct Data {
		int frequency;
		File file;
		Data(File file, int frequency):file(file), frequency(frequency){}
	};
	struct DpData {
		int totalCost;
		int dataIndex;
		DpData(int totalCost, int dataIndex):totalCost(totalCost), dataIndex(dataIndex){}
		DpData(){}
	};
	struct Node
	{
	public:
		Node(Node *parent, Data data) :parent(parent), data(data) {}

		Node* left = nullptr;
		Node* right = nullptr;
		Node* parent = nullptr;
		Data data;
	};

	Node *root = nullptr;

	void addDataDfs(Node *curNode, Data data);
	Node *getNodeDfs(Node * curNode,const File &file);

	void initDp(const vector<Data> &data, vector<vector<DpData>> &dp);
	void calculatePartSums(const vector<Data> &data, vector<int> &partSums);
	int calculateDp(int l, int r, vector<int> partSums, vector<vector<DpData>>& dp);
	int sum(int l, int r, const vector<int> &partSums);
	Node *rebuildTree(int l, int r, vector<Data> &data, vector<vector<DpData>>& dp);
	void fillDataDfs(Node * curNode, vector<Data>& data);
};

