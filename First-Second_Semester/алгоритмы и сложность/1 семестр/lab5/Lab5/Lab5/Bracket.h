#pragma once
#include "Operator.h"
#include <string>
class Bracket : public Operator
{
public:
	Bracket(std::string bracketString);
	int numOfArguments();
	int getOperationPriority(std::string operation) override;
	~Bracket();
};

