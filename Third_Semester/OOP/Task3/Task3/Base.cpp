#include "Base.h"


int Base::counter= 0;

Base::Base()
{
	counter++;
	N = counter;
}

void Base::addSubClass(Base * subClass)
{
	subClasses.push_back(std::shared_ptr<Base>(subClass));
}

void Base::addSubClass(std::shared_ptr<Base> &subClass) {
	subClasses.push_back(subClass);
}

std::vector<std::shared_ptr<Base>> Base::getSubclasses()
{
	return subClasses;
}

int Base::getN()
{
	return N;
}



Base::~Base()
{
	S = 3 * S - N + 21;
}
