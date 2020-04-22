#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <chrono>

using namespace std;

int** createMatrix(int n) {
	int ** arr = new int*[n];
	for (int i = 0; i < n; i++)
		arr[i] = new int[n];
	return arr;
}

void deleteMatrix(int** matrix, int n) {
	for (int i = 0; i < n; i++)
		delete[] matrix[i];

	delete[] matrix;
}

int** sumMatrix(int** A, int**B, int n) {
	int** res = createMatrix(n);
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			res[i][j] = A[i][j] + B[i][j];
	return res;
}

int** minusMatrix(int** A, int** B, int n) {
	int** res = createMatrix(n);
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			res[i][j] = A[i][j] - B[i][j];
	return res;
}

int** multMatrix(int** A, int** B, int n) {
	int** res = createMatrix(n);
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
		{
			res[i][j] = 0;
			for (int k = 0; k < n; k++) {
				res[i][j] += A[i][k] * B[k][j];
			}
		}
	return res;
}

int** procedeStrassen(int** A, int**B,int n) {
	if (n == 2) {
		return multMatrix(A, B, n);
	}

	int** A11 = createMatrix(n / 2);
	int** A12 = createMatrix(n / 2);
	int** A21 = createMatrix(n / 2);
	int** A22 = createMatrix(n / 2);

	int** B11 = createMatrix(n / 2);
	int** B12 = createMatrix(n / 2);
	int** B21 = createMatrix(n / 2);
	int** B22 = createMatrix(n / 2);

	for(int i=0;i<n/2;i++)
		for (int j = 0; j < n/2; j++)
		{
			A11[i][j] = A[i][j];
			A12[i][j] = A[i][j + n / 2];
			A21[i][j] = A[i + n / 2][j];
			A22[i][j] = A[i + n / 2][j + n / 2];

			B11[i][j] = B[i][j];
			B12[i][j] = B[i][j + n / 2];
			B21[i][j] = B[i + n / 2][j];
			B22[i][j] = B[i + n / 2][j + n / 2];
		}

	//M1 = (A11 + A22) × (B11 + B22)
	int** M1=procedeStrassen(sumMatrix(A11, A22, n / 2), sumMatrix(B11, B22, n / 2),n/2);

	//M2 = (A21 + A22) × B11
	int** M2 = procedeStrassen(sumMatrix(A21, A22, n / 2), B11, n / 2);

	//M3 = A11 × (B12 - B22)
	int** M3 = procedeStrassen(A11, minusMatrix(B12, B22, n / 2), n / 2);

	//M4 = A22 × (B21 - B11)
	int** M4 = procedeStrassen(A22, minusMatrix(B21, B11, n / 2), n / 2);

	//M5 = (A11 + A12) × B22
	int** M5 = procedeStrassen(sumMatrix(A11, A12,n/2), B22, n / 2);

	//M6 = (A21 - A11) × (B11 + B12)
	int** M6 = procedeStrassen(minusMatrix(A21, A11, n / 2), sumMatrix(B11, B12, n / 2), n / 2);

	//M7 = (A12 - A22) × (B21 + B22)
	int** M7 = procedeStrassen(minusMatrix(A12, A22, n / 2), sumMatrix(B21, B22, n / 2), n / 2);

	//C11 = M1 + M4 - M5 + M7
	int** C11 = sumMatrix(M1, M4, n / 2);
	C11 = minusMatrix(C11, M5, n / 2);
	C11 = sumMatrix(C11, M7, n / 2);

	//C12 = M3 + M5
	int** C12 = sumMatrix(M3, M5, n / 2);

	//C21 = M2 + M4
	int** C21 = sumMatrix(M2, M4,n/2);

	//C22 = M1 - M2 + M3 + M6
	int** C22 = minusMatrix(M1, M2, n / 2);
	C22 = sumMatrix(C22, M3, n / 2);
	C22 = sumMatrix(C22, M6, n / 2);

	int** C = createMatrix(n);

	for(int i=0;i<n/2;i++)
		for (int j = 0; j < n / 2; j++) {
			C[i][j] = C11[i][j];
			C[i][j + n / 2] = C12[i][j];
			C[i + n / 2][j] = C21[i][j];
			C[i + n / 2][j + n / 2] = C22[i][j];
		}

	deleteMatrix(A11,n/2);
	deleteMatrix(A12,n/2);
	deleteMatrix(A21,n/2);
	deleteMatrix(A22,n/2);

	deleteMatrix(B11,n/2);
	deleteMatrix(B12,n/2);
	deleteMatrix(B21,n/2);
	deleteMatrix(B22,n/2);

	deleteMatrix(C11,n/2);
	deleteMatrix(C12,n/2);
	deleteMatrix(C21, n / 2);
	deleteMatrix(C22, n / 2);

	deleteMatrix(M1, n / 2);
	deleteMatrix(M2, n / 2);
	deleteMatrix(M3, n / 2);
	deleteMatrix(M4, n / 2);
	deleteMatrix(M5, n / 2);
	deleteMatrix(M6, n / 2);
	deleteMatrix(M7, n / 2);

	return C;
}

int main() {
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);

	int n;
	cin >> n;
	int stepN = 1;

	while (stepN < n) {
		stepN *= 2;
	}
	int**A = createMatrix(stepN);
	int**B = createMatrix(stepN);

	for(int i=0;i<stepN;i++)
		for (int j = 0; j < stepN; j++) {
			A[i][j] = 0;
			B[i][j] = 0;
		}

	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			A[i][j] = rand() % 11;

	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			B[i][j] = rand() % 11;

	cout << "Matrix A:" << endl;

	/*for (int i = 0; i < stepN; i++)
	{
		for (int j = 0; j < stepN; j++)
			cout << A[i][j] << " ";
		cout << endl;
	}
	cout << endl;*/

	cout << "Matrix B:" << endl;

	/*for (int i = 0; i < stepN; i++)
	{
		for (int j = 0; j < stepN; j++)
			cout << B[i][j] << " ";
		cout << endl;
	}
	cout << endl;*/

	auto time = chrono::high_resolution_clock::now();
	int** res = procedeStrassen(A, B, stepN);
	cout << "Time: " << chrono::duration_cast<chrono::nanoseconds>(chrono::high_resolution_clock::now() - time).count() << " nanosec" << endl;


	/*for (int i = 0; i < stepN; i++)
	{
		for (int j = 0; j < stepN; j++)
			cout << res[i][j] << " ";
		cout << endl;
	}*/
	cout << endl;
	cout << "Ordinary multiplication:";
	cout << endl;
	cout << endl;

	time = chrono::high_resolution_clock::now();
	res = multMatrix(A, B, stepN);
	cout << "Time: " << chrono::duration_cast<chrono::nanoseconds>(chrono::high_resolution_clock::now() - time).count() << " nanosec" << endl;

	/*for (int i = 0; i < stepN; i++)
	{
		for (int j = 0; j < stepN; j++)
			cout << res[i][j] << " ";
		cout << endl;
	}*/
}