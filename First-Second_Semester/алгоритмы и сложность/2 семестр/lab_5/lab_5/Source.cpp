#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <vector>

#include "Bridge.h"
#include "BridgeManager.h"
#include "GammaManager.h"

using namespace std;

int main() {
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);

	//считываем кол-во вершин и ребер
	int n, m;
	cin >> n >> m;

	vector<vector<int>> graph(n);
	//считываем список смежности

	for (int i = 0; i < m; i++) {
		int from, to;
		cin >> from >> to;
		from--;
		to--;
		graph[from].push_back(to);
		graph[to].push_back(from);
	}

	vector<Bridge> bridges=BridgeManager::findBridges(graph); //находим мосты

	//удаляем все мосты
	for (Bridge bridge : bridges) {
		int from = bridge.from;
		int to = bridge.to;
		for (int i = 0; i < graph[from].size(); i++) {
			if (graph[from][i] == to) {
				graph[from].erase(graph[from].begin() + i);
				break;
			}
		}

		for (int i = 0; i < graph[to].size(); i++) {
			if (graph[to][i] == from) {
				graph[to].erase(graph[to].begin() + i);
				break;
			}
		}
	}


	if (GammaManager::isGraphPlanar(graph)) {
		cout << "Graph is planar";
	}
	else {
		cout << "Graph is not planar";
	}



	return 0;
}