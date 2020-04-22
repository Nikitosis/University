#pragma once
#include <string>
class ExpressionItem
{
public:
	ExpressionItem(std::string expressionString);
	virtual std::string getExpressionString();
	virtual void setExpressionString(std::string expressionString);
	~ExpressionItem();
protected:
	std::string expressionString;
};

