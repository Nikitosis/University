#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <string>
#include<vector>

using namespace std;

bool isSubString(string source, string target) {
	if (source.length() < target.length())
		return false;

	const int p = 31;
	vector<long long> pows(source.length());
	pows[0] = 1;
	for (int i = 1; i < pows.size(); i++)
		pows[i] = pows[i - 1] * p;

	vector<long long> sourceHash(source.length());
	long long targetHash =0;

	for (int i = 0; i < source.length(); i++) {
		sourceHash[i] = (source[i] - 'a' + 1)*pows[i];
		if (i != 0)
			sourceHash[i] += sourceHash[i - 1];
	}

	
	for (int i = 0; i < target.length(); i++) {
		targetHash += (target[i] - 'a' + 1)*pows[i];
	}

	for (int i = target.length()-1; i < source.length(); i++) {
		long long subHash = sourceHash[i];
		if (int(i - target.length())>= 0)
			subHash -= sourceHash[i - target.length()];
		if (subHash == targetHash * pows[i + 1 - target.length()])             //if hashes are equal
			if(target==source.substr(i-target.length()+1,target.length()))  //if strings are really equal
				return true;
	}

	return false;
}

int main() {
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);

	string source;
	string target;

	cin >> source;
	cin >> target;

	cout<<isSubString(source, target);


}