#include <fstream>
#include <iostream>
#include <cmath>
using namespace std;

ifstream fin("input.in");
ofstream fout("output.out");

int** minorMatrix(int** A, int n, int x, int y) {
	int** B = new int*[n - 1];
	for (int i = 0; i < n - 1; i++)
		B[i] = new int[n - 1];

	int* currentBx = new int;
	int* currentBy = new int;
	*currentBx = 0;
	*currentBy = 0;

	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++) {
			if (i != x && j != y) {
				B[*currentBy][*currentBx] = A[i][j];
				*currentBx = *currentBx + 1;
				if (*currentBx > n - 2) {
					*currentBy = *currentBy + 1;
					*currentBx = 0;
				}
			}
		}
	return B;
	delete currentBx;
	delete currentBy;
	for (int i = 0; i < n - 1; i++)
		delete B[i];
	delete B;
}

void outMatrix(int** A, int n, int m) {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			fout << A[i][j] << " ";
		}
		fout << endl;
	}
}

void closeProgramm() {
	fin.close();
	fout.close();
}

int determinantFindMatrix(int** A, int n) {
	if (n == 1)
		return A[0][0];
	else if (n == 2) {
		return A[0][0] * A[1][1] - A[0][1] * A[1][0];
	}
	else {
		int* result = new int;
		*result = 0;
		for (int i = 0; i < n; i++) {
			if (i % 2 == 0)
				*result += determinantFindMatrix(minorMatrix(A, n, 0, i), n - 1) * A[0][i];
			else
				*result -= determinantFindMatrix(minorMatrix(A, n, 0, i), n - 1) * A[0][i];
		}
		return *result;
		delete result;
	}
}

int main() {
	int n;
	fout << "Enter matrix demension." << endl;
	fin >> n;
	fout << "You entered: " << n << endl;
	int** A = new int*[n];
	for (int i = 0; i < n; i++)
		A[i] = new int[n];

	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			fin >> A[i][j];
	fout << "You've entered next matrix: " << endl;
	outMatrix(A, n, n);
	fout << endl << "Determinant of this matrix = ";
	fout << determinantFindMatrix(A, n);

	for (int i = 0; i < n; i++)
		delete A[i];
	delete A;
	closeProgramm();
}