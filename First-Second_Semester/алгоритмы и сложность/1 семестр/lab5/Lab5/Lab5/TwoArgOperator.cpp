#include "TwoArgOperator.h"

TwoArgOperator::TwoArgOperator(std::string operationExpression):Operator(operationExpression)
{
	priority = this->getOperationPriority(operationExpression);
}

Operand TwoArgOperator::calculate(Operand a, Operand b)
{
	std::string curOperator = getExpressionString();
	if (curOperator == "+")
		return Operand(a.getValue() + b.getValue());
	if (curOperator == "-")
		return Operand(a.getValue() - b.getValue());
	if (curOperator == "*")
		return Operand(a.getValue() * b.getValue());
	if (curOperator == "/")
	{
		if (b.getValue() == 0)
			throw std::exception("Error: division by zero is not supported");
		return Operand(a.getValue() / b.getValue());
	}
	if (curOperator == "%")
		return Operand((int)a.getValue() % (int)b.getValue());
	if (curOperator == ">>")
		return Operand((int)a.getValue() >> (int)b.getValue());
	if (curOperator == "<<")
		return Operand((int)a.getValue() << (int)b.getValue());
	if (curOperator == "&")
		return Operand((int)a.getValue() & (int)b.getValue());
	if (curOperator == "^")
		return Operand(std::pow(a.getValue(), b.getValue()));
	//	return Operand((int)a.getValue() ^ (int)b.getValue());
	if (curOperator == "|")
		return Operand((int)a.getValue() | (int)b.getValue());


	throw std::exception("Error: cannot calculate OneArgOperator");
}

int TwoArgOperator::getOperationPriority(std::string operation)
{
	if (operation == "^")
		return 1;
	if (operation == "*" || operation == "/" || operation == "%")
		return 2;
	if (operation == "+" || operation == "-")
		return 3;
	if (operation == ">>" || operation == "<<")
		return 4;
	if (operation == "&")
		return 5;
	if (operation == "|")
		return 7;
}

int TwoArgOperator::numOfArguments()
{
	return 2;
}

TwoArgOperator::~TwoArgOperator()
{
}
