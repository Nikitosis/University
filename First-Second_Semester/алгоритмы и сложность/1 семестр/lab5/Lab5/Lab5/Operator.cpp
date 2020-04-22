#include "Operator.h"

Operator::Operator(std::string expressionString): ExpressionItem(expressionString)
{
	//priority = getOperationPriority(expressionString);
}

void Operator::setExpressionString(std::string expressionString)
{
	ExpressionItem::setExpressionString(expressionString);
	priority = getOperationPriority(expressionString);
}

int Operator::getPriority() const
{
	return this->priority;
}

bool Operator::comparePriorities(const Operator & op1, const Operator & op2)
{
	return op1.getPriority() <= op2.getPriority();
}

/*int Operator::getOperationPriority(std::string operation)
{
	if (operation == "++" || operation == "--" || operation=="cos" || operation=="sin")
		return 1;
	if (operation == "*" || operation == "/" || operation == "%")
		return 2;
	if (operation == "+" || operation == "-")
		return 3;
	if (operation == ">>" || operation == "<<")
		return 4;
	if (operation == "&")
		return 5;
	if (operation == "^")
		return 6;
	if (operation == "|")
		return 7;
	if (operation == "(")
		return 8;
	if (operation == ")")
		return 8;
}*/

Operator::~Operator()
{
}
