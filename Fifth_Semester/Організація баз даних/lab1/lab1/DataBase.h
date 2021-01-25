#pragma once
#include "Company.h"
#include "Employee.h"
#include "IndexAddress.h"
#include "EmployeeUpdateRequest.h"
#include "CompanyUpdateRequest.h"
#define _CRT_SECURE_NO_DEPRECATE
#define INDEX_TABLE_SIZE 1000

class DataBase
{
public:
	void get_m(int index);
	void get_s(int index_m,int index_s);
	void delete_m(int index);
	void delete_s(int index_m, int index_s);
	void update_m(int index, CompanyUpdateRequest *updateRequest);
	void update_s(int index_m, int index_s, EmployeeUpdateRequest *updateRequest);
	void insert_m(Company* company);
	void insert_s(int index_m, Employee* customer);
	void ut_m();
	void ut_s();
	void Init();
private:
	int companyCount = 0;
	int employeeCount = 0;

	int companyEndAddress = 0;
	int employeeEndAddress = 0;
	IndexAddress indexTable[INDEX_TABLE_SIZE];

	const char* filename_m = "Company.bin";
	const char* filename_s = "Customer.bin";
	const char* filename_i = "CompanyIndexes.bin";

	void loadIndexTable();
	void rewriteIndexTable();
	void sortTable();
	int getAddress(int index);
	void deleteOnIndex(int index);
	int getAddressForNewCompany();
	int getCompanyEndAddress();
	void rewriteAddressOfEmployee(int oldAddress, int newAddress);
};

