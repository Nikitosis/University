#pragma once

#include <cstring>

class Employee {
public:
	int document;
	char name[40];
	int nextEmployeeAddress;


	Employee(long document, char name[40]) {
		this->document = document;
		strncpy_s(this->name, name, 40);
		this->nextEmployeeAddress = -1;
	}
};