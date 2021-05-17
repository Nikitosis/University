import matplotlib.pyplot as plt
from matplotlib.collections import LineCollection


TURN_LEFT, TURN_RIGHT, TURN_NONE = (1, -1, 0)


def turn(p, q, r):
    turn_value = (q[0] - p[0])*(r[1] - p[1]) - (r[0] - p[0])*(q[1] - p[1])
    if turn_value > 0:
        return TURN_LEFT
    elif turn_value < 0:
        return TURN_RIGHT
    return TURN_NONE


def distance(p, q):
    dx, dy = q[0] - p[0], q[1] - p[1]
    return dx * dx + dy * dy


def next_point(points, p):
    next_p = p
    for curr_point in points:
        t = turn(p, next_p, curr_point)
        if t == TURN_RIGHT or t == TURN_NONE and distance(p, curr_point) > distance(p, next_p):
            next_p = curr_point
    return next_p


def jarvis(points):
    hull = [min(points)]
    for p in hull:
        next_p = next_point(points, p)
        if next_p != hull[0]:
            hull.append(next_p)
    return hull


def merge(points_1, points_2):
    p = points_1 + points_2
    p.sort()
    return jarvis(points_1 + points_2)


def divide_and_conquer(points):
    if len(points) > 5:
        s1 = []
        for i in range(0, len(points) // 2):
            s1.append(points[i])

        s2 = []
        for i in range(len(points) // 2, len(points)):
            s2.append(points[i])

        return merge(s1, s2)
    else:
        return jarvis(points)


def read_points(file):
    points = []
    input_data = open(file).read().split()

    i = 0
    while i < len(input_data):
        points.append([float(input_data[i]), float(input_data[i + 1])])
        i += 2

    return points


def draw(points, result):
    fig, ax = plt.subplots()
    ax.set_xlim([-10, 15])
    ax.set_ylim([-10, 15])

    for point in points:
        plt.scatter(point[0], point[1], s=10, edgecolors='g', facecolor='g')

    lines = [[result[len(result) - 1], result[0]]]
    for i in range(1, len(result)):
        lines.append([result[i - 1], result[i]])
    lc = LineCollection(lines, linewidths=1)
    ax.add_collection(lc)

    plt.show()


points = read_points("points.txt")
result = divide_and_conquer(points)

draw(points, result)