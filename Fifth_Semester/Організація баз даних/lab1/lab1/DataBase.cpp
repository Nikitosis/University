#include "DataBase.h"
#include <cstdio>
#include <iostream>
#include "Employee.h"
#include "EmployeeUpdateRequest.h"
#define _CRT_SECURE_NO_WARNINGS
using namespace std;

void DataBase::Init()
{
	FILE* file_m = fopen(filename_m, "wb+");
	fclose(file_m);
	FILE* file_s = fopen(filename_s, "wb+");
	fclose(file_s);
	FILE* file_i = fopen(filename_i, "wb+");
	fclose(file_i);
}

void DataBase::loadIndexTable()
{
	FILE* file_i = fopen(filename_i, "rb+");
	for (int i = 0; i < companyCount; i++) {
		fread(&indexTable[i].index, sizeof(int), 1, file_i);
		fread(&indexTable[i].address, sizeof(int), 1, file_i);
	}
	fclose(file_i);
}

void DataBase::rewriteIndexTable()
{
	FILE* file_i = fopen(filename_i, "wb+");
	for (int i = 0; i < companyCount; i++) {
		fwrite(&indexTable[i].index, sizeof(int), 1, file_i);
		fwrite(&indexTable[i].address, sizeof(int), 1, file_i);
	}
	fclose(file_i);
}

//bulb sorting company
void DataBase::sortTable()
{
	IndexAddress temp;
	for (int i = 0; i < companyCount; i++) {
		for (int j = 0; j < companyCount; j++) {
			if ((indexTable[j].index > indexTable[j + 1].index && indexTable[j + 1].index != NULL && indexTable[j].index != NULL) 
				|| (indexTable[j].index == NULL && indexTable[j + 1].index != NULL))
			{
				temp = indexTable[j];
				indexTable[j] = indexTable[j + 1];
				indexTable[j + 1] = temp;
			}
		}
	}
}

int DataBase::getAddress(int index)
{
	for (int i = 0; i < companyCount; i++) {
		if (indexTable[i].index == index) {
			return indexTable[i].address;
		}
	}
	return -1;
}

void DataBase::deleteOnIndex(int index)
{
	for (int i = 0; i < companyCount; i++) {
		if (indexTable[i].index == index) {
			indexTable[i].index = NULL;
			indexTable[i].address = NULL;
			return;
		}
	}
}

int DataBase::getAddressForNewCompany() {
	int curAddress = 0;
	while (true) {
		bool isAddressFree = true;
		for (int i = 0; i < companyCount; i++) {
			if (indexTable[i].address == curAddress) {
				isAddressFree = false;
				break;
			}
		}
		if (isAddressFree) {
			return curAddress;
		}
		curAddress += sizeof(Company);
	}
}

int DataBase::getCompanyEndAddress() {
	int maxAddress = 0;
	for (int i = 0; i < companyCount; i++) {
		if (indexTable[i].address > maxAddress) {
			maxAddress = indexTable[i].address;
		}
	}

	return maxAddress;
}

void DataBase::rewriteAddressOfEmployee(int oldAddress, int newAddress)
{
	FILE* file_m = fopen(filename_m, "rb+");
	int employeeAddress = -1;
	for (int i = 0; i < companyCount; i++) {
		fseek(file_m, sizeof(Company::index) + sizeof(Company::name) + sizeof(Company::oui) +
			sizeof(Company::logo), SEEK_CUR);
		fread(&employeeAddress, sizeof(int), 1, file_m);
		if (employeeAddress == oldAddress) {
			fseek(file_m, 0 - sizeof(int), SEEK_CUR);
			fwrite(&newAddress, sizeof(int), 1, file_m);
			break;
		}
	}
	fclose(file_m);

	employeeAddress = -1;
	FILE* file_s = fopen(filename_s, "rb+");
	for (int i = 0; i < employeeCount; i++) {
		fseek(file_s, sizeof(Employee::document) + sizeof(Employee::name), SEEK_CUR);
		fread(&employeeAddress, sizeof(int), 1, file_s);
		if (employeeAddress == oldAddress) {
			fseek(file_s,  0 - sizeof(int), SEEK_CUR);
			fwrite(&newAddress, sizeof(int), 1, file_s);
			break;
		}
	}
	fclose(file_s);
}

void DataBase::ut_s()
{
	cout << "ut_s:" << endl;
	FILE* file = fopen(filename_s, "rb+");
	int document;
	char name[40];
	int nextEmployeeAddress;
	for (int i = 0; i < employeeCount; i++) {
		fread(&document, sizeof(Employee::document), 1, file);
		fread(&name, sizeof(Employee::name), 1, file);
		fread(&nextEmployeeAddress, sizeof(int), 1, file);
		cout << "Employee document: " << document << "; name: "<< name << "; next employee address: " << nextEmployeeAddress << endl;
	}

	fclose(file);
}

void DataBase::ut_m()
{
	cout << "ut_m:" << endl;
	FILE* file = fopen(filename_m, "rb+");
	int index;
	char name[40];
	char oui[30];
	char logo[50];
	int firstEmployeeAddress;
	for (int i = 0; i < companyCount; i++) {
		fread(&index, sizeof(int), 1, file);
		fread(&name, sizeof(Company::name), 1, file);
		fread(&oui, sizeof(Company::oui), 1, file);
		fread(&logo, sizeof(Company::logo), 1, file);
		fread(&firstEmployeeAddress, sizeof(int), 1, file);
		cout << "index: " << index << "; name: " << name << "; oui: " << oui << "; logo: " << logo << "; firstEmployeeAddress: " 
			<< firstEmployeeAddress << endl;
	}

	fclose(file);
}


void DataBase::get_m(int index_m)
{
	int offset = getAddress(index_m);
	if (offset == -1) {
		cout << "There is no company at index " << index_m << endl;
		return;
	}
	FILE* file_m = fopen(filename_m, "rb+");
	fseek(file_m, offset, SEEK_SET);

	int index;
	char name[40];
	char oui[30];
	char logo[50];
	int firstEmployeeAddress;
	fread(&index, sizeof(int), 1, file_m);
	fread(&name, sizeof(Company::name), 1, file_m);
	fread(&oui, sizeof(Company::oui), 1, file_m);
	fread(&logo, sizeof(Company::logo), 1, file_m);
	fread(&firstEmployeeAddress, sizeof(int), 1, file_m);

	cout << "index: " << index << "; name: " << name << "; oui: " << oui << "; logo: " << logo << "; firstEmployeeAddress: "
		<< firstEmployeeAddress << endl;

	fclose(file_m);
}

void DataBase::get_s(int index_m, int index_s)
{
	int offset_m = getAddress(index_m);
	if (offset_m == -1) {
		cout << "There is company model at index " << index_m << endl;
		return;
	}
	FILE* file_m = fopen(filename_m, "rb+");
	//find firstEmployeeAddress field
	fseek(file_m, offset_m + sizeof(Company::index) + sizeof(Company::name) + sizeof(Company::oui) +
		sizeof(Company::logo), SEEK_SET);

	int firstEmployeeAddress;

	fread(&firstEmployeeAddress, sizeof(int), 1, file_m);

	fclose(file_m);

	if (firstEmployeeAddress == -1) {
		cout << "There are no employees in this company" << endl;
		return;
	} else {
		FILE* file_s = fopen(filename_s, "rb+");
		fseek(file_s, firstEmployeeAddress, SEEK_SET);
		if (index_s == 1)
		{
			int document;
			char name[40];
			int nextEmployeeAddress;

			fread(&document, sizeof(Employee::document), 1, file_s);
			fread(&name, sizeof(Employee::name), 1, file_s);
			fread(&nextEmployeeAddress, sizeof(int), 1, file_s);
			cout << "Employee document: " << document << "; name: " << name << "; next employee address: " << nextEmployeeAddress << endl;
		}
		else {
			fseek(file_s, sizeof(Employee::document) + sizeof(Employee::name), SEEK_CUR);
			int nextEmployeeAddress;
			//move cusor to desired employee
			for (int i = 1; i < index_s; i++) {
				fread(&nextEmployeeAddress, sizeof(int), 1, file_s);
				fseek(file_s, nextEmployeeAddress + sizeof(Employee::document) + sizeof(Employee::name), SEEK_SET);
			}
			if (nextEmployeeAddress < 0) {
				cout << "There is no employee with index " << index_s <<
					" for a company with index " << index_m << endl;
				return;
			}
			//move cursor to the object's start
			fseek(file_s, 0 - sizeof(Employee::document) - sizeof(Employee::name), SEEK_CUR);

			int document;
			char name[40];

			fread(&document, sizeof(Employee::document), 1, file_s);
			fread(&name, sizeof(Employee::name), 1, file_s);
			fread(&nextEmployeeAddress, sizeof(int), 1, file_s);

			cout << "Employee document: " << document << "; name: " << name << "; next employee address: " << nextEmployeeAddress << endl;
		}
		fclose(file_s);
	}
}

void DataBase::delete_m(int index_m)
{
	int offset_m = getAddress(index_m);
	if (offset_m == -1) {
		cout << "There is no company at index " << index_m << endl;
		return;
	}
	FILE* file_m = fopen(filename_m, "rb+");
	fseek(file_m, getCompanyEndAddress(), SEEK_SET);

	int index;
	char name[40];
	char oui[30];
	char logo[50];
	int firstEmployeeAddress;
	fread(&index, sizeof(int), 1, file_m);
	fread(&name, sizeof(Company::name), 1, file_m);
	fread(&oui, sizeof(Company::oui), 1, file_m);
	fread(&logo, sizeof(Company::logo), 1, file_m);
	fread(&firstEmployeeAddress, sizeof(int), 1, file_m);

	int deletedFirstEmployeeAddress;

	fseek(file_m, offset_m + sizeof(Company::index) + sizeof(Company::name) + sizeof(Company::oui) +
		sizeof(Company::logo), SEEK_SET);

	fread(&deletedFirstEmployeeAddress, sizeof(int), 1, file_m);

	fseek(file_m, offset_m, SEEK_SET);

	fwrite(&index, sizeof(int), 1, file_m);
	fwrite(&name, sizeof(name), 1, file_m);
	fwrite(&oui, sizeof(oui), 1, file_m);
	fwrite(&logo, sizeof(logo), 1, file_m);
	fwrite(&firstEmployeeAddress, sizeof(int), 1, file_m);

	fclose(file_m);

	//delete all employees
	if (deletedFirstEmployeeAddress != -1) {
		int nextEmployeeAddress = deletedFirstEmployeeAddress;
		FILE* file_s = fopen(filename_s, "rb+");

		int document;
		char name[40];
		nextEmployeeAddress;

		int deletedAdress;
		while (nextEmployeeAddress != -1) {
			deletedAdress = nextEmployeeAddress;
			//find next employee address
			fseek(file_s, nextEmployeeAddress + sizeof(Employee::document) + sizeof(Employee::name), SEEK_SET);
			fread(&nextEmployeeAddress, sizeof(int), 1, file_s);

			fseek(file_s, employeeEndAddress - sizeof(Employee), SEEK_SET);
			fread(&document, sizeof(Employee::document), 1, file_s);
			fread(&name, sizeof(Employee::name), 1, file_s);
			fread(&nextEmployeeAddress, sizeof(int), 1, file_s);

			fseek(file_s, deletedAdress, SEEK_SET);
			fwrite(&document, sizeof(document), 1, file_s);
			fwrite(&name, sizeof(name), 1, file_s);
			fwrite(&nextEmployeeAddress, sizeof(int), 1, file_s);
			rewriteAddressOfEmployee(employeeEndAddress - sizeof(Employee), deletedAdress);
			employeeEndAddress -= sizeof(Employee);
			employeeCount--;
		}
		fclose(file_s);
	}

	loadIndexTable();
	deleteOnIndex(index_m);
	sortTable();
	rewriteIndexTable();
	companyCount--;
}

void DataBase::delete_s(int index_m, int index_s)
{
	int offset_m = getAddress(index_m);
	if (offset_m == -1) {
		cout << "There is company at index " << index_m << endl;
		return;
	}
	FILE* file_m = fopen(filename_m, "rb+");
	int firstEmployeeAddress;
	fseek(file_m, offset_m + sizeof(Company::index) + sizeof(Company::name) + sizeof(Company::oui) +
		sizeof(Company::logo), SEEK_SET);
	fread(&firstEmployeeAddress, sizeof(int), 1, file_m);
	if (firstEmployeeAddress == -1) {
		cout << "There are employees for this company" << endl;
		return;
	}
	else {
		FILE* file_s = fopen(filename_s, "rb+");
		int nextEmployeeAddress = firstEmployeeAddress;
		for (int i = 1; i < index_s; i++) {
			fseek(file_s, nextEmployeeAddress + sizeof(Employee::document) + sizeof(Employee::name), SEEK_SET);
			fread(&nextEmployeeAddress, sizeof(int), 1, file_s);
		}

		int deletedAddress = nextEmployeeAddress;

		if (nextEmployeeAddress == -1) {
			cout << "There is employee with index " << index_s <<
				" for a company with index " << index_m;
			return;
		}

		fseek(file_s, employeeEndAddress - sizeof(Employee), SEEK_SET);

		int document;
		char name[40];
		nextEmployeeAddress;
		int deletedNextAddress;

		fread(&document, sizeof(document), 1, file_s);
		fread(&name, sizeof(name), 1, file_s);
		fread(&nextEmployeeAddress, sizeof(int), 1, file_s);

		fseek(file_s, deletedAddress + sizeof(Employee::document) + sizeof(Employee::name), SEEK_SET);
		fread(&deletedNextAddress, sizeof(int), 1, file_s);

		fseek(file_s, 0 - sizeof(Employee), SEEK_CUR);

		fwrite(&document, sizeof(document), 1, file_s);
		fwrite(&name, sizeof(name), 1, file_s);
		fwrite(&nextEmployeeAddress, sizeof(int), 1, file_s);

		fclose(file_s);

		rewriteAddressOfEmployee(deletedAddress, deletedNextAddress);
		rewriteAddressOfEmployee(employeeEndAddress - sizeof(Employee), deletedAddress);

		employeeEndAddress -= sizeof(Employee);
		employeeCount--;
	}
	fclose(file_m);
}

void DataBase::update_m(int index, CompanyUpdateRequest *updateRequest)
{
	int offset = getAddress(index);
	if (offset == -1) {
		cout << "There is no company at index " << index << endl;
		return;
	}
	FILE* file_m = fopen(filename_m, "rb+");
	fseek(file_m, offset + sizeof(Company::index), SEEK_SET);

	fwrite(&updateRequest->name, sizeof(Company::name), 1, file_m);
	fwrite(&updateRequest->oui, sizeof(Company::oui), 1, file_m);
	fwrite(&updateRequest->logo, sizeof(Company::logo), 1, file_m);

	fclose(file_m);
}

void DataBase::update_s(int index_m, int index_s, EmployeeUpdateRequest *updateRequest)
{
	int offset_m = getAddress(index_m);
	if (offset_m == -1) {
		cout << "There is company at index " << index_m << endl;
		return;
	}
	FILE* file_m = fopen(filename_m, "rb+");

	fseek(file_m, offset_m +sizeof(Company::index) + sizeof(Company::name) + sizeof(Company::oui) +
		sizeof(Company::logo), SEEK_SET);

	int firstEmployeeAddress;
	fread(&firstEmployeeAddress, sizeof(int), 1, file_m);

	fclose(file_m);

	if (firstEmployeeAddress == -1) {
		cout << "There are employees in this company" << endl;
		return;
	}
	else {
		FILE* file_s = fopen(filename_s, "rb+");
		fseek(file_s, firstEmployeeAddress, SEEK_SET);
		if (index_s == 1)
		{
			fwrite(&updateRequest->document, sizeof(Employee::document), 1, file_s);
			fwrite(&updateRequest->name, sizeof(Employee::name), 1, file_s);
		}
		else {
			fseek(file_s, sizeof(Employee::document) + sizeof(Employee::name), SEEK_CUR);
			int nextEmployeeAddress;
			for (int i = 1; i < index_s; i++) {
				fread(&nextEmployeeAddress, sizeof(int), 1, file_s);
				fseek(file_s, nextEmployeeAddress + sizeof(Employee::document) + sizeof(Employee::name), SEEK_SET);
			}
			if (nextEmployeeAddress < 0) {
				cout << "There is employee with index " << index_s <<
					" for a company with index " << index_m << endl;
				return;
			}
			fseek(file_s, 0 - sizeof(Employee::document) - sizeof(Employee::name), SEEK_CUR);

			fwrite(&updateRequest->document, sizeof(Employee::document), 1, file_s);
			fwrite(&updateRequest->name, sizeof(Employee::name), 1, file_s);
		}
		fclose(file_s);
	}
}

void DataBase::insert_m(Company* company)
{
	for (int i = 0; i < companyCount; i++) {
		if (indexTable[i].index == company->index) {
			cout << "This key is allready used" << endl;
			return;
		}
	}
	FILE* file = fopen(filename_m, "rb+");
	loadIndexTable();
	int newCompanyAddress = getAddressForNewCompany();
	indexTable[companyCount].index = company->index;
	indexTable[companyCount].address = newCompanyAddress;
	fseek(file, newCompanyAddress, SEEK_SET);

	fwrite(&company->index, sizeof(int), 1, file);
	fwrite(&company->name, sizeof(Company::name), 1, file);
	fwrite(&company->oui, sizeof(Company::oui), 1, file);
	fwrite(&company->logo, sizeof(Company::logo), 1, file);
	fwrite(&company->firstEmployeeAddress, sizeof(int), 1, file);

	sortTable();
	rewriteIndexTable();
	companyCount++;
	fclose(file);
}

void DataBase::insert_s(int index_m, Employee* employee)
{
	FILE* file_s = fopen(filename_s, "rb+");
	fseek(file_s, employeeEndAddress, SEEK_SET);
	employeeEndAddress += sizeof(Employee);

	fwrite(&employee->document, sizeof(Employee::document), 1, file_s);
	fwrite(&employee->name, sizeof(Employee::name), 1, file_s);
	fwrite(&employee->nextEmployeeAddress, sizeof(Employee::nextEmployeeAddress), 1, file_s);

	int offset = getAddress(index_m) + sizeof(Company::index) + sizeof(Company::name) + sizeof(Company::oui) +
		sizeof(Company::logo);

	FILE* file_m = fopen(filename_m, "rb+");
	fseek(file_m, offset, SEEK_SET);

	int firstEmployeeAddress;
	fread(&firstEmployeeAddress, sizeof(int), 1, file_m);
	int address = employeeCount * (sizeof(Employee));
	if (firstEmployeeAddress == -1) {
		fseek(file_m, 0 -sizeof(int), SEEK_CUR);
		fwrite(&address, sizeof(int), 1, file_m);
	}
	else {
		int nextEmployeeAddress = firstEmployeeAddress;
		while (nextEmployeeAddress != -1) {
			fseek(file_s, nextEmployeeAddress + sizeof(Employee::document) + sizeof(Employee::name), SEEK_SET);
			fread(&nextEmployeeAddress, sizeof(int), 1, file_s);
		}
		fseek(file_s, 0-sizeof(int), SEEK_CUR);
		fwrite(&address, sizeof(int), 1, file_s);
	}

	employeeCount++;
	fclose(file_s);
	fclose(file_m);
}
