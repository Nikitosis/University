#pragma once
#include <cstring>


class Company {
public:
	int index;
	char name[40];
	char oui[30];
	char logo[50];
	int firstEmployeeAddress;

	Company(int index, char name[40], char oui[30], char logo[50]) {
		strncpy_s(this->name, name, 40);
		strncpy_s(this->oui, oui, 30);
		strncpy_s(this->logo, logo, 50);
		this->index = index;
		this->firstEmployeeAddress = -1;
	}
};