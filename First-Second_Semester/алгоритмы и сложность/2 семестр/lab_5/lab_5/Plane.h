#pragma once
#include <vector>

using namespace std;

struct Edge;

struct Plane {
	Plane(){}
	Plane(int index,vector<Edge*> edges):index(index){
		for (Edge *edge : edges) {
			addEdge(edge);
		}
	}
	int index;
	vector<Edge*> edges;

	void deleteEdgesBetween(int startIndex, int endIndex) {
		if (startIndex > endIndex) {
			swap(startIndex, endIndex);
		}
		if (startIndex + 1 <= endIndex - 1) {
			for (int i = startIndex + 1; i <= endIndex - 1; i++) {
				Edge *edge = edges[i];
				edge->planes.erase(this);
			}
			edges.erase(edges.begin() + startIndex+1,edges.begin() + endIndex);
		}
	}

	void addEdge(Edge *edge) {
		edges.push_back(edge);
		edge->planes.insert(this);
	}

	void addEdge(Edge *edge, int index) {
		edges.insert(edges.begin() + index, edge);
		edge->planes.insert(this);
	}

	bool operator<(const Plane& plane) const {
		return index < plane.index;
	}
};
