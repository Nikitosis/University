#include "FileSystem.h"
#include "FibonacciHeap.h"
#include <string>
#include <iostream>
#include <cstdlib> 

using namespace std;

int main() {
	std::system("chcp 1251");

	FibonacciHeap heap;

	while (true) {
		cout << "������ ��������� ��������(pop, top, add):";
		string operation;
		cin >> operation;
		if (operation == "add") {
			string name;
			int size;
			cout << "������ ����� �����:";
			cin >> name;
			cout << "������ ����� �����:";
			cin >> size;
			File file(name, size);
			heap.add(file);
			cout << "���� ������" << endl;
		}
		if (operation == "top") {
			File result = heap.top();
			cout << "��������� ���� - " << result.name << endl;
		}
		if (operation == "pop") {
			File result = heap.pop();
			cout << "��������� ���� - " << result.name << endl;
		}
	}
	system("pause");
}