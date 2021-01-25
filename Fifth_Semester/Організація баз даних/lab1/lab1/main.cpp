#include <iostream>
#include "DataBase.h"
#include "Employee.h"
#include "CompanyUpdateRequest.h"
#include "EmployeeUpdateRequest.h"
using namespace std;

int main()
{
	DataBase dataBase;
	dataBase.Init();
	//1
	char company1Name[40] = "Softserve";
	char company1Oui[30] = "2323-232sd";
	char company1Logo[50] = "http://logo1";
	Company company1(1, company1Name, company1Oui, company1Logo);

	char company2Name[40] = "Epam";
	char company2Oui[30] = "2323-552sd";
	char company2Logo[50] = "http://logo2";
	Company company2(5, company2Name, company2Oui, company2Logo);

	char company3Name[40] = "Arex";
	char company3Oui[30] = "2323-55";
	char company3Logo[50] = "http://logo3";
	Company company3(3, company3Name, company3Oui, company3Logo);

	char company4Name[40] = "Spectral";
	char company4Oui[30] = "2355567777";
	char company4Logo[50] = "http://logo4";
	Company company4(4, company4Name, company4Oui, company4Logo);

	char company5Name[40] = "Mcdonalds";
	char company5Oui[30] = "29090077";
	char company5Logo[50] = "http://logo5";
	Company company5(6, company5Name, company5Oui, company5Logo);

	dataBase.insert_m(&company1);
	dataBase.insert_m(&company2);
	dataBase.insert_m(&company3);
	dataBase.insert_m(&company4);
	dataBase.insert_m(&company5);

	cout << "Printing(1)" << endl;
	dataBase.ut_m();
	dataBase.ut_s();
	cout << endl;

	//2

	int employeeDocument1 = 199;
	char employeeName1[30] = "Alex";
	Employee employee1(employeeDocument1, employeeName1);

	int employeeDocument2 = 200;
	char employeeName2[30] = "Max";
	Employee employee2(employeeDocument2, employeeName2);

	int employeeDocument2_1 = 300;
	char employeeName2_1[30] = "Max2";
	Employee employee2_1(employeeDocument2_1, employeeName2_1);

	int employeeDocument3 = 201;
	char employeeName3[30] = "Igor";
	Employee employee3(employeeDocument3, employeeName3);

	int employeeDocument4 = 202;
	char employeeName4[30] = "Egor";
	Employee employee4(employeeDocument4, employeeName4);

	int employeeDocument5 = 202;
	char employeeName5[30] = "Julia";
	Employee employee5(employeeDocument5, employeeName5);

	dataBase.insert_s(company1.index, &employee1);
	dataBase.insert_s(company2.index, &employee2);
	dataBase.insert_s(company2.index, &employee2_1);
	dataBase.insert_s(company3.index, &employee3);
	dataBase.insert_s(company4.index, &employee4);
	dataBase.insert_s(company5.index, &employee5);

	//3

	cout << "Printing(3):" << endl;
	dataBase.ut_m();
	dataBase.ut_s();
	cout << endl;

	//4
	dataBase.delete_m(company1.index);


	cout << "Printing(4):" << endl;
	dataBase.ut_m();
	dataBase.ut_s();
	cout << endl;

	//5
	dataBase.delete_s(company2.index, 1);


	cout << "Printing(5):" << endl;
	dataBase.ut_m();
	dataBase.ut_s();
	cout << endl;

	//7
	char company6Name[40] = "Main";
	char company6Oui[30] = "2323-232555s";
	char company6Logo[50] = "http://logo6";
	Company company6(10, company6Name, company6Oui, company6Logo);

	int employeeDocument6 = 259;
	char employeeName6[40] = "Emplo";
	Employee employee6(employeeDocument6, employeeName6);

	dataBase.insert_m(&company6);
	dataBase.insert_s(company6.index, &employee6);

	cout << "Printing(6):" << endl;
	dataBase.ut_m();
	dataBase.ut_s();
	cout << endl;

	dataBase.delete_m(company2.index);

	cout << "Printing(7):" << endl;
	dataBase.ut_m();
	dataBase.ut_s();
	cout << endl;

	//9
	CompanyUpdateRequest update6Company;
	strncpy_s(update6Company.name, "New name", sizeof(CompanyUpdateRequest::name));
	strncpy_s(update6Company.oui, "34433434", sizeof(CompanyUpdateRequest::oui));
	strncpy_s(update6Company.logo, "http://newLogo", sizeof(CompanyUpdateRequest::logo));
	dataBase.update_m(company6.index, &update6Company);

	EmployeeUpdateRequest update6Employee;
	update6Employee.document = 10000;
	strncpy_s(update6Employee.name, "new employee", sizeof(EmployeeUpdateRequest::name));

	dataBase.update_s(company6.index, 1, &update6Employee);

	cout << "Printing(9):" << endl;
	dataBase.ut_m();
	dataBase.ut_s();
	cout << endl;

	//Extra:
	cout << "Extra:" << endl;
	cout << "Get company with index 6 (get_m(6)):" << endl;
	dataBase.get_m(6);
	cout << "Get first employee from company with index6 (get_s(6,1)):" << endl;
	dataBase.get_s(6, 1);

	system("pause");
}

