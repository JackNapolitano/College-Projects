#include <stdio.h>

int main(int argc, char* argv[])
{
	char curr_string[4];
	char x; 
	int size;
	FILE *file;
	int num;

	if (argc !=2)
	{
		printf("You have entered the wrong number of arguements. Please only enter one file after the call to mystrings.");
		return 0;
	}
	file = fopen (argv[1], "rb");
	while(!feof(file))
	{
		size =ftell(file);
		num = fread(&curr_string, 1, 4, file);
		if(num !=4)
		{
			return 0;
		}

		if((curr_string[0] >= 32) && (curr_string[0]<=126)  && (curr_string[1] >= 32) && (curr_string[1]<=126)  && (curr_string[2] >= 32) && (curr_string[2]<=126) && (curr_string[3] >= 32) && (curr_string[3] <= 126))
		{
			fseek(file, size, SEEK_SET);
			while(num>0 && !feof(file))
			{
				num = fread(&x, 1, 1, file);
				if(x >=32 && x <= 126)
				{
					printf("%c", x);
				}
				else
				{
					printf("\n");
					break;
				}
			}
		}
		else
		{
			fseek(file, ftell(file)-3, SEEK_SET);
		}
	}
	return 0;

}
