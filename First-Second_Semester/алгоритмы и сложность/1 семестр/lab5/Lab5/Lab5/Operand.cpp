#include "Operand.h"\

Operand::Operand(std::string operandString) :ExpressionItem(operandString)
{
	
	if (isAllNumbers(operandString))
	{
		size_t *b = new size_t(0);
		setValue(std::stod(operandString.c_str(),b));
		std::cout << *b;
	}
	else //if operandString is variable name
	{
		
		if (!firstIsLetter(operandString))
		{
			throw std::exception("Error: incorrect name of the variable. First symbol of variable is not a letter.");
		}
		
		if(!isValidVariableName(operandString))
			throw std::exception("Error: incorrect name of the variable. It must consist of ONLY letters and numbers");

		//std::streambuf *old_cout_buf = std::cout.rdbuf();
		//std::streambuf *old_cin_buf = std::cin.rdbuf();

		std::string variableValue;
		do {
			std::ifstream console_in("CON");
			std::ofstream console_out("CON");

			console_in.clear();
			console_out.clear();
			console_out << "Enter the value of " << operandString << " :" << std::endl;
			console_in >> variableValue;

		} while (!isAllNumbers(variableValue));



		//std::cout.rdbuf(old_cout_buf);
		//std::cin.rdbuf(old_cin_buf);
	}
}

Operand::Operand(const Operand & operand):ExpressionItem(std::to_string(operand.value))
{
	setValue(operand.value);
}

Operand::Operand(double operandValue):ExpressionItem(std::to_string(operandValue))
{
	setValue(operandValue);
}

double Operand::getValue()
{
	return value;
}

void Operand::setValue(double val)
{
	this->value = val;
}

void Operand::setValue(std::string val)
{
	setValue(atoi(val.c_str()));
}

Operand::~Operand()
{
}

bool Operand::isAllNumbers(std::string str)
{
	bool isAlwaysNumbers = true;
	for (int i = 0; i < str.length(); i++)
		if (!(str[i] >= '0' && str[i] <= '9' || str[i]=='.'))
		{
			if (str[i] == '-' || str[i] == '+' && i == 0)
				continue;
			isAlwaysNumbers = false;
			break;
		}
	return isAlwaysNumbers;
}

bool Operand::firstIsLetter(std::string str)
{
	return ((str[0] >= 'a' && str[0] <= 'z') || (str[0] >= 'A' && str[0] <= 'Z'));
}

bool Operand::isValidVariableName(std::string str)
{
	bool isValidName = true;
	for (int i = 0; i<str.length(); i++)
		if (!((str[0] >= 'a' && str[0] <= 'z') || (str[0] >= 'A' && str[0] <= 'Z') ||
			(str[0] >= '0' && str[0] <= '9')))
		{
			isValidName = false;
			break;
		}
	return isValidName;
}
