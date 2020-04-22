#include "ExpressionItem.h"

ExpressionItem::ExpressionItem(std::string expressionString):expressionString(expressionString)
{
}

std::string ExpressionItem::getExpressionString()
{
	return expressionString;
}

void ExpressionItem::setExpressionString(std::string expressionString)
{
	this->expressionString = expressionString;
}

ExpressionItem::~ExpressionItem()
{
}
