import matplotlib.pyplot as plt

from matplotlib import patches


class Point:
    def __init__(self, x, y) -> None:
        self.x = float(x)
        self.y = float(y)
        self.n = 0

    def __gt__(self, o: object) -> bool:
        if self.x > o.x:
            return True
        elif self.x == o.x and self.y > o.y:
            return True
        else:
            return False

    def __eq__(self, o: object) -> bool:
        if self.x == o.x and self.y == o.y:
            return True
        return False

    def __repr__(self) -> str:
        return str(self.n)

    def set_n(self, n):
        self.n = n


class Rectangle:
    def __init__(self, point1, point2) -> None:
        self.point1 = point1
        self.point2 = point2


class Line:
    def __init__(self, x, y) -> None:
        self.x = x
        self.y = y


class Node:
    def __init__(self, point, rectangle, points, line) -> None:
        self.point = point
        self.rectangle = rectangle
        self.points = points
        self.line = line
        self.left = None
        self.right = None

    def __repr__(self) -> str:
        return str(self.point.n)

    def set_left(self, left):
        self.left = left

    def set_right(self, right):
        self.right = right


def read_points():
    f = open("points.txt")
    points = []
    for line in f.readlines():
        coordinates = [float(i) for i in line.split(" ")]
        points.append(Point(coordinates[0], coordinates[1]))
    f.close()

    points.sort()

    number = 0
    for p in points:
        p.set_n(number)
        number += 1

    return points


def get_line_segment(line, rectangle):
    if line.x is None:
        return (rectangle.point1.x, rectangle.point2.x), (line.y, line.y)
    else:
        return (line.x, line.x), (rectangle.point1.y, rectangle.point2.y)


def draw_tree(node):
    if node is None:
        return
    line = get_line_segment(node.line, node.rectangle)
    plt.plot(line[0], line[1], color='black')
    draw_tree(node.left)
    draw_tree(node.right)


def draw(points, region_point1, region_point2, tree, points_in_region):
    fig, ax = plt.subplots()
    draw_tree(tree)
    for p in points:
        plt.plot(p.x, p.y, 'bo')
        plt.text(p.x - 0.3, p.y - 0.4, p.n)

    for p in points_in_region:
        plt.plot(p.x, p.y, 'ro')

    width = region_point2.x - region_point1.x
    height = region_point2.y - region_point1.y
    rect = patches.Rectangle((region_point1.x, region_point1.y), width, height, linewidth=2, edgecolor='r',
                             facecolor='none')
    ax.add_patch(rect)

    ax.autoscale()

    plt.show()


def divide_rectangle_left(rectangle, x) -> Rectangle:
    p1 = rectangle.point1
    p2 = Point(x, rectangle.point2.y)
    return Rectangle(p1, p2)


def divide_rectangle_right(rectangle, x) -> Rectangle:
    p1 = Point(x, rectangle.point1.y)
    p2 = rectangle.point2
    return Rectangle(p1, p2)


def divide_rectangle_lower(rectangle, y) -> Rectangle:
    p1 = rectangle.point1
    p2 = Point(rectangle.point2.x, y)
    return Rectangle(p1, p2)


def divide_rectangle_higher(rectangle, y) -> Rectangle:
    p1 = Point(rectangle.point1.x, y)
    p2 = rectangle.point2
    return Rectangle(p1, p2)


def get_rectangle_from_points(points):
    p1 = Point(0, 0)
    max_x = 0
    max_y = 0
    for p in points:
        if p.x > max_x:
            max_x = p.x
        if p.y > max_y:
            max_y = p.y
    p2 = Point(max_x + 1, max_y + 1)
    return Rectangle(p1, p2)


def get_node_points_vertical(point, points):
    node_points_left = []
    node_points_right = []
    for p in points:
        if p.x < point.x:
            node_points_left.append(p)
        if p.x > point.x:
            node_points_right.append(p)
    return node_points_left, node_points_right


def get_node_points_horizontal(point, points):
    node_points_left = []
    node_points_right = []
    for p in points:
        if p.y < point.y:
            node_points_left.append(p)
        if p.y > point.y:
            node_points_right.append(p)
    return node_points_left, node_points_right


def get_middle_point_y(points):
    ret = points[:]
    ret.sort(key=lambda p: p.y)
    middle = int(len(points) / 2)
    return ret[middle]


def add_children(node, vertical):
    if len(node.points) <= 1:
        return
    if vertical:
        node_left_points, node_right_points = get_node_points_vertical(node.point, node.points)
        node_left_point = get_middle_point_y(node_left_points)
        node_right_point = get_middle_point_y(node_right_points)
        node_left = Node(node_left_point, divide_rectangle_left(node.rectangle, node.line.x), node_left_points,
                         Line(None, node_left_point.y))
        node.set_left(node_left)
        node_right = Node(node_right_point, divide_rectangle_right(node.rectangle, node.line.x), node_right_points,
                          Line(None, node_right_point.y))
        node.set_right(node_right)
        vertical = False
    else:
        node_left_points, node_right_points = get_node_points_horizontal(node.point, node.points)
        middle_left = int(len(node_left_points) / 2)
        middle_right = int(len(node_right_points) / 2)
        node_left_point = node_left_points[middle_left]
        node_right_point = node_right_points[middle_right]
        node_left = Node(node_left_point, divide_rectangle_lower(node.rectangle, node.line.y), node_left_points,
                         Line(node_left_point.x, None))
        node.set_left(node_left)
        node_right = Node(node_right_point, divide_rectangle_higher(node.rectangle, node.line.y), node_right_points,
                          Line(node_right_point.x, None))
        node.set_right(node_right)
        vertical = True
    add_children(node_left, vertical)
    add_children(node_right, vertical)


def build_tree(points):
    i = 0
    middle = int(len(points) / 2)
    vertical = True
    root = Node(points[middle], get_rectangle_from_points(points), points, Line(points[middle].x, None))
    add_children(root, vertical)
    return root


def rectangle_crosses_region(rectangle, region):
    return not (
            rectangle.point2.x < region.point1.x or rectangle.point1.x > region.point2.x
            or rectangle.point2.y < region.point1.y or rectangle.point1.y > region.point2.y)


def point_is_in_rectangle(point, rectangle):
    return rectangle.point1.x <= point.x <= rectangle.point2.x and rectangle.point1.y <= point.y <= rectangle.point2.y


points_in_region = []


def search(node, region):
    if rectangle_crosses_region(node.rectangle, region):
        if point_is_in_rectangle(node.point, region):
            points_in_region.append(node.point)
        if node.left is not None:
            search(node.left, region)
        if node.right is not None:
            search(node.right, region)


point1 = Point(6.1, 1)
point2 = Point(10, 11)

points = read_points()
tree = build_tree(points)
search(tree, Rectangle(point1, point2))
draw(points, point1, point2, tree, points_in_region)
