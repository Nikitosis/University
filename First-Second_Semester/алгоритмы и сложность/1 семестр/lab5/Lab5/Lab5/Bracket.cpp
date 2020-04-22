#include "Bracket.h"

Bracket::Bracket(std::string bracketString):Operator(bracketString)
{
	priority = this->getOperationPriority(bracketString);
}

int Bracket::numOfArguments()
{
	return 0;
}

int Bracket::getOperationPriority(std::string operation)
{
	if (operation == "(")
		return 8;
	if (operation == ")")
		return 8;
}

Bracket::~Bracket()
{
}
