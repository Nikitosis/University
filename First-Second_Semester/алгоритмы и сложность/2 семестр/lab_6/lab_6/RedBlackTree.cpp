#include "RedBlackTree.h"

RedBlackTree::RedBlackTree()
{
	root = nullptr;
}

void RedBlackTree::addElement(int value)
{
	if (root == nullptr) {
		root = new Node(value, RED,nullptr);
	}
	else {
		addElementDfs(value, root);
	}

	root->color = BLACK;
}

void RedBlackTree::printTree()
{
	std::cout << getTreeHeight();
	
	int height = getTreeHeight();
	//calculate width of the tree 2^(height)-1
	int allWidth = (1 << height) - 1;

	Node *** nodesPerHeight = new Node**[height];
	for (int i = 0; i < height; i++) {
		nodesPerHeight[i] = new Node*[allWidth];
		for (int j = 0; j < allWidth; j++)
			nodesPerHeight[i][j] = nullptr;
	}

	fillNodesPerHeightDfs(root, nodesPerHeight, 0, 0, allWidth - 1);

	/*for (int curHeight = 1; curHeight <= height; curHeight++) {
		/nt spaceBefore = (curHeight << (height - curHeight)) - 1;
		int spaceBetween = (curHeight << (height - curHeight)) - 1;

		for (int j = 0; j < spaceBefore; j++)
			std::cout << " ";
		for (Node *curNode : nodesPerHeight[curHeight]) {
			std::cout << curNode->value;
			for (int j = 0; j < spaceBetween; j++)
				std::cout << " ";
		}

		std::cout << std::endl;
	}*/


	std::cout << std::endl;

	int spacesAmount = 1;

	for (int i = 0; i < height; i++) {
		for (int j = 0; j < allWidth; j++) {
			Node *curNode = nodesPerHeight[i][j];
			if (curNode != nullptr) {
				spacesAmount = std::max(spacesAmount, (int)std::to_string(curNode->value).length());
			}
		}
	}


	HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hConsole, BACKGROUND_INTENSITY);

	for (int i = 0; i < height; i++) {
		for (int j = 0; j < allWidth; j++)
		{
			if (nodesPerHeight[i][j] == nullptr) {
				for (int k = 0; k < spacesAmount; k++)
					std::cout << " ";
			}
			else {
				Node *curNode = nodesPerHeight[i][j];

				int spacesDelta = std::to_string(curNode->value).length();

				if (curNode->color == BLACK) {

					int curSpacesAmount = spacesAmount-spacesDelta;
					for (int k = 0; k < curSpacesAmount; k++)
						std::cout << " ";

					SetConsoleTextAttribute(hConsole, 0 | BACKGROUND_INTENSITY);
					std::cout << curNode->value;
				}
				else {

					int curSpacesAmount = spacesAmount - spacesDelta;
					for (int k = 0; k < curSpacesAmount; k++)
						std::cout << " ";

					SetConsoleTextAttribute(hConsole, FOREGROUND_RED| BACKGROUND_INTENSITY);
					std::cout << curNode->value;
				}
			}
		}
		std::cout << std::endl;
	}





}

void RedBlackTree::deleteElement(int value)
{

	Node *deletedNode = getNodeByValue(value);
	if (deletedNode == nullptr) {
		return;
	}

	Node *replacedNode;

	if (getMostLeftHeightDfs(deletedNode->right) > getMostRightHeightDfs(deletedNode->left)) {
		replacedNode = getMostLeftDfs(deletedNode->right);
	}
	else {
		replacedNode = getMostRightDfs(deletedNode->left);
	}

	if (replacedNode == nullptr)
		replacedNode = deletedNode;

	deletedNode->value = replacedNode->value;
	

	deleteNode(replacedNode);

	root->color = BLACK;
}

void RedBlackTree::addElementDfs(int value, Node * curNode)
{
	if (curNode->value <= value) {
		if (curNode->right == nullptr) {                    //if right child is empty, then add our Node to the right
			Node *newNode = new Node(value, RED, curNode);
			curNode->right = newNode;
			resolveAddConflictsForNode(newNode);
		}
		else {
			addElementDfs(value, curNode->right);           //else go recursion
		}
	}
	
	if (curNode->value > value) {
		if (curNode->left == nullptr) {						//if left child is empty, then add our Node to the left
			Node *newNode = new Node(value, RED, curNode);
			curNode->left = newNode;
			newNode->parent = curNode;
			resolveAddConflictsForNode(newNode);
		}
		else {												//else go recursion
			addElementDfs(value, curNode->left);
		}
	}

	resolveAddConflictsForNode(curNode);
}

void RedBlackTree::resolveAddConflictsForNode(Node * curNode)
{

	if (curNode->parent == nullptr || !(curNode->parent->color == RED && curNode->color == RED)){
		return;
	}
	//if parent is red(violation)

	if (getUncle(curNode)!=nullptr && getUncle(curNode)->color == RED) {  //if uncle is RED too
		curNode->parent->color = BLACK;
		getUncle(curNode)->color = BLACK;
		getGrandParent(curNode)->color = RED;

		resolveAddConflictsForNode(getGrandParent(curNode));
		return;
	}

	if (getGrandParent(curNode)!=nullptr && isLeftChild(curNode) && isRightChild(curNode->parent)) {  //if triangle situation(right triangle)
		rightRotation(curNode);
		resolveAddConflictsForNode(curNode->right);
	}

	if (getGrandParent(curNode) != nullptr &&  isRightChild(curNode) && isLeftChild(curNode->parent)) {  //if triangle situation(left triangle)
		leftRotation(curNode);
		resolveAddConflictsForNode(curNode->left);
	}

	if (getGrandParent(curNode) != nullptr &&  isRightChild(curNode) && isRightChild(curNode->parent)) { //if line situation(right)
		Node *parent = curNode->parent;
		Node *grandParent = getGrandParent(curNode);
		grandParent->color = RED;
		parent->color = BLACK;
		doubleLeftRotation(curNode);
		return;
	}

	if (getGrandParent(curNode) != nullptr &&  isLeftChild(curNode) && isLeftChild(curNode->parent)) {   //if line situation(left)
		Node *parent = curNode->parent;
		Node *grandParent = getGrandParent(curNode);
		grandParent->color = RED;
		parent->color = BLACK;
		doubleRightRotation(curNode);
		return;
	}
}

bool RedBlackTree::isLeftChild(Node * child)
{
	Node *parent = child->parent;
	return parent->left == child;
}

bool RedBlackTree::isRightChild( Node * child)
{
	return !isLeftChild(child);
}

void RedBlackTree::leftRotation(Node * curNode)
{
	Node *parent = curNode->parent;
	Node *grandParent = getGrandParent(curNode);

	//rotation left
	if (grandParent != nullptr) {
		if (isLeftChild(parent))
			grandParent->left = curNode;
		else
			grandParent->right = curNode;
	}

	if (root == parent) {
		root = curNode;
	}

	Node *wasCurNodeLeft = curNode->left;

	curNode->left = parent;
	curNode->parent = grandParent;

	parent->right = wasCurNodeLeft;
	parent->parent = curNode;

	resolveAddConflictsForNode(parent);
}

void RedBlackTree::rightRotation(Node * curNode)
{
	Node *parent = curNode->parent;
	Node *grandParent = getGrandParent(curNode);


	//rotation
	if (grandParent != nullptr) {
		if (isRightChild(parent))
			grandParent->right = curNode;
		else
			grandParent->left = curNode;
	}
	if (root == parent) {
		root = curNode;
	}

	Node *wasCurNodeRight = curNode->right;

	curNode->right = parent;
	curNode->parent = grandParent;

	parent->left = wasCurNodeRight;
	parent->parent = curNode;

	resolveAddConflictsForNode(parent);
}

void RedBlackTree::doubleLeftRotation(Node * curNode)
{
	leftRotation(curNode->parent);
}

void RedBlackTree::doubleRightRotation(Node * curNode)
{
	rightRotation(curNode->parent);
}

RedBlackTree::Node * RedBlackTree::getUncle(Node * curNode)
{
	Node *parent = curNode->parent;
	Node* grandParent = getGrandParent(curNode);

	if (parent == nullptr || grandParent == nullptr)
		return nullptr;

	if (parent == grandParent->left)
		return grandParent->right;
	return grandParent->left;
}

RedBlackTree::Node * RedBlackTree::getGrandParent(Node * curNode)
{
	if (curNode->parent == nullptr)
	{
		std::cout << "Error in getGrandParent";
		return nullptr;
	}

	return curNode->parent->parent;
}

RedBlackTree::Node * RedBlackTree::getSibling(Node * curNode)
{
	Node *parent = curNode->parent;
	if (parent == nullptr)
		return nullptr;
	if (parent->left == curNode)
		return parent->right;
	return parent->left;
}

RedBlackTree::Node * RedBlackTree::getNodeByValue(int value)
{
	return getNodeByValueBinary(root, value);
}

RedBlackTree::Node * RedBlackTree::getMostRightDfs(Node * node)
{
	if (node != nullptr && node->right != nullptr)
		return getMostRightDfs(node->right);
	return node;
}

RedBlackTree::Node * RedBlackTree::getMostLeftDfs(Node * node)
{
	if (node != nullptr && node->left != nullptr)
		return getMostLeftDfs(node->left);
	return node;
}

int RedBlackTree::getMostRightHeightDfs(Node * node)
{
	if (node != nullptr && node->right != nullptr)
		return getMostRightHeightDfs(node->right)+1;
	return 0;
}

int RedBlackTree::getMostLeftHeightDfs(Node * node)
{
	if (node!=nullptr && node->left != nullptr)
		return getMostLeftHeightDfs(node->left)+1;
	return 0;
}


void RedBlackTree::deleteNode(Node * curNode)
{

	Node *child = (curNode->left != nullptr) ? curNode->left : curNode->right;
	Node *parent = curNode->parent;

	if (curNode == root) {
		root = child;
		root->parent = nullptr;
		delete curNode;
		return;
	}

	if (curNode->color == RED) {
		if (isLeftChild(curNode)) {
			parent->left = child;
		}
		else {
			parent->right = child;
		}

		if (child != nullptr)
			child->parent = parent;

		delete curNode;
		return;
	}
	if (curNode->color == BLACK &&
		child!=nullptr && child->color==RED) {
			if (isLeftChild(curNode)) {
				parent->left = child;
			}
			else {
				parent->right = child;
			}

			child->color = BLACK;

			delete curNode;
			return;
	}



	if (child != nullptr) {
		if (isLeftChild(curNode)) {
			parent->left = child;
		}
		else {
			parent->right = child;
		}

		child->parent = parent;

		resolveDeleteConflictsForNode(child);
	}
	else {
		resolveDeleteConflictsForNode(curNode);
		if (isLeftChild(curNode)) {
			curNode->parent->left = nullptr;
		}
		else {
			curNode->parent->right = nullptr;
		}
	}
	/*else {
		child = new Node(0, BLACK, parent);

		if (isLeftChild(curNode)) {
			parent->left = child;
		}
		else {
			parent->right = child;
		}

		child->parent = parent;

		resolveAddConflictsForNode(child);

		if (isLeftChild(child)) {
			parent->left = nullptr;
		}
		else {
			parent->right = nullptr;
		}

		delete child;
	}*/

	delete curNode;


}

void RedBlackTree::resolveDeleteConflictsForNode(Node * curNode)
{
	if (curNode->color == BLACK && curNode == root) {  //case 3.1(Terminal)
		return;
	}


	if (curNode->color == BLACK &&
		curNode->parent != nullptr && curNode->parent->color == RED) { //case 3.4(Terminal)

		curNode->parent->color = BLACK;

		if (getSibling(curNode) != nullptr) {  // we recolor sibling.
			getSibling(curNode)->color = RED;
		}
		return;
	}

	if (curNode->color == BLACK &&
		getSibling(curNode) != nullptr && getSibling(curNode)->color == BLACK) {	//case 3.6(Terminal)

		if (isRightChild(getSibling(curNode)) &&
			getSibling(curNode)->right != nullptr && getSibling(curNode)->right->color == RED) {


			getSibling(curNode)->right->color = curNode->parent->color;
			curNode->parent->color = BLACK;
			getSibling(curNode)->right->color = BLACK;

			leftRotation(getSibling(curNode));
			return;
		}

		if (isLeftChild(getSibling(curNode)) &&
			getSibling(curNode)->left != nullptr && getSibling(curNode)->left->color == RED) {


			getSibling(curNode)->left->color = curNode->parent->color;
			curNode->parent->color = BLACK;
			getSibling(curNode)->left->color = BLACK;

			rightRotation(getSibling(curNode));
			return;
		}
	}

	if (curNode->color == BLACK && 
		curNode->parent != nullptr && curNode->parent->color == BLACK && 
		getSibling(curNode) != nullptr && getSibling(curNode)->color == RED) { //case 3.2

		getSibling(curNode)->color = BLACK;
		curNode->parent->color = RED;
		if (isLeftChild(curNode)) {
			leftRotation(getSibling(curNode));
		}
		else {
			rightRotation(getSibling(curNode));
		}
		resolveDeleteConflictsForNode(curNode);
		return;	
	}

	if(curNode->color==BLACK && 
		curNode->parent!=nullptr && curNode->parent->color==BLACK &&
		getSibling(curNode)!=nullptr && getSibling(curNode)->color==BLACK &&
		((getSibling(curNode)->left!=nullptr && getSibling(curNode)->left->color==BLACK) || getSibling(curNode)->left==nullptr) &&
		((getSibling(curNode)->right != nullptr && getSibling(curNode)->right->color == BLACK) || getSibling(curNode)->right == nullptr)) { //case 3.3
		
		getSibling(curNode)->color = RED;

		resolveDeleteConflictsForNode(curNode->parent);
		return;
	}

	if (curNode->color == BLACK &&
		curNode->parent != nullptr && curNode->parent->color == BLACK &&
		getSibling(curNode) != nullptr && getSibling(curNode)->color == BLACK) { //case 3.5

		if (isRightChild(getSibling(curNode)) &&
			getSibling(curNode)->left != nullptr && getSibling(curNode)->left->color == RED) { //if sibling is a right child and has 1 LEFT RED child

			getSibling(curNode)->color = RED;
			getSibling(curNode)->left->color = BLACK;
			rightRotation(getSibling(curNode)->left);
			resolveDeleteConflictsForNode(curNode);
			return;
		}

		if (isLeftChild(getSibling(curNode)) &&
			getSibling(curNode)->right != nullptr && getSibling(curNode)->right->color == RED) { //if sibling is a left child and has 1 RIGHT RED child

			getSibling(curNode)->color = RED;
			getSibling(curNode)->right->color = BLACK;
			leftRotation(getSibling(curNode)->right);
			resolveDeleteConflictsForNode(curNode);
			return;
		}
	}



}

RedBlackTree::Node * RedBlackTree::getNodeByValueBinary(Node * curNode, int value)
{
	if (curNode == nullptr)
		return nullptr;
	if (curNode->value == value)
		return curNode;

	if (curNode->value < value)
		return getNodeByValueBinary(curNode->right, value);
	return getNodeByValueBinary(curNode->left, value);
}

int RedBlackTree::getTreeHeight()
{
	if (root == nullptr) {
		return 0;
	}

	return heightDfs(root) - 1;
}

int RedBlackTree::heightDfs(Node * curNode)
{
	if (curNode == nullptr)
		return 1;

	return std::max(heightDfs(curNode->left), heightDfs(curNode->right))+1;

}

void RedBlackTree::fillNodesPerHeightDfs(Node *curNode,Node *** arr, int curHeight, int vl, int vr)
{
	if (curNode == nullptr)
		return;
	int index = (vl + vr) / 2;
	arr[curHeight][index] = curNode;
	fillNodesPerHeightDfs(curNode->left, arr, curHeight + 1, vl, index - 1);
	fillNodesPerHeightDfs(curNode->right, arr, curHeight + 1, index + 1, vr);
}

/*void RedBlackTree::fillNodesPerHeightDfs(Node * curNode, std::vector<std::vector<Node*>> &nodesPerHeight,int curHeight)
{
	if (curNode == nullptr)
		return;
	nodesPerHeight[curHeight].push_back(curNode);
	fillNodesPerHeightDfs(curNode->left, nodesPerHeight, curHeight + 1);
	fillNodesPerHeightDfs(curNode->right, nodesPerHeight, curHeight + 1);
}*/
