#pragma once
#include "Color.h"
#include <GL/glut.h>
class Point
{
public:
	Point(float x = 0, float y = 0, Color color= Color(255, 255, 255));
	Point(const Point& point); //copy constructor
	void draw();

	float getX() const;
	float getY() const;
	Color getColor() const;
	~Point();
private:
	float x, y;
	Color color;
};

