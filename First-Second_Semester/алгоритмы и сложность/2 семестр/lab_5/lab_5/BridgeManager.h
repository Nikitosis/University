#pragma once
#include <vector>
#include <algorithm>
#include "Bridge.h"
using namespace std;
class BridgeManager
{
public:
	static vector<Bridge> findBridges(vector<vector<int>> &graph);
private:
	static void bridgeDFS(int v, int p,vector<vector<int>> &graph);


	static vector<int> tIn;
	static vector<int> tMin;
	static vector<bool> used;
	static vector<Bridge> bridges;
	static int timer;
};

