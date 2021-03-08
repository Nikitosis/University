import numpy as np
from graphics import *


def read_input():
    f = open("points.txt")
    points = []
    for line in f.readlines():
        coordinates = [float(i) for i in line.split(" ")]
        points.append([coordinates[0], coordinates[1]])
    f.close()
    return points


def getArea(p1, p2, p3):
    a = np.matrix([[p1[0], p1[1], 1], [p2[0], p2[1], 1], [p3[0], p3[1], 1]])
    det = np.linalg.det(a)
    return det * -1


def getQ(points):
    x = (points[0][0] + points[1][0] + points[2][0]) / 3.0
    y = (points[0][1] + points[1][1] + points[2][1]) / 3.0
    return x, y


def binSearch(indexes_array, angles):
    start = 0
    end = np.size(indexes_array) - 1
    while end - start > 1:
        mid = int((start + end) / 2)
        if angles[indexes_array[start]] * angles[indexes_array[mid]] < 0:
            end = mid
        else:
            start = mid
    return indexes_array[start], indexes_array[end]


def isInPoligon(points, z):
    q = getQ(points)
    angles = []
    for point in points:
        value = getArea(z, q, point)
        angles.append(value)

    smallest = 0
    for i in range(np.size(angles)):
        if angles[i] < angles[smallest]:
            smallest = i

    indexes_array = []
    i = smallest
    while i < np.size(angles):
        indexes_array.append(i)
        i = i + 1

    for i in range(np.size(angles)):
        if i < smallest:
            indexes_array.append(i)
        else:
            break

    angle = binSearch(indexes_array, angles)
    result = getArea(points[angle[0]], points[angle[1]], z)
    if result < 0:
        return True
    else:
        return False


def main():
    windows_size = 600
    win = GraphWin('Lab1', windows_size, windows_size)
    points = read_input()
    center = windows_size/2
    size = np.size(points, 0)
    for i in range(size - 1):
        line = Line(Point(points[i][0] + center, points[i][1] + center),
                    Point(points[i + 1][0] + center, points[i + 1][1] + center))
        line.setWidth(2)
        line.draw(win)
    line = Line(Point(points[0][0] + center, points[0][1] + center),
                Point(points[size - 1][0] + center, points[size - 1][1] + center))
    line.setWidth(2)
    line.draw(win)

    while True:
        point = win.getMouse()
        circle = Circle(point, 2)
        z = (point.x - center, point.y - center)
        res = isInPoligon(points, z)
        if res:
            circle.setFill('green')
            circle.setOutline('green')
        else:
            circle.setFill('red')
            circle.setOutline('red')
        circle.draw(win)


main()