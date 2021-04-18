import math

import numpy
from PIL import Image


def median_filter(data, filter_size):
    temp = []
    half_size = math.floor(filter_size / 2)
    print(half_size)
    data_final = numpy.zeros((len(data),len(data[0])))
    for i in range(len(data)):
        for j in range(len(data[0])):
            for z in range(filter_size):
                if 0 <= i + z - half_size <= len(data) - 1:
                    for l in range(filter_size):
                        if 0 <= j + l - half_size <= len(data[0]) - 1:
                            temp.append(data[i + z - half_size][j + l - half_size])
            temp.sort()
            data_final[i][j] = temp[math.floor(len(temp) / 2)]
            temp = []
    return data_final


def main():
    img = Image.open("saturn3.GIF").convert("L")
    arr = numpy.array(img)
    removed_noise = median_filter(arr, 5)
    img = Image.fromarray(removed_noise)
    img.save("Saturn3After.gif")


main()