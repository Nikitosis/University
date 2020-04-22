#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include "MyTree.h"
#include "LcaManager.h"

using namespace std;

int main() {
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
	MyTree tree;

	while (!cin.eof()) {
		string first, second;
		cin >> first;
		if (first == "-")
			break;
		cin >> second;
		tree.addBridge({ first,second });
	}

	vector<Node*> nodes = tree.getDfsVector();

	/*for (Node *curNode : nodes) {
		cout << curNode->val << endl;
	}
	cout << endl;*/


	LcaManager lca(nodes);

	while (!cin.eof()) {
		string first, second;
		cin >> first >> second;
		Node *ansv = lca.getMinimalParent(first, second);
		cout << ansv->val << endl;
	}
}