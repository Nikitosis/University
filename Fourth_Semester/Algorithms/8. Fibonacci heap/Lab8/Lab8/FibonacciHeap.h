#pragma once
#include "FileSystem.h"
#include <vector>
using namespace std;
class FibonacciHeap
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
	Node *minNode = nullptr;

	//��������� ���� � ��������� ���������
	Node *findTopNode();
	//�������� ���� � ��������� ���� ������� roots(��� ���� ���� ����������� �� degree)
	void insertNodeToRoots(Node * node);
	//���������� ��� ��� � ������ roots � ��������� degree 
	void resolveMerging();
public:
	FibonacciHeap();
	~FibonacciHeap();

	//��������� ��������
	void add(File file);
	//����������� ���������� ��������
	File top();
	//����������� ���������� �������� �� ���� ���������
	File pop();
};

