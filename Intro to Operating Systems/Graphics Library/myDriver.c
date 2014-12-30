typedef unsigned short color_t;

void clear_screen();
void exit_graphics();
void init_graphics();
char getkey();
void sleep_ms(long ms);

void draw_pixel(int x, int y, color_t color);
void draw_rect(int x1, int y1, int width, int height, color_t c);

int main(int argc, char** argv)
{
	int i;
	init_graphics();
	char key;
	int x = (640-20)/2;
	int y = (480-20)/2;
	do
	{
		key = getkey();
		if(key == 'w') y-=1;
		else if(key == 's') y+=1;
		else if(key == 'a') x-=1;
		else if(key == 'd') x+=1;
		//draw a blue line, like an etch-a-sketch
		draw_rect(x, y, 4, 4, 20);
		sleep_ms(5);
	} while(key != 'q');
	exit_graphics();
	return 0;
}
