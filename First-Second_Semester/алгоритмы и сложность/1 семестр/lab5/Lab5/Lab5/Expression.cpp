#include "Expression.h"

Expression::Expression(std::string expression):mainExpression(expression)
{
}

Expression::Expression():mainExpression("")
{
}

Operand Expression::calculateExpression() //todo
{
	fillPolish();
	return getPolishCalculated();
}

void Expression::setExpression(std::string expression)
{
	this->mainExpression = expression;
}

void Expression::addOperator(Operator *op)
{
	if (op->getExpressionString() == ")")
	{
		operators.push_back(op);
		proceedClosedBracket();
		return;
	}
	if (op->getExpressionString() == "(")
	{
		operators.push_back(op);
		return;
	}

	if (!operators.empty())
		if(Operator::comparePriorities(*operators.back(), *op))
		{
			addPolish(operators.back());
			operators.pop_back();
			operators.push_back(op);
			return;
		}
	operators.push_back(op);
}

void Expression::addOperand(std::string operand)
{
	if (stringToOperand.find(operand) == stringToOperand.end())
	{
		stringToOperand[operand] = new Operand(operand);
	}
	addPolish(stringToOperand[operand]);
}

void Expression::addPolish(ExpressionItem * item)
{
	polish.push_back(item);
}

void Expression::proceedClosedBracket()
{
	bool isFoundOpenBracket = false;
	if (operators.back()->getExpressionString() == ")")
	{
		operators.pop_back();

		while (!operators.empty())
		{
			if (operators.back()->getExpressionString() == "(")
			{
				operators.pop_back();
				isFoundOpenBracket = true;
				break;
			}
			addPolish(operators.back());
			operators.pop_back();
		}
		if (!isFoundOpenBracket)
			throw std::exception("Error:Closed bracket without opened one");
	}
}

void Expression::fillPolish()
{
	polish.clear();
	operators.clear();

	std::string curOperand = "";
	int lastOperandIndex = -1;
	int lastTwoArgOperatorIndex = -1;
	for (int i = 0; i < mainExpression.length(); i++)
	{

		if (mainExpression[i] == ' ')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			continue;
		}
		if (i < mainExpression.length() - 2 && mainExpression[i] == 's' && mainExpression[i + 1] == 'i' && mainExpression[i + 2] == 'n')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new OneArgOperator("sin"));
			i+=2;
			continue;
		}
		if (i < mainExpression.length() - 2 && mainExpression[i] == 'c' && mainExpression[i + 1] == 'o' && mainExpression[i + 2] == 's')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new OneArgOperator("cos"));
			i+=2;
			continue;
		}
		if (i < mainExpression.length() - 1 && mainExpression[i] == '+' && mainExpression[i + 1] == '+')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new OneArgOperator("++"));
			i++;
			continue;
		}
		if (i < mainExpression.length() - 1 && mainExpression[i] == '-' && mainExpression[i + 1] == '-')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new OneArgOperator("--"));
			i++;
			continue;
		}
		if (mainExpression[i] == '+')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}

			if (lastTwoArgOperatorIndex >= lastOperandIndex) //if we have unar operation
			{
				addOperator(new OneArgOperator("+"));
			}
			else //we have binary operation
			{
				addOperator(new TwoArgOperator("+"));
				lastTwoArgOperatorIndex = i;
			}
			continue;
		}
		if (mainExpression[i] == '-')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			if (lastTwoArgOperatorIndex >= lastOperandIndex) //if we have unar operation
			{
				addOperator(new OneArgOperator("-"));
			}
			else //we have binary operation
			{
				addOperator(new TwoArgOperator("-"));
				lastTwoArgOperatorIndex = i;
			}
			continue;
		}
		if (mainExpression[i] == '*')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new TwoArgOperator("*"));
			lastTwoArgOperatorIndex = i;
			continue;
		}
		if (mainExpression[i] == '/')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new TwoArgOperator("/"));
			lastTwoArgOperatorIndex = i;
			continue;
		}
		if (mainExpression[i] == '%')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new TwoArgOperator("%"));
			lastTwoArgOperatorIndex = i;
			continue;
		}
		if (i < mainExpression.length() - 1 && mainExpression[i] == '<' && mainExpression[i + 1] == '<')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new TwoArgOperator("<<"));
			lastTwoArgOperatorIndex = i;
			i++;
			continue;
		}
		if (i < mainExpression.length() - 1 && mainExpression[i] == '>' && mainExpression[i + 1] == '>')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new TwoArgOperator(">>"));
			lastTwoArgOperatorIndex = i;
			i++;
			continue;
		}
		if (mainExpression[i] == '&')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new TwoArgOperator("&"));
			lastTwoArgOperatorIndex = i;
			continue;
		}
		if (mainExpression[i] == '^')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new TwoArgOperator("^"));
			lastTwoArgOperatorIndex = i;
			continue;
		}
		if (mainExpression[i] == 'cos')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new OneArgOperator("cos"));
			continue;
		}
		if (mainExpression[i] == 'sin')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new OneArgOperator("sin"));
			continue;
		}
		if (mainExpression[i] == '|')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new TwoArgOperator("|"));
			lastTwoArgOperatorIndex = i;
			continue;
		}
		if (mainExpression[i] == '(')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new Bracket("("));
			continue;
		}
		if (mainExpression[i] == ')')
		{
			if (curOperand != "")
			{
				addOperand(curOperand);
				curOperand = "";
			}
			addOperator(new Bracket(")"));
			continue;
		}

		curOperand += mainExpression[i];
		lastOperandIndex = i;
	}
	if (curOperand != "")
	{
		addOperand(curOperand);
		curOperand = "";
	}
	if (lastOperandIndex < lastTwoArgOperatorIndex)
		throw std::exception("Error: operand expected, but only operator found");
	while (!operators.empty())
	{
		addPolish(operators.back());
		operators.pop_back();
	}
}

Operand Expression::getPolishCalculated()
{
	for(int i=0;i<polish.size();i++)
	{
		ExpressionItem *curItem = polish.at(i);

		Operator* op = dynamic_cast<Operator*>(curItem);
		//OneArgOperator *oneArg = static_cast<OneArgOperator*>(curItem);
		//TwoArgOperator *twoArg= static_cast<TwoArgOperator*>(curItem);
		

		if (op!=nullptr && op->numOfArguments()==1)
		{
			if (i - 1 < 0)
				throw std::exception("Error:One value in operation expected, but have less");

			OneArgOperator *oneArg = dynamic_cast<OneArgOperator*>(op);
			Operand *prevOperand = dynamic_cast<Operand*>(polish.at(i-1));

			if (prevOperand == nullptr)
				throw std::exception("Error: Operand expected in Operation");

			Operand *result = new Operand(oneArg->calculate(*prevOperand));
			polish.insert(polish.begin()+i+1 , result);
			polish.erase(polish.begin() + i);
			polish.erase(polish.begin() + i - 1);
			i = 0;
		}
		if (op!=nullptr && op->numOfArguments() == 2)
		{
			if (i - 2 < 0)
				throw std::exception("Error:Two values in operation expected, but have less");

			TwoArgOperator *twoArg = dynamic_cast<TwoArgOperator*>(op);
			Operand *prevOperand = dynamic_cast<Operand*>(polish.at(i-1));
			Operand *prevPrevOperand = dynamic_cast<Operand*>(polish.at(i-2));

			if (prevOperand == nullptr || prevPrevOperand==nullptr)
				throw std::exception("Error: Operand expected in Operation");

			Operand *result = new Operand(twoArg->calculate(*prevPrevOperand,*prevOperand));
			polish.insert(polish.begin() + i+1, result);
			polish.erase(polish.begin() + i);
			polish.erase(polish.begin() + i - 1);
			polish.erase(polish.begin() + i - 2);
			i = 0;
		}
		if (op!=nullptr && op->getExpressionString() == "(")
			throw std::exception("Opened bracked wothout closed one!");
	}
	if (polish.size() != 1)
		throw std::exception("Wrong output in getPolishCalculated.");

	Operand *result = dynamic_cast<Operand*>(polish.at(0));
	return *result;
}

std::string Expression::getPolish()
{
	std::string curPolish;
	for (ExpressionItem *item : polish)
	{
		curPolish += item->getExpressionString()+" ";
	}
	return curPolish;
}

std::string Expression::getExpression()
{
	return mainExpression;
}

Expression::~Expression()
{
}
