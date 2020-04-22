#include "GammaManager.h"

vector<Edge*> GammaManager::markedEdges = {};
vector<Segment*> GammaManager::segments = {};
vector<Plane*> GammaManager::planes = {};
vector<vector<Edge*>> GammaManager::newGraph = {};

bool GammaManager::isGraphPlanar(vector<vector<int>>& graph)
{
	vector<bool> used(graph.size(), false);
	for (int i = 0; i < graph.size(); i++) {
		if (!used[i]) {
			if (!isSubgraphPlanar(i, graph))
				return false;
		}
	}
	return true;
}

bool GammaManager::isSubgraphPlanar(int v,vector<vector<int>>& graph)
{
	newGraph.resize(graph.size());

	vector<int> markedV = findCycle(v, graph);

	if (markedV.empty())  //если нет циклов
		return true;

	Plane *inside = new Plane(0, {});
	Plane *outside = new Plane(1, {});
	planes.push_back(inside);
	planes.push_back(outside);

	for (int i = 0; i < markedV.size(); i++) {
		int curV = markedV[i];
		int nextV = markedV[(i + 1) % markedV.size()];

		Edge *curEdge = new Edge(curV, {});
		Edge *nextEdge = new Edge(nextV, {});

		inside->addEdge(curEdge);
		outside->addEdge(curEdge);

		newGraph[curV].push_back(nextEdge);
		newGraph[nextV].push_back(curEdge);

		markedEdges.push_back(curEdge);
	}

	while (true) {
		segments.clear();

		addFirstTypeSegments(graph);
		addSecondTypeSegments(graph);

		if (segments.size() == 0)
			return true;

		Segment *segment = getMinimalSegment();
		if (segment->getJointPlanes().size() == 0)
			return false;

		if (segment->edges.size() == 2) {
			drawFirstTypeSegment(segment);
		}
		else {
			drawSecondTypeSegment(segment);
		}
	}
}

void GammaManager::addFirstTypeSegments(vector<vector<int>>& graph)
{
	for (Edge *curEdge : markedEdges) {
		for (int j = 0; j < graph[curEdge->index].size(); j++)
		{
			Edge *toEdge = findEdgeByIndexInArray(markedEdges, graph[curEdge->index][j]);
			if (toEdge != nullptr && !areEdgesConnectedInNewGraph(curEdge, toEdge)) {  //если второй конец ребра тоже контактная вершина и ребра нет в новом графе
				Segment *segment = new Segment({ curEdge,toEdge });
				segments.push_back(segment);
			}
		}
	}
}

void GammaManager::addSecondTypeSegments(vector<vector<int>>& graph)
{
	for (Edge *edge : markedEdges) {
		vector<bool> used(graph.size(), false);
		stack<int> mainStack;
		segmentDFS(edge->index, edge->index, -1, graph, mainStack, used);
	}
}

void GammaManager::drawSecondTypeSegment(Segment* segment)
{
	set<Plane*> jointPlanes = segment->getJointPlanes();
	if (jointPlanes.size() == 0)
		return;

	Plane *chosenPlane = *jointPlanes.begin(); //берем какую-то первую попавшуюся грань
	Edge *startEdge = segment->getStartEdge();
	Edge *endEdge = segment->getEndEdge();

	vector<Edge*> newPlaneEdges = getEdgesInPlaneBetweenTwo(chosenPlane, startEdge, endEdge);
	deleteEdgesInPlaneBetweenTwo(chosenPlane, startEdge, endEdge);
	if (startEdge->index == endEdge->index) {  //если начальная вершина это конечная вершина сегмента
		newPlaneEdges.push_back(startEdge);
	}
	else {  //иначе добавляем в начало массива первую startEdge, а в конец массива endEdge вершину
		newPlaneEdges.push_back(endEdge);
		vector<Edge*> newDeletedEdges;
		newDeletedEdges.push_back(startEdge);
		for (Edge *edge : newPlaneEdges) {
			newDeletedEdges.push_back(edge);
		}

		newPlaneEdges = newDeletedEdges;
	}

	for (int i = segment->edges.size() - 2; i >= 1; i--)
		newPlaneEdges.push_back(segment->edges[i]);

	/*if (startEdge->index == endEdge->index) {
		newPlaneEdges.push_back(startEdge);
	}*/

	Plane *newPlane = new Plane(planes.size(), newPlaneEdges);
	planes.push_back(newPlane);

	addEdgesInPlaneBetweenTwo(chosenPlane, segment);
	

	for (Edge *edge : segment->edges) {
		if (!isEdgeInMarkedEdges(edge->index)) {
			markedEdges.push_back(edge);
		}
	}

	for (int i = 0; i < segment->edges.size()-1; i++) {
		Edge *firstEdge = segment->edges[i];
		Edge *secondEdge = segment->edges[i + 1];
		if (!areEdgesConnectedInNewGraph(firstEdge->index, secondEdge->index)) {
			newGraph[firstEdge->index].push_back(secondEdge);
			newGraph[secondEdge->index].push_back(firstEdge);
		}
	}

}

void GammaManager::drawFirstTypeSegment(Segment * segment)
{
	set<Plane*> jointPlanes = segment->getJointPlanes();
	if (jointPlanes.size() == 0)
		return;

	Plane *chosenPlane = *jointPlanes.begin(); //берем какую-то первую попавшуюся грань
	Edge *startEdge = segment->getStartEdge();
	Edge *endEdge = segment->getEndEdge();

	vector<Edge*> newPlaneEdges = getEdgesInPlaneBetweenTwo(chosenPlane, startEdge, endEdge);
	deleteEdgesInPlaneBetweenTwo(chosenPlane, startEdge, endEdge); 

	 {  //добавляем в начало массива первую startEdge, а в конец массива endEdge вершину
		newPlaneEdges.push_back(endEdge);
		vector<Edge*> newDeletedEdges;
		newDeletedEdges.push_back(startEdge);
		for (Edge *edge : newPlaneEdges) {
			newDeletedEdges.push_back(edge);
		}

		newPlaneEdges = newDeletedEdges;
	}

	 Plane *newPlane = new Plane(planes.size(), newPlaneEdges);
	 planes.push_back(newPlane);
	
	 if (!areEdgesConnectedInNewGraph(startEdge->index, endEdge->index)) {
		 newGraph[startEdge->index].push_back(endEdge);
		 newGraph[endEdge->index].push_back(startEdge);
	 }

}

Edge* GammaManager::findEdgeByIndexInArray(vector<Edge*>& edges,int index)
{
	for (Edge *edge : edges) {
		if (edge->index == index) {
			return edge;
		}
	}
	return nullptr;
}

bool GammaManager::areEdgesConnectedInNewGraph(Edge * firstEdge, Edge * secondEdge)
{
	for (Edge *edge : newGraph[firstEdge->index]) {
		if (edge->index == secondEdge->index)
			return true;
	}
	return false;
}

bool GammaManager::areEdgesConnectedInNewGraph(int firstEdgeIndex, int secondEdgeIndex)
{
	for (Edge *edge : newGraph[firstEdgeIndex]) {
		if (edge->index == secondEdgeIndex)
			return true;
	}
	return false;
}

bool GammaManager::isEdgeInMarkedEdges(int edgeIndex)
{
	for (Edge *edge : markedEdges) {
		if (edge->index == edgeIndex)
			return true;
	}
	return false;
}

Edge * GammaManager::getEdgeFromMarkedEdges(int edgeIndex)
{
	for (Edge *curEdge : markedEdges) {
		if (curEdge->index == edgeIndex) {
			return curEdge;
		}
	}
	return nullptr;
}

Segment * GammaManager::getMinimalSegment()
{
	if (segments.empty())
		return nullptr;

	int minimalIndex = 0;
	int minimalValue = 100000;
	for (int i = 0; i < segments.size(); i++) {
		set<Plane*> jointPlanes = segments[i]->getJointPlanes();
		if (jointPlanes.size()<minimalValue) {
			minimalValue = jointPlanes.size();
			minimalIndex = i;
		}
	}
	return segments[minimalIndex];
}

vector<Edge*> GammaManager::getEdgesInPlaneBetweenTwo(Plane *plane, Edge * firstEdge, Edge * secondEdge)
{
	vector<Edge*> ansver;

	int startIndex, endIndex;

	for (int i = 0; i < plane->edges.size(); i++) {
		if (plane->edges[i]->index == firstEdge->index) {
			startIndex = i;
		}
		if (plane->edges[i]->index == secondEdge->index) {
			endIndex = i;
		}
	}

	if (startIndex > endIndex)
		swap(startIndex, endIndex);

	for (int i = startIndex + 1; i < endIndex; i++) {
		ansver.push_back(plane->edges[i]);
	}

	/*if (startIndex > endIndex) {
		for (int i = startIndex - 1; i > endIndex; i--) {
			ansver.push_back(plane->edges[i]);
		}
	}
	else {
		for (int i = startIndex+1; i < endIndex; i++) {
			ansver.push_back(plane->edges[i]);
		}
	}*/
	return ansver;
}

void GammaManager::deleteEdgesInPlaneBetweenTwo(Plane *plane,Edge * firstEdge, Edge * secondEdge)
{

	int startIndex, endIndex;

	for (int i = 0; i < plane->edges.size(); i++) {
		if (plane->edges[i]->index == firstEdge->index) {
			startIndex = i;
		}
		if (plane->edges[i]->index == secondEdge->index) {
			endIndex = i;
		}
	}

	plane->deleteEdgesBetween(startIndex, endIndex);
}

void GammaManager::addEdgesInPlaneBetweenTwo(Plane * plane, Segment * segment)
{
	Edge *startEdge = segment->getStartEdge();
	Edge *endEdge = segment->getEndEdge();

	for (int i = 0; i < plane->edges.size(); i++) {
		if (plane->edges[i]->index == startEdge->index) {
			vector<Edge*> segmentEdges = segment->edges;
			for (int j = 1; j < segmentEdges.size() - 1; j++) {
				plane->addEdge(segmentEdges[j],i+j);
			}
			return;
		}
		if (plane->edges[i]->index == endEdge->index) {
			vector<Edge*> segmentEdges = segment->edges;
			for (int j = segmentEdges.size() - 2; j >=1; j--) {
				plane->addEdge(segmentEdges[j], i + j);
			}
			return;
		}
	}
}

void GammaManager::addSegmentFromStack(stack<int> mainStack)
{
	vector<Edge*> edges;
	while (!mainStack.empty()) {
		int edgeIndex = mainStack.top();
		Edge *curEdge = isEdgeInMarkedEdges(edgeIndex) ? getEdgeFromMarkedEdges(edgeIndex) : new Edge(edgeIndex, {});

		edges.push_back(curEdge);
		mainStack.pop();
	}
	Segment *curSegment = new Segment(edges);
	segments.push_back(curSegment);
}

vector<int> GammaManager::findCycle(int v, vector<vector<int>>& graph)
{
	int n = graph.size();
	stack<int> mainStack;
	vector<int> colors(n, 0);
	bool isCycle = false;
	cycleDFS(v, -1, graph, mainStack, colors, isCycle);

	if (!isCycle)
		return {};

	vector<int> result;
	int repeatedV = mainStack.top();
	do
	{
		result.push_back(mainStack.top());
		mainStack.pop();
	} while (mainStack.top() != repeatedV);

	return result;
}

void GammaManager::cycleDFS(int v, int p, vector<vector<int>>& graph, stack<int>& mainStack, vector<int>& colors,bool &isCycle)  //для нахождения цикла
{
	mainStack.push(v);
	colors[v] = 1;
	for (int i = 0; i < graph[v].size(); i++) {
		if (isCycle)
			return;

		int to = graph[v][i];

		if (to == p || colors[to]==2)
			continue;
		else
			if (colors[to] == 0)
				cycleDFS(to, v, graph, mainStack, colors, isCycle);
		else
			if (colors[to] == 1)
			{
				mainStack.push(to);
				isCycle = true;
				break;
			}
	}

	if (isCycle)
		return;
	colors[v] = 2;
	mainStack.pop();
}

void GammaManager::segmentDFS(int startedV,int v, int p, vector<vector<int>>& graph, stack<int>& mainStack,vector<bool> &used)
{
	mainStack.push(v);
	used[v] = true;

	if (isEdgeInMarkedEdges(v) && p!=-1) {   //если мы пришли в контактную вершину
		addSegmentFromStack(mainStack);
		mainStack.pop();
		return;
	}

	for (int i = 0; i < graph[v].size(); i++) {
		int to = graph[v][i];

		if (to == startedV && p != startedV) { //если пришли в начальную контактную вершину
			mainStack.push(to);
			addSegmentFromStack(mainStack);
			mainStack.pop();
			return;
		}

		if (!used[to]) {
			if (!areEdgesConnectedInNewGraph(v, to)) {  //если нет ребра между этими вершинами в новом графе
				if(!(isEdgeInMarkedEdges(to) && isEdgeInMarkedEdges(v))) //и это ребро не связывает две контактные вершины
					segmentDFS(startedV,to, v, graph, mainStack, used);
			}
		}
	}

	mainStack.pop();
	used[v] = false;

}

void GammaManager::componentDFS(int v, vector<vector<int>>& graph, vector<bool>& used)
{
	used[v] = true;
	for (int i = 0; i < graph[v].size(); i++) {
		int to = graph[v][i];
		if (!used[to])
			componentDFS(to, graph, used);
	}
		
}

