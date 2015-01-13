#include<stdlib.h>
#include<stdio.h>

struct node {
   int grade;
   struct node * next;
};

typedef struct node node1;

void main() {
   node1 * curr, * head;
   head = NULL;

  int x = 0;
	while(x>=0)
	
{		printf("Enter a grade:");
		scanf("%d", &x);
		printf("To stop, enter -1.\n");
		if (x>=0)
		{
     			 curr = (node1 *)malloc(sizeof(node1));
     			 curr->grade = x;
     			 curr->next  = head;
     			 head = curr;
		}

   }

   curr = head;

   while(curr) {
      printf("%d\n", curr->grade);
      curr = curr->next ;
   }
}
