#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

void deleteSymb(char *buf, int position)
{
	int j = position; //delete symbol
	while (buf[j] != '\0')
	{
		buf[j] = buf[j + 1];
		j++;
	}
}

int main()
{
	FILE *fileIn;
	FILE *fileOut;
	const int BUFSIZE = 50;
	char buf[BUFSIZE];
	const int LINELENGTH = 50;

	fileIn = fopen("input.txt", "r");
	fileOut = fopen("output.txt", "w");

	bool wasNextLine = false;
	bool wasNextParagraph = true;
	int curLineLength = 0;
	while (fgets(buf, sizeof(buf), fileIn))
	{
		int i = 0;
		while(buf[i]!='\0')
		{
			if (curLineLength > LINELENGTH && buf[i]==' ') {
				fprintf(fileOut, "%s", "\n");
				curLineLength = 0;
				wasNextLine = true;
				continue;
			}

			if (wasNextLine && buf[i] == ' ')
			{
				//deleteSymb(buf, i);
				i++;
				continue;
			}
			if (buf[i] == ' ' && buf[i + 1] == ' ')
			{
				//deleteSymb(buf, i);
				i++;
				continue;
			}
			if (buf[i] == '\n')
			{
				wasNextLine = true;
				wasNextParagraph = true;
				curLineLength = 0;
				fprintf(fileOut, "%s", "\n");
				i++;
				continue;
			}
			curLineLength++;
			if (wasNextParagraph) {
				wasNextParagraph = false;
				fprintf(fileOut, "%s", "\t");
			}
			wasNextLine = false;
			fprintf(fileOut, "%c", buf[i]);
			i++;
		}
		//fprintf(fileOut,"%s", buf);
	}

	fclose(fileIn);
	fclose(fileOut);
	return 0;
}