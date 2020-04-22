#include "FibonacciHeap.h"



FibonacciHeap::FibonacciHeap()
{
}


FibonacciHeap::~FibonacciHeap()
{
}

void FibonacciHeap::add(File file)
{
	Node * newNode = new Node(file);
	insertNodeToRoots(newNode);

	//оновлюємо вказівник на найменшу вершину
	if (minNode == nullptr || newNode->file < minNode->file) {
		minNode = newNode;
	}

}

void FibonacciHeap::resolveMerging()
{
	//мерджимо, поки мерджиться
	bool wasMerged = false;
	do {
		wasMerged = false;
		for (int i = 0; i < (int)roots.size() - 1; i++) {
			if (roots[i]->getDegree() == roots[i + 1]->getDegree()) {
				//merge two roots
				if (roots[i]->file < roots[i + 1]->file) {
					roots[i]->children.push_back(roots[i + 1]);
					roots.erase(roots.begin() + i + 1, roots.begin() + i + 2);
					wasMerged = true;
					break;
				}
				else {
					roots[i + 1]->children.push_back(roots[i]);
					roots.erase(roots.begin() + i, roots.begin() + i + 1);
					wasMerged = true;
					break;
				}
			}
		}
	} while (wasMerged);
}

File FibonacciHeap::top()
{
	if (roots.empty()) {
		return File();
	}

	return minNode->file;
}

File FibonacciHeap::pop()
{
	if (roots.empty()) {
		return File();
	}


	//видалити найменшу ноду
	for (int i = 0; i < roots.size(); i++) {
		if (roots[i] == minNode) {
			roots.erase(roots.begin() + i, roots.begin() + i+1);
			break;
		}
	}
	File result = minNode->file;
	//всіх дітей знайденої ноди підіймаємо догори
	for (Node *child : minNode->children) {
		insertNodeToRoots(child);
	}

	//виконуємо операцію merge
	resolveMerging();

	delete minNode;

	//оновлюємо найменшу вершину
	minNode = findTopNode();

	return result;
}

FibonacciHeap::Node * FibonacciHeap::findTopNode()
{
	if (roots.empty()) {
		return nullptr;
	}

	//знаходимо ноду з найменшим елементом
	File minFile = roots[0]->file;
	Node *minNode = roots[0];
	for (int i = 0; i < roots.size(); i++) {
		if (roots[i]->file < minFile) {
			minFile = roots[i]->file;
			minNode = roots[i];
		}
	}

	return minNode;
}

void FibonacciHeap::insertNodeToRoots(Node * node)
{
	bool wasInserted = false;
	for (int i = 0; i < roots.size(); i++) {
		if(node->getDegree()<=roots[i]->getDegree()) {
			roots.insert(roots.begin() + i, node);
			wasInserted = true;
			break;
		}
	}
	if (!wasInserted) {
		roots.insert(roots.end(), node);
	}
}
