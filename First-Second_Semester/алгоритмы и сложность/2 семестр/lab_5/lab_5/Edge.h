#pragma once
#include <set>

using namespace std;

struct Plane;

struct Edge {
	Edge(){}
	Edge(int index,set<Plane*> planes):index(index),planes(planes){}

	int index;
	set<Plane*> planes;
};
