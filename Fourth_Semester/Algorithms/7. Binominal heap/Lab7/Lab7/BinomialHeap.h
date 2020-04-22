#pragma once
#include "FileSystem.h"
#include <vector>
using namespace std;
class BinomialHeap
{
private:
	struct Node {
		File file;
		vector<Node*> children;
		Node(File file) {
			this->file = file;
		}
		int getDegree() {
			return children.size();
		}
	};
	vector<Node*> roots;

	//��������� ���� � ��������� ���������
	Node *findTopNode();
	//�������� ���� � ��������� ���� ������� roots(��� ���� ���� ����������� �� degree)
	void insertNodeToRoots(Node * node);
	//���������� ��� ��� � ������ roots � ��������� degree 
	void resolveMerging();
public:
	BinomialHeap();
	~BinomialHeap();

	//��������� ��������
	void add(File file);
	//����������� ���������� ��������
	File top();
	//����������� ���������� �������� �� ���� ���������
	File pop();
};

