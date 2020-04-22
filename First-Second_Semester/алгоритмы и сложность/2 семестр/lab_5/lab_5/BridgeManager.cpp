#include "BridgeManager.h"


vector<int> BridgeManager::tIn = {};
vector<int> BridgeManager::tMin = {};
vector<bool> BridgeManager::used = {};
vector<Bridge> BridgeManager::bridges = {};
int BridgeManager::timer = 0;

vector<Bridge> BridgeManager::findBridges(vector<vector<int>>& graph)
{
	int n = graph.size();
	timer = 0;
	tIn.resize(n);
	tMin.resize(n);
	used.resize(n, false);
	bridges.clear();

	for (int i = 0; i < n; i++)
		if (!used[i])
			bridgeDFS(i, -1, graph);

	return bridges;
}

void BridgeManager::bridgeDFS(int v, int p, vector<vector<int>> &graph)
{
	used[v] = true;
	tIn[v] = timer;
	tMin[v] = timer;
	timer++;
	for (int i = 0; i < graph[v].size(); i++) {
		int to = graph[v][i];
		if (to == p)
			continue;
		if (used[to])
			tMin[v] = min(tMin[v], tMin[to]);
		else
		{
			bridgeDFS(to, v, graph);
			tMin[v] = min(tMin[v], tMin[to]);
			if (tIn[v] < tMin[to])
				bridges.push_back({ v,to });
		}
	}
}
