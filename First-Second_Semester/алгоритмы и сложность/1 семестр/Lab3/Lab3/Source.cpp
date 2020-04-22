#include <GL/freeglut.h>

#include <vector>
#include <iostream>
#include <algorithm>

#include "Color.h"
#include "Point.h"



const int WindowWidth = 680;
const int WindowHeight = 460;
const int Far = 10;
const int gridCellsDefaultAmount = 20;
float scale = 1;

std::vector<Point> points;

void drawGrid()
{
	Color axisColor(63, 145, 84);
	glColor3ub(axisColor.red, axisColor.green, axisColor.blue);

	glLineWidth(3);
	glBegin(GL_LINES);
	glVertex2f(0, WindowHeight); //draw x axis
	glVertex2f(0, -WindowHeight);

	glVertex2f(WindowWidth, 0); //draw y axis
	glVertex2f(-WindowWidth, 0);
	glEnd();

	Color gridColor(255, 255, 255);
	glColor3ub(gridColor.red, gridColor.green, gridColor.blue);
	glLineWidth(1);
	glBegin(GL_LINES);

	float cellWidth = (float)WindowWidth / (float)(gridCellsDefaultAmount*scale);
	float cellHeight = (float)WindowHeight / (float)(gridCellsDefaultAmount*scale);
	
	for (float x = cellWidth; x <= WindowWidth/2; x += cellWidth)
	{
		glVertex2f(x, WindowHeight);
		glVertex2f(x, -WindowHeight);

		glVertex2f(-x, WindowHeight);
		glVertex2f(-x, -WindowHeight);
	}
	for (float y =cellHeight; y <= WindowHeight / 2; y += cellHeight)
	{
		glVertex2f(WindowWidth, y);
		glVertex2f(-WindowWidth, y);

		glVertex2f(WindowWidth, -y);
		glVertex2f(-WindowWidth, -y);
	}

	glEnd();
}

void drawPolinom()
{
	Color funcColor = Color(42, 132, 143);
	glColor3ub(funcColor.red, funcColor.green, funcColor.blue);
	glLineWidth(6);

	glBegin(GL_LINE_STRIP);
	int n = points.size();
	float minX=1000000;
	float maxX = -1000000;

	for (int i = 0; i < points.size(); i++)
	{
		minX = std::min(minX, points[i].x);
		maxX = std::max(maxX, points[i].x);
	}
	for (float x = minX-100; x <= maxX+100; x += 1)
	{
		float y = 0;
		for (int k = 0; k < n; k++)
		{
			float temp = 1;
			for (int iter = 0; iter < n; iter++)
			{
				if (iter == k)
					continue;
				temp *= (x - points[iter].x) / (points[k].x - points[iter].x);
			}
			y += points[k].y*temp;
		}
		//std::cout << "x=" << x << "    " << "y=" << y << std::endl;
		glVertex2f(x * 1 / scale , y*1/scale);
	}
	glEnd();
}

void drawPoints()
{
	Color pointColor(140, 46, 48);
	glColor3ub(pointColor.red, pointColor.green, pointColor.blue);
	glPointSize(10);
	glBegin(GL_POINTS);

	for (int i = 0; i < points.size(); i++)
	{
		float x = points[i].x * 1 / scale;
		float y = points[i].y * 1 / scale;

		glVertex2f(x, y);
	}
	

	glEnd();
}

void display()
{
	glClear(GL_COLOR_BUFFER_BIT);

	drawGrid();
	if (points.size() >= 2)
		drawPolinom();
	drawPoints();

	glFlush();
}

void onMouseClicked(int button, int state, int x, int y)
{
	if (button == GLUT_LEFT_BUTTON && state == GLUT_DOWN)
	{
		float clickX = (x - WindowWidth / 2)*scale;
		float clickY = (WindowHeight / 2 - y)*scale;
		points.push_back(Point(clickX, clickY));
		std::cout << "Added point:"<<points.back().x<<" "<<points.back().y<<std::endl;
	}
	glutPostRedisplay();

}

void onKeyboardPressed(int key, int x, int y)
{
	if (key == GLUT_KEY_UP)
	{
		scale += 0.05;
		std::cout << "Up arrow pressed" << std::endl;
	}
	if (key == GLUT_KEY_DOWN)
	{
		scale -= 0.05;

		if (scale < 0.01)
			scale = 0.01;

		std::cout << "Down arrow pressed" << std::endl;
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
	glOrtho(-WindowWidth / 2, WindowWidth / 2, -WindowHeight / 2, WindowHeight / 2, -Far / 2, Far / 2);

	//glEnable(GL_BLEND);
	//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	glutDisplayFunc(display);
	glutMouseFunc(onMouseClicked);
	glutSpecialFunc(onKeyboardPressed);


	glutMainLoop();
	return 0;
}