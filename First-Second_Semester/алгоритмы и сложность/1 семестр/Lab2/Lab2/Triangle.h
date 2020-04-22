#pragma once
#include <GL/glut.h>
#include <cmath>

#include "Color.h"
#include "Point.h"
class Triangle
{
public:
	Triangle(Point firstPoint,Point secondPoint,Point thirdPoint,Color color=Color(255,255,255));
	void draw();
	void setRotationAngle(float angle);
	float getRotationAngle() const;

	void setScale(float scale);
	float getScale() const;
	~Triangle();
private:
	Point firstPoint;
	Point secondPoint;
	Point thirdPoint;
	Color color;

	float rotationAngle;
	float scale;
};

