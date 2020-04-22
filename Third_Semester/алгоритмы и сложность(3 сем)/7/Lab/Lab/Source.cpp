#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <string>
#include<vector>

using namespace std;

bool isSubMatrix(char** source,char** target,int n,int m) {
	if (n < m)
		return false;

	const int p = 1000000007;

	long long* pows = new long long[n];

	long long curPow = 1;
	for(int i=0;i<n;i++){
		pows[i] = curPow;
		curPow *= p;
	}

	long long** sourceHash = new long long*[n];
	for (int i = 0; i < n; i++)
		sourceHash[i] = new long long[n];


	long long* targetHash = new long long[m];

	for (int i = 0; i < n; i++)
		for(int j=0;j<n;j++){
			sourceHash[i][j] = (source[i][j] - 'a' + 1)*pows[j];
			if (j != 0)
				sourceHash[i][j] += sourceHash[i][j-1];
	}


	for (int i = 0; i < m; i++) {
		long long curHash = 0;
		for (int j = 0; j < m; j++) {
			curHash += (target[i][j] - 'a' + 1)*pows[j];
		}
		targetHash[i] = curHash;
	}

	
	for(int i=0;i<n-m+1;i++)
		for (int j = m - 1; j < n; j++) {
			long long subHash = sourceHash[i][j];
			if (j!=m-1)
				subHash -= sourceHash[i][j - m];
		
			if (subHash == targetHash[0] * pows[j + 1 - m]) {
				//if we found that first source substring is equal to first target string, than we check every substring under
				bool isAllEqual = true;
				for (int k = i + 1; k < i + m; k++) {
					subHash = sourceHash[k][j];
					if (j!=m-1)
						subHash -= sourceHash[k][j - m];
					if (subHash != targetHash[k-i] * pows[j + 1 - m]) {
						isAllEqual = false;
						break;
					}
				}

				//check if they are really equal
				if (isAllEqual) {
					for (int i1 = i; i1 < i + m; i1++) {
						if (!isAllEqual)
							break;
						for (int j1 = j-m+1; j1 < j; j1++)
							if (source[i1][j1] != target[i1-i][m-j-1+j1])
							{
								isAllEqual = false;
								break;
							}
					}
				}
				if (isAllEqual)
					return true;
			}
		}

	return false;
}

int main() {
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
	int n, m;
	cin >> n >> m;
	char** source = new char*[n];
	for (int i = 0; i < n; i++)
		source[i] = new char[n];

	char** target = new char*[n];
	for (int i = 0; i < n; i++)
		target[i] = new char[n];

	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			cin >> source[i][j];

	for (int i = 0; i < m; i++)
		for (int j = 0; j < m; j++)
			cin >> target[i][j];

	cout << isSubMatrix(source, target, n, m);


}