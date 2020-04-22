#pragma once
#include "Operand.h"
#include "OneArgOperator.h"
#include "TwoArgOperator.h"
#include "Bracket.h"

#include <string>
#include <deque>
#include <exception>
#include <unordered_map>
class Expression
{
public:
	Expression(std::string expression);
	Expression();
	Operand calculateExpression();
	void setExpression(std::string expression);
	std::string getExpression();
	void fillPolish();
	Operand getPolishCalculated();
	std::string getPolish();
	~Expression();
private:
	void addOperator(Operator *op);
	void addOperand(std::string operand);
	void addPolish(ExpressionItem* item);
	void proceedClosedBracket();

	template<typename Base, typename T>
	bool instanceof(const T*) {
		return std::is_base_of<Base, T>::value;
	}

	std::string mainExpression;
	std::deque<ExpressionItem*> polish;
	std::deque<Operator*> operators;

	std::unordered_map<std::string, Operand*> stringToOperand;
};

