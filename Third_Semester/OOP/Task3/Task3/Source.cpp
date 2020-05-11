#include "global.h"
#include "Base.h"
#include "Alpha.h"
#include "Beta.h"
#include "Gamma.h"

#include <iostream>
#include <unordered_map>
#include <algorithm>

using namespace std;

int predictS(vector<shared_ptr<Base>> objects);
void setupLinkCounter(shared_ptr<Base> object, unordered_map<Base*, int>& linkCounter);
void predictGlobalVarForObject(shared_ptr<Base> object, int& globalVar, unordered_map<Base*, int>& linkCounter);

int predictS(vector<shared_ptr<Base>> objects) {
	int globalVar = 0;
	unordered_map<Base*, int> linkCounter;
	for (shared_ptr<Base> curObject : objects) {
		setupLinkCounter(curObject, linkCounter);
	}

	for (shared_ptr<Base> curObject : objects) {
		predictGlobalVarForObject(curObject, globalVar,linkCounter);
	}

	return globalVar;
}

void setupLinkCounter(shared_ptr<Base> object, unordered_map<Base*, int>& linkCounter) {
	linkCounter[object.get()]++;
	for (shared_ptr<Base> curObject : object->getSubclasses()) {
		linkCounter[curObject.get()]++;
	}
}

void predictGlobalVarForObject(shared_ptr<Base> object,int& globalVar, unordered_map<Base*, int>& linkCounter) {
	linkCounter[object.get()]--;
	//if no links on this object and it can be deleted
	if (linkCounter[object.get()] == 0) {
		if (Alpha *alpha = dynamic_cast<Alpha*>(object.get())) {
			globalVar = globalVar - alpha->getN() + 5;
			globalVar = 3 * globalVar - alpha->getN() + 21;
		}
		else if (Beta *beta = dynamic_cast<Beta*>(object.get())) {
			globalVar = globalVar + beta->getN();
			globalVar = 3 * globalVar - beta->getN() + 21;
		}
		else if (Gamma *gamma = dynamic_cast<Gamma*>(object.get())) {
			globalVar = globalVar + 2 * gamma->getN() - 21;
			globalVar = 3 * globalVar - gamma->getN() + 21;
		}
		else if (Base *base = dynamic_cast<Base*>(object.get())) {
			globalVar = 3 * globalVar - base->getN() + 21;
		}
		for (shared_ptr<Base> curObject : object->getSubclasses()) {
			predictGlobalVarForObject(curObject,globalVar, linkCounter);
		}
	}
}

void allCombinations(vector<shared_ptr<Base>> objects) {
	int permutationAmount = 1;
	for (int i = 1; i <= objects.size(); i++)
		permutationAmount *= i;
	for (int i = 0; i < permutationAmount; i++) {
		cout << "Predicted S for permutation: " << predictS(objects) << endl;
		std::next_permutation(objects.begin(), objects.end());
	}
}

int main() {
	{
		std::shared_ptr<Base> p1(new Alpha());
		std::shared_ptr<Base> p2(new Gamma());
		std::shared_ptr<Base> p3(new Beta());
		p3->addSubClass(p1);
		p3->addSubClass(p2);
		std::shared_ptr<Base> pAll(new Base());
		pAll->addSubClass(p3);

		cout << "All possible permutations: " << endl;
		allCombinations({ pAll,p3,p2,p1 });

		cout << "Predicted value:"<<predictS({ pAll,p3,p2,p1 }) << endl;
	}

	cout << "Global variable value:"<<S<<endl;
	system("pause");
}