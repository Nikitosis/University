#pragma once
struct Color {
	Color(int r=255, int g=255, int b=255):red(r),green(g),blue(b){}
	Color(const Color& newColor) //copy constructor
	{
		red = newColor.red;
		green = newColor.green;
		blue = newColor.blue;
	}

	Color& operator=(const Color& newColor)
	{
		red = newColor.red;
		green = newColor.green;
		blue = newColor.blue;
		return *this;
	}

	int red, green, blue;
};
