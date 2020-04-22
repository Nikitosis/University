#pragma once

#include <stdexcept>

template <typename T>
class MyList
{
private:
	struct Node {
		T data;
		Node *nextNode;
		Node() :data(new T()), nextNode(nullptr)
		{}
		Node(T data) : data(data), nextNode(nullptr)
		{}
	};

	Node *root;
	int size;

	void deleteNodes(Node *curNode) {
		if (curNode->nextNode != nullptr)
			deleteNodes(curNode->nextNode);
		delete curNode;
		size--;
		curNode = nullptr;
	}

public:
	MyList() :root(nullptr), size(0)
	{}


	void push_back(T data) {
		if (root == nullptr) {
			root = new Node(data);
			size++;
			return;
		}

		Node *curNode = this->root;
		while (curNode->nextNode != nullptr) {
			curNode = curNode->nextNode;
		}

		Node* newNode = new Node(data);
		curNode->nextNode = newNode;
		size++;
	}

	const T& get(int index) const {
		if (index < 0 || index >= this->size)
			throw std::invalid_argument("Index out of bounds");

		int curIndex = 0;
		Node *curNode = this->root;
		while (curIndex < index) {
			curNode = curNode->nextNode;
			curIndex++;
		}
		return curNode->data;
	}

	int getSize() const {
		return this->size;
	}

	const T& operator[](int index) const {
		return get(index);
	}

	T& operator[](int index) {
		if (index < 0 || index >= this->size)
			throw std::invalid_argument("Index out of bounds");

		int curIndex = 0;
		Node *curNode = this->root;
		while (curIndex < index) {
			curNode = curNode->nextNode;
			curIndex++;
		}
		return curNode->data;
	}

	void deleteIndex(int index) {
		if (index < 0 || index >= this->size)
			throw std::invalid_argument("Index out of bounds");

		if (index == 0) {
			Node *deleteNode = root;
			root = root->nextNode;
			delete deleteNode;
			this->size--;
			return;
		}

		int curIndex = 0;
		Node *curNode = this->root;
		while (curIndex < index - 1) {
			curNode = curNode->nextNode;
			curIndex++;
		}

		Node *deleteNode = curNode->nextNode;
		curNode->nextNode = curNode->nextNode->nextNode;
		this->size--;
		delete deleteNode;
	}

	~MyList() {
		deleteNodes(root);
	}
};

