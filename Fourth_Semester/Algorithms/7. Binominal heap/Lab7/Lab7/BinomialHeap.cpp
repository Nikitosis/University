#include "BinomialHeap.h"



BinomialHeap::BinomialHeap()
{
}


BinomialHeap::~BinomialHeap()
{
}

void BinomialHeap::add(File file)
{
	Node * newNode = new Node(file);
	insertNodeToRoots(newNode);
	resolveMerging();
}

void BinomialHeap::resolveMerging()
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

File BinomialHeap::top()
{
	if (roots.empty()) {
		return File();
	}

	return findTopNode()->file;
}

File BinomialHeap::pop()
{
	if (roots.empty()) {
		return File();
	}

	//знаходимо найменшу ноду
	Node * top = findTopNode();

	//видалити найменшу ноду
	for (int i = 0; i < roots.size(); i++) {
		if (roots[i] == top) {
			roots.erase(roots.begin() + i, roots.begin() + i+1);
			break;
		}
	}
	File result = top->file;
	//всіх дітей знайденої ноди підіймаємо догори
	for (Node *child : top->children) {
		insertNodeToRoots(child);
	}
	
	//виконуємо операцію merge всіх нод з однаковим degree
	resolveMerging();

	delete top;

	return result;
}

BinomialHeap::Node * BinomialHeap::findTopNode()
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

void BinomialHeap::insertNodeToRoots(Node * node)
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
