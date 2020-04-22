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

	//знаходить ноду з найменшим елементом
	Node *findTopNode();
	//вставляє ноду в необхідне місце вектора roots(щоб ноди були впорядковані за degree)
	void insertNodeToRoots(Node * node);
	//обьеднання всіх нод в векторі roots з однаковим degree 
	void resolveMerging();
public:
	FibonacciHeap();
	~FibonacciHeap();

	//додавання елемента
	void add(File file);
	//знаходження найменшого елемента
	File top();
	//знаходження найменшого елемента та його видалення
	File pop();
};

