#pragma once
#include "Edge.h"
#include <vector>
#include <set>
#include <algorithm>

using namespace std;
struct Segment {
	Segment(){}
	Segment(vector<Edge*> edges):edges(edges){}

	vector<Edge*> edges;

	set<Plane*> getJointPlanes() {
		if (edges.size()<2)
			return {};
		
		set<Plane*> firstEdgePlanes = edges[0]->planes;  
		set<Plane*> lastEdgePlanes = edges[edges.size() - 1]->planes;

		set<Plane*> jointPlanes;
		for (Plane *plane : firstEdgePlanes) {
			if (lastEdgePlanes.find(plane)!=lastEdgePlanes.end()) {
				jointPlanes.insert(plane);
			}
		}

		return jointPlanes;
	}

	Edge* getStartEdge() {
		if (edges.empty())
			return nullptr;
		return edges[0];
	}

	Edge* getEndEdge() {
		if (edges.empty())
			return nullptr;
		return edges[edges.size()-1];
	}
};
