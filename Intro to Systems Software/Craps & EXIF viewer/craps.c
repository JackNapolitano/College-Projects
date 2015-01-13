#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>
int main(){
	int roll();
	void buttonOn(int x);
	int but = 0;
	char name[20];
	char ans[4];
	char again[4] = "yes";
	int point;
	int die1;
	int die2;

	srand(time(NULL));
	printf("Welcome to Jack's Casino!\nPlease Enter your name:");
	scanf("%s", name);
	printf("%s, would you like to 'play' or 'quit'? ", name);
	scanf("%s", ans);
	if(stricmp("play",ans)==0)
	{
		while(stricmp("yes", again)==0)
		{
			die1 = roll();
			die2 = roll();
			point = die1+die2;
			printf("\nYou have rolled a %d + %d = %d\n", die1, die2, point);

			switch(point)
			{
				case 2:
				case 3:
				case 12:
					printf("You lose :(\n");
					break;
				case 7:
					printf("win\n");
					break;
				default:
					but = point;
					buttonOn(but);
					break;
			}
			printf("Play again? ");
			scanf("%s", again);
		}
	}
	printf("Goodbye, %s.", name);
	return 0;
}


int roll()
{
	int value;
	value = rand()%6+1;
	return value;
}
void buttonOn(int x)
{
	int roll();
	int die1;
	int die2;
	int sum = 0;
	printf("The point is %d \n", x);
	while(sum !=7 && sum != x)
	{
		printf("Hit enter to roll again\n");
		system ("pause");
		die1 = roll();
		die2 = roll();
		sum = die1+die2;
		printf("\nYou have rolled a %d + %d = %d\n", die1, die2, sum);
	}
	if(sum == 7)
	{
		printf("You lose :(\n");
	}
	if(sum == x)
	{
		printf("You win!\n");
	}

}
