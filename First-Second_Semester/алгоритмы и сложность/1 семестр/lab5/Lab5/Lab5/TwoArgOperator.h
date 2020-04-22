#pragma once
#include "Operator.h"
#include "Operand.h"
#include <string>
#include <cmath>
class TwoArgOperator : public Operator
{
public:
	TwoArgOperator(std::string operationExpression);
	Operand calculate(Operand a, Operand b);
	int getOperationPriority(std::string operation) override;
	int numOfArguments() override;
	~TwoArgOperator();
};

