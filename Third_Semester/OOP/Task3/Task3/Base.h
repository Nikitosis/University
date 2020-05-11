#include "global.h"

#include <vector>
#include <memory>
#pragma once
class Base
{
private:
	static int counter;
protected:
	int N;
	std::vector<std::shared_ptr<Base>> subClasses;
public:
	Base();
	void addSubClass(Base* subClass);
	void addSubClass(std::shared_ptr<Base> &subClass);
	std::vector<std::shared_ptr<Base>> getSubclasses();
	int getN();
	virtual ~Base();
};

