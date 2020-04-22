#pragma once
#include "ExpressionItem.h"
class Operator : public ExpressionItem
{
public:
	Operator(std::string expressionString);
	void setExpressionString(std::string expressionString);
	int getPriority() const;
	static bool comparePriorities(const Operator &op1,const Operator &op2);
	virtual int getOperationPriority(std::string operation) =0;
	virtual int numOfArguments() = 0;

	~Operator();
protected:
	int priority;
};

