#include "Triangle.h"

Triangle::Triangle(Point firstPoint, Point secondPoint, Point thirdPoint,Color color):
	firstPoint(firstPoint),
	secondPoint(secondPoint),
	thirdPoint(thirdPoint),
	color(color)
{
	rotationAngle = 0;
	scale = 1;
}

void Triangle::draw()
{
	glBegin(GL_TRIANGLES);
	glColor3ub(color.red, color.green, color.blue);
	////////////////////////////// find mass center
	float centerX = (firstPoint.getX() + secondPoint.getX() + thirdPoint.getX()) / 3;
	float centerY = (firstPoint.getY() + secondPoint.getY() + thirdPoint.getY()) / 3;
	//////////////////////////////

	//////////////////////////////        find rotation coords
	float firstPointX = centerX + (firstPoint.getX() - centerX)*cos(rotationAngle / 180*3.14)-
		(firstPoint.getY()-centerY)*sin(rotationAngle/ 180 * 3.14);
	float firstPointY = centerY + (firstPoint.getY() - centerY)*cos(rotationAngle / 180 * 3.14)+
		(firstPoint.getX()-centerX)*sin(rotationAngle/ 180 * 3.14);

	float secondPointX = centerX + (secondPoint.getX() - centerX)*cos(rotationAngle / 180 * 3.14) -
		(secondPoint.getY() - centerY)*sin(rotationAngle / 180 * 3.14);
	float secondPointY = centerY + (secondPoint.getY() - centerY)*cos(rotationAngle / 180 * 3.14) +
		(secondPoint.getX() - centerX)*sin(rotationAngle / 180 * 3.14);

	float thirdPointX = centerX + (thirdPoint.getX() - centerX)*cos(rotationAngle / 180 * 3.14) -
		(thirdPoint.getY() - centerY)*sin(rotationAngle / 180 * 3.14);
	float thirdPointY = centerY + (thirdPoint.getY() - centerY)*cos(rotationAngle / 180 * 3.14) +
		(thirdPoint.getX() - centerX)*sin(rotationAngle / 180 * 3.14);
	//////////////////////////////

	////////////////////////////// find scale coords
	firstPointX = centerX + (firstPointX - centerX)*scale;
	firstPointY = centerY + (firstPointY - centerY)*scale;

	secondPointX = centerX + (secondPointX - centerX)*scale;
	secondPointY = centerY + (secondPointY - centerY)*scale;

	thirdPointX = centerX + (thirdPointX - centerX)*scale;
	thirdPointY = centerY + (thirdPointY - centerY)*scale;
	//////////////////////////////

	//////////////////////////////  paint
	glVertex2f(firstPointX, firstPointY);
	glVertex2f(secondPointX, secondPointY);
	glVertex2f(thirdPointX, thirdPointY);
	//////////////////////////////

	glEnd();
}

void Triangle::setRotationAngle(float angle)
{
	rotationAngle = angle;
}

float Triangle::getRotationAngle() const
{
	return rotationAngle;
}

void Triangle::setScale(float scale)
{
	this->scale = scale;
}

float Triangle::getScale() const
{
	return scale;
}

Triangle::~Triangle()
{
}
