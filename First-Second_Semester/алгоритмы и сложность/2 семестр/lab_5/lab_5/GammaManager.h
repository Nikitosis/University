#pragma once
#include <vector>
#include <stack>
#include <set>

#include "Edge.h"
#include "Segment.h"
#include "Plane.h"

using namespace std;
class GammaManager
{
public:
	static bool isGraphPlanar(vector<vector<int>> &graph);
	static vector<int> findCycle(int v, vector<vector<int>> &graph);

private:
	static void init();
	static bool isSubgraphPlanar(int v, vector<vector<int>> &graph);


	static void cycleDFS(int v, int p, vector<vector<int>> &graph, stack<int> &mainStack,vector<int> &colors,bool &isOver);
	static void segmentDFS(int startedV, int v, int p, vector<vector<int>>& graph, stack<int>& mainStack, vector<bool> &used);
	static void componentDFS(int v, vector<vector<int>>&graph, vector<bool>&used);

	static void addFirstTypeSegments(vector<vector<int>>& graph);
	static void addSecondTypeSegments(vector<vector<int>>& graph);
	static void drawSecondTypeSegment(Segment* segment);
	static void drawFirstTypeSegment(Segment* segment);


	static Edge* findEdgeByIndexInArray(vector<Edge*> &edges,int index);
	static bool areEdgesConnectedInNewGraph(Edge *firstEdge, Edge*secondEdge);
	static bool areEdgesConnectedInNewGraph(int firstEdgeIndex, int secondEdgeIndex);
	static bool isEdgeInMarkedEdges(int edgeIndex);
	static Edge *getEdgeFromMarkedEdges(int edgeIndex);

	static Segment* getMinimalSegment();

	static vector<Edge*> getEdgesInPlaneBetweenTwo(Plane *plane,Edge *firstEdge, Edge* secondEdge);
	static void deleteEdgesInPlaneBetweenTwo(Plane *plane,Edge* firstEdge, Edge *secondEdge);
	static void addEdgesInPlaneBetweenTwo(Plane *plane, Segment *segment);

	static void addSegmentFromStack(stack<int> mainStack);


	static vector<Edge*> markedEdges;
	static vector<Segment*> segments;
	static vector<Plane*> planes;
	static vector<vector<Edge*>> newGraph;
};

