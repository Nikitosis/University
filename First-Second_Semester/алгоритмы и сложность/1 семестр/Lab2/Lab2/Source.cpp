#include <iostream>

#include <GL/glut.h>
#include <cmath>
#include <vector>
#include "Triangle.h"
#include "Point.h"

using namespace std;

const int WindowWidth = 640;
const int WindowHeight = 480;
const int centerX = WindowWidth / 2;
const int centerY = WindowHeight / 2;
const int Far = 10;

vector<Point> points;
Triangle *triangle=nullptr;


void display()
{
	glClear(GL_COLOR_BUFFER_BIT);

	if (triangle != nullptr)
		triangle->draw();
	glPointSize(10);

	for (int i = 0; i < points.size(); i++)
		points[i].draw();

	glFlush();
	cout << "display()"<<endl;
}

void createNewTriangle()
{
	if (triangle != nullptr)  //clear the memory
		delete triangle;
	if (points.size() >= 3)
	{
		triangle = new Triangle(points[0], points[1], points[2]);
		points.erase(points.begin(), points.begin() + 3);
	}
}

void rotateTriangle(float angle)
{
	if (triangle != nullptr)
	{
		triangle->setRotationAngle(triangle->getRotationAngle() + angle);
	}
}

void changeScale(float scale)
{
	if (triangle != nullptr)
	{
		triangle->setScale(triangle->getScale() + scale);

		if (triangle->getScale() < 0)
			triangle->setScale(0);
		cout << "Scale: " << triangle->getScale() << endl;
	}
}

void onMouseClicked(int button, int state, int x, int y)
{
	if (button == GLUT_LEFT_BUTTON && state == GLUT_DOWN)
	{
		points.push_back(Point(x-centerX, y-centerY));
		if (points.size() >= 3)
			createNewTriangle();
		cout << "Glut mouse down"<<" "<<x-centerX<<" "<<y-centerY<<endl;
	}
	glutPostRedisplay();
}

void onKeyboardPressed(int key, int x, int y)
{
	if (key == GLUT_KEY_LEFT)
	{
		rotateTriangle(-2);
		cout << "Left arrow pressed"<<endl;
	}
	if (key == GLUT_KEY_RIGHT)
	{
		rotateTriangle(2);
		cout << "Right arrow pressed"<<endl;
	}
	if (key == GLUT_KEY_UP)
	{
		changeScale(0.05);
		cout << "Up arrow pressed" << endl;
	}
	if (key == GLUT_KEY_DOWN)
	{
		changeScale(-0.05);
		cout << "Down arrow pressed" << endl;
	}
	glutPostRedisplay();
}

int main(int argc, char **argv)
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(WindowWidth, WindowHeight);
	glutInitWindowPosition(100, 100);
	glutCreateWindow("Triangle lab");
	glClearColor(0, 0, 0, 1.0);
	glOrtho(-WindowWidth / 2, WindowWidth / 2, WindowHeight / 2, -WindowHeight / 2, -Far / 2, Far / 2);
	glutDisplayFunc(display);
	glutMouseFunc(onMouseClicked);
	glutSpecialFunc(onKeyboardPressed);


	glutMainLoop();
	
	if (triangle != nullptr) //clear the memory
		delete triangle;
	return 0;
}