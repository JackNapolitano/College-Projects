#include <stdio.h>
#include <string.h>
#include <stdlib.h>

struct s1
{
	unsigned short start;
	unsigned short app1;
	unsigned short app1_length;
	char exif_str[4];
	unsigned short nt;
	char endi[2];
	unsigned short version;
	unsigned int offset_block;
};
struct tiff
{
	unsigned short tag;
	unsigned short dtype;
	unsigned int num;
	unsigned int offset;
};



int main(int argcount, char* argv[])
{
	if (argcount != 2)
	{
		printf("You need to enter a file name to read in.");
		return -1;
	}


	struct s1 s1struct;
	struct tiff t1;
	FILE *f;
	short cnt;
	int x;
	int y;
	int z;
	int pointer;
	char exif[4];
	char endian[2];
	char print[100];


	f = fopen(argv[1], "rb");
	if(f == NULL)
	{
		printf("File does not exist.");
		return -1;
	}
	
	if(fread(&s1struct, sizeof(struct s1), 1, f)==1)
	{
		strcpy(exif, (s1struct).exif_str);
		strcpy(endian, (s1struct).endi);
		if ((s1struct).start != 0xd8ff || (s1struct).app1 != 0xe1ff || strcmp(exif, "Exif") !=0 || endian[0] != 'I')
		{
			printf("The file does not have the proper format of metadata for our viewer.");
			return -1;
		}

		fread(&cnt, sizeof(short), 1, f);
		for (x = 0; x<cnt; x++)
		{
			fread(&t1, sizeof(struct tiff), 1, f);
			if(t1.tag == 0x010F)
			{
				pointer = ftell(f);
				t1.offset = t1.offset+12;
				fseek(f, t1.offset, SEEK_SET);
				fread(print, sizeof(char), t1.num, f);
				printf("%-20s","Manufacturer:");
				printf("%s\n", print);
				fseek(f, pointer, SEEK_SET);
			}
			else if(t1.tag == 0x0110)
			{
				pointer = ftell(f);
				t1.offset = t1.offset+12;
				fseek(f, t1.offset, SEEK_SET);
				fread(print, sizeof(char), t1.num, f);
				printf("%-20s","Model:");
				printf("%s\n", print);
				fseek(f, pointer, SEEK_SET);
			}
			else if(t1.tag == 0x8769)
			{
				fseek(f, (t1.offset+12), SEEK_SET);
				fread(&cnt, sizeof(short), 1, f);
				for (x = 0; x<cnt; x++)
				{
					fread(&t1, sizeof(struct tiff), 1, f);
					if(t1.tag == 0xA002)
						printf("%-20s%d %s", "Width:", t1.offset, "pixels\n");
					else if(t1.tag == 0xA003)
						printf("%-20s%d %s", "Height:", t1.offset, "pixels\n");
					else if(t1.tag == 0x8827)
						printf("%-20sISO %d\n", "ISO:", t1.offset);
					else if(t1.tag == 0x829a)
					{
						pointer = ftell(f);
						t1.offset = t1.offset+12;
						fseek(f, t1.offset, SEEK_SET);
						fread(&y, sizeof(int), 1, f);
						fread(&z, sizeof(int), 1, f);
						printf("%-20s%d/%d%s", "Exposure Time:", y,z," second\n");
						fseek(f, pointer, SEEK_SET);
					}
					else if(t1.tag == 0x829d)
					{
						pointer = ftell(f);
						t1.offset = t1.offset+12;
						fseek(f, t1.offset, SEEK_SET);
						fread(&y, sizeof(int), 1, f);
						fread(&z, sizeof(int), 1, f);
						printf("%-20sf/%1.1f\n", "F-stop:", (double)y/z);
						fseek(f, pointer, SEEK_SET);
					}
					else if(t1.tag == 0x920A)
					{
						pointer = ftell(f);
						t1.offset = t1.offset+12;
						fseek(f, t1.offset, SEEK_SET);
						fread(&y, sizeof(int), 1, f);
						fread(&z, sizeof(int), 1, f);
						printf("%-20s%2.0f mm\n", "Focal Length:", (double)y/z);
						fseek(f, pointer, SEEK_SET);
					}
					else if(t1.tag == 0x9003)
					{
						pointer = ftell(f);
						t1.offset = t1.offset+12;
						fseek(f, t1.offset, SEEK_SET);
						fread(print, sizeof(char), t1.num, f);
						printf("%-20s","Date Taken:");
						printf("%s\n", print);
						fseek(f, pointer, SEEK_SET);
					}
				}


				break;
			}
			
		}
	}
	else
	{
		printf("Could not read file.");
		return -1;
	}

	fclose(f);
	return 0;
}


