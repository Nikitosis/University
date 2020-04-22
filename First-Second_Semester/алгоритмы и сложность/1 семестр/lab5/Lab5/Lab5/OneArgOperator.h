#pragma once
#include "Operator.h"
#include "Operand.h"
#include <string>
class OneArgOperator : public Operator
{
public:
	OneArgOperator(std::string operationExpression);
	Operand calculate(Operand a);
	int numOfArguments() override;
	int getOperationPriority(std::string operation) override;
	~OneArgOperator();
private:
	const double PI = 3.14159265358;
};

