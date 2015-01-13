//BLACKJACK - John Napolitano
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>
int main(){
	void hit(int [], int []);
	void drawCard(int []);
	void printHands(int [], int [], int);
	void convertAce(int []);
	int * deckp;
	int * player;
	int * dealer;
	int x = 0;
	char ans[5];
	//the deck is used to place "-1's" in the place of a card that has already been drawn. though the driver should never send the same number between 0-51
	int deck[53] = {2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5,6,6,6,6,7,7,7,7,8,8,8,8,9,9,9,9,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,11,11,11,11,0};
	int phand[20] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	int dhand[20] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	//phand[18] and dhand[18] are used to store the sum
	//phand[19] and dhand[19] are used for the number of cards in the hand
	int card();
	srand(time(NULL));

	printf("Welcome to Jack's Blackjack game!\n\n");
	deckp = deck;
	player = phand;
	dealer = dhand;
	//make your hand
	hit(deckp, player);
	hit(deckp, player);
	//make dealer hand
	hit(deckp, dealer);
	hit(deckp, dealer);
	printHands(player, dealer, 1);
	//end of inital hand set up


	//this while loop is the "playing" of the game. 
	//player can hit or stand here and the hands will be updated
	//when the player stands, then the dealer hits or stands
	//when we are in the stand point, then the dealer's hand will be revealed
	//this is because the player can no longer do anything so it is okay to see the dealers hand
	while(x == 0)
	{
		if (phand[18] > 21)
		{
			printf("You busted. Dealer wins.\n\n");
			break;
		}
		printf("Would you like to hit or stand? (hit/stand) : ");
		scanf("%s", ans);
		printf("\n");
		if(strcasecmp("hit", ans)==0)
		{
			hit(deckp, player);
			printHands(player, dealer, 1);
		}
		else if(strcasecmp("stand", ans)==0)
		{
			x = 1;
			if(dhand[18]<17)
			{
				while (dhand[18]<17)
				{
					hit(deckp, dealer);
					printf("Dealer hits\n\n");
					printHands(player, dealer, 0);
				}
			}
			else
				printHands(player, dealer, 0);

			//all of these ifs/else ifs tell the result of the game
			if (dhand[18]>21)
				printf("You win, dealer busted and you stood!");
			else if(dhand[18]==21)
				printf("You lose, dealer has Blackjack.");
			else if(dhand[18]>=phand[18])
				printf("You lose, dealer had a better hand.(dealer wins ties)");
			else if(phand[18]>dhand[18] && phand[18]==21)
				printf("You win, you have Blackjack!");
			else if(phand[18]>dhand[18])
				printf("You win, you have a better hand than the dealer!");

		}
		else
		{
			printf("invalid input, try again.\n");//makes sure player enters stand or hit
		}
	}

	return 0;
}

//draws a random card, will be commented out when implementing driver
int card()
{
	int value;
	value = rand()%52;
	return value;
}
//draw card puts the "-1's" in the deck so that we dont draw the same card twice
//pretty useless when driver is implemented, but will keep in program from now atleast
void drawCard(int deck[])
{
	int pos;
	int value;
	pos = card();
	while(deck[pos] == -1)
	{
		pos = card();
	}
	value = deck[pos];
	deck[52] = value;
	deck[pos] = -1;
}
//prints the hands. If the player is still playing, it will hide the dealers first card and total
//it the player chose stand, then the dealer's hand and sum is revealed
//this is because the player can no longer do anything
//this function also updates the sum of the persons hand
//the sum is stored in the persons hand array at hand[18]
void printHands(int phand[], int dhand[], int hide)
{
	int i;
	int ace = -1;
	dhand[18] = 0;
	phand[18] = 0;
	if(hide == 1) //Hide allows the dealers first card and sum to be hidden until the player is done playing.
	{
		//print dealer
		i = 0;
		printf("The Dealer:\n");
		while(i < dhand[19])
		{
			if (dhand[i] == 11)
				ace = i;
			if(i==0)
				printf("? ");
			else
				printf("%d ", dhand[i] );
			if(i!=dhand[19]-1)
				printf("+ ");
			dhand[18] = dhand[18] + dhand[i];
			i++;
		}
		if(dhand[18]>21 && ace != -1)
		{

			dhand[ace] = 1;
			dhand[18] = 0;
			printf("\nYour ace was converted from 11 to 1.\n");
			i = 0;
			printf("The dealer:\n");
			while(i < dhand[19])
			{
				if (dhand[i] == 11)
					ace = i;
				if(i==0)
					printf("? ");
				else
					printf("%d ", dhand[i] );
				if(i!=dhand[19]-1)
					printf("+ ");
				dhand[18] = dhand[18] + dhand[i];
				i++;
			}
		}
		else if(dhand[18]>21 && ace == -1)
			printf(" DEALER BUSTED!");
		printf("\n\n");
	}
	else if ( hide == 0)//here, since the player is done, we will show the dealers cards.
	{
		//print dealer
		i = 0;
		ace = -1;
		printf("The dealer:\n");
		while(i < dhand[19])
		{
			if (dhand[i] == 11)
				ace = i;
			printf("%d ", dhand[i] );
			if(i!=dhand[19]-1)
				printf("+ ");
			else
				printf("= ");
			dhand[18] = dhand[18] + dhand[i];
			i++;
		}
		printf("%d", dhand[18] );
		if(dhand[18]>21 && ace != -1)
		{
			dhand[ace] = 1;
			dhand[18] = 0;
			printf("\nDealer's ace was converted from 11 to 1.\n");
			i = 0;
			printf("The dealer:\n");
			while(i < dhand[19])
			{
				if (dhand[i] == 11)
					ace = i;
				printf("%d ", dhand[i] );
				if(i!=dhand[19]-1)
					printf("+ ");
				else
					printf("= ");
				dhand[18] = dhand[18] + dhand[i];
				i++;
			}
			printf("%d", dhand[18] );
		}
		else if(dhand[18]>21 && ace == -1)
			printf(" DEALER BUSTED!");
		printf("\n\n");
	}
	//print player
	i = 0;
	ace = -1;
	printf("You:\n");
	while(i < phand[19])
	{
		if (phand[i] == 11)
			ace = i;
		printf("%d ", phand[i] );
		if(i!=phand[19]-1)
			printf("+ ");
		else
			printf("= ");
		phand[18] = phand[18] + phand[i];
		i++;
	}
	printf("%d", phand[18] );
	if(phand[18]>21 && ace != -1)
	{
		phand[ace] = 1;
		phand[18] = 0;
		printf("\nYour ace was converted from 11 to 1.\n");
		i = 0;
		printf("You:\n");
		while(i < phand[19])
		{
			if (phand[i] == 11)
				ace = i;
			printf("%d ", phand[i] );
			if(i!=phand[19]-1)
				printf("+ ");
			else
				printf("= ");
			phand[18] = phand[18] + phand[i];
			i++;
		}
		printf("%d", phand[18] );
	}
	else if(phand[18]>21 && ace == -1)
		printf(" BUSTED!");
	printf("\n\n");
}
//this allows the dealer or the player to draw a card
//it updates the player hand array and the dealer hand array
void hit(int deck[], int person[])
{
	int *deckp;
	deckp = deck;
	drawCard(deckp);
	person[person[19]] = deck[52];
	person[19] = person[19]+1;
}
