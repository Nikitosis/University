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
		cout << "Введіть необхідну операцію(pop, top, add):";
		string operation;
		cin >> operation;
		if (operation == "add") {
			string name;
			int size;
			cout << "Введіть назву файла:";
			cin >> name;
			cout << "Введіть розмір файла:";
			cin >> size;
			File file(name, size);
			heap.add(file);
			cout << "Файл додано" << endl;
		}
		if (operation == "top") {
			File result = heap.top();
			cout << "Найбільший файл - " << result.name << endl;
		}
		if (operation == "pop") {
			File result = heap.pop();
			cout << "Найбільший файл - " << result.name << endl;
		}
	}
	system("pause");
}