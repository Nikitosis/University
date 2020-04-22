#include "Point.h"

#include <iostream>

Point::Point(float x, float y, Color color):x(x),y(y),color(color)
{
}

Point::Point(const Point & point)
{
	x = point.getX();
	y = point.getY();
	color = point.getColor();
}

void Point::draw()
{
	glPointSize(10);
	glColor3ub(color.red, color.green, color.blue);

	glBegin(GL_POINTS);
	glVertex2f(x,y);
	glEnd();

	std::cout << "Draw point at" << " " << x << " " << y << std::endl;
}

float Point::getX() const
{
	return x;
}

float Point::getY() const
{
	return y;
}

Color Point::getColor() const
{
	return color;
}

Point::~Point()
{
}
