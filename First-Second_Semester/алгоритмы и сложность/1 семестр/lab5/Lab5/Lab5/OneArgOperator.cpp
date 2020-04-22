#include "OneArgOperator.h"

OneArgOperator::OneArgOperator(std::string operationExpression):Operator(operationExpression)
{
	priority = this->getOperationPriority(operationExpression);
}

Operand OneArgOperator::calculate(Operand a)
{
	std::string curOperator = getExpressionString();
	if (curOperator == "+")
		return Operand(a.getValue());
	if (curOperator == "-")
		return Operand(-a.getValue());
	if (curOperator == "++")
		return Operand(a.getValue() + 1);
	if (curOperator == "--")
		return Operand(a.getValue() - 1);
	if (curOperator == "cos")
		return Operand(cos(a.getValue()/180*PI));
	if(curOperator=="sin")
		return Operand(sin(a.getValue()/180*PI));

	throw std::exception("Error: cannot calculate OneArgOperator. No such operator");
}

int OneArgOperator::numOfArguments()
{
	return 1;
}

int OneArgOperator::getOperationPriority(std::string operation)
{
	if (operation == "++" || operation == "--" || operation == "cos" || operation == "sin")
		return 1;
	if (operation == "+" || operation == "-")
		return 1;
}

OneArgOperator::~OneArgOperator()
{
}
