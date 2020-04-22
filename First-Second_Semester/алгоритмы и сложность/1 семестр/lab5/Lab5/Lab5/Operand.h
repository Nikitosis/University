#pragma once
#define _CRT_SECURE_NO_WARNINGS

#include "ExpressionItem.h"

#include <fstream> 
#include <iostream> 
#include <stdio.h>
#include <string>
class Operand : public ExpressionItem
{
public:
	Operand(std::string operandString);
	Operand(const Operand & operand);
	Operand(double operandValue);
	double getValue();
	void setValue(double value);
	void setValue(std::string value);
	~Operand();
private:
	bool isAllNumbers(std::string str);
	bool firstIsLetter(std::string str);
	bool isValidVariableName(std::string str);

	double value;
};

