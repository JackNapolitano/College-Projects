//jon18 proj2


//Includes
#include <linux/unistd.h>
#include <sys/mman.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
/////////////////////////////////////////
struct cs1550_sem {
        int value;
        struct Node *first; 
        struct Node *last;
};
/////////////////////////////////////////
void down(struct cs1550_sem *sem) {
       syscall(__NR_cs1550_down, sem);
}
void up(struct cs1550_sem *sem) {
       syscall(__NR_cs1550_up, sem);
}
/////////////////////////////////////////


int main(int argc, char *argv[]) 
{
    int producerCount, consumerCount, bufSize, waitStatus;
    
    if(argc != 4) { 
            printf("Input should be ./prodcons [number of prods] [number of consumers] [bufferSize]");
            exit(1);
    }
    else {
        producerCount = atoi(argv[1]);
        consumerCount = atoi(argv[2]);
        bufSize = atoi(argv[3]);
    }
    //asking the OS for RAM, we use 3 times the size of the struct, to accomadate for having 3 structs. (below)
    void *ptr = mmap(NULL, sizeof(struct cs1550_sem)*3, PROT_READ|PROT_WRITE, MAP_SHARED|MAP_ANONYMOUS, 0, 0);
    
    struct cs1550_sem *empty = (struct cs1550_sem*)ptr;
    //empty sem
    struct cs1550_sem *filled = (struct cs1550_sem*)ptr + 1; 
    //filled sem taking into account offset
    struct cs1550_sem *mutualExclusion = (struct cs1550_sem*)ptr + 2; 
    //mutualExclusion sem taking into account offset for 2 semaphores


    // memory allocation for the buffer that will be shared by the producers and the consumers
    void *tempP = mmap(NULL, sizeof(int)*(bufSize + 1), PROT_READ|PROT_WRITE, MAP_SHARED|MAP_ANONYMOUS, 0, 0);
    
            
    int *count = (int*)tempP;
    int *producerPosition = (int*)tempP + 1;
    int *consumerPosition = (int*)tempP + 2;
    int *bufLoc = (int*)tempP + 3;
    
    filled->value = 0;
    mutualExclusion->value = 1;
    empty->value = bufSize;

    filled->first = NULL;
    mutualExclusion->first = NULL;
    empty->first = NULL;

    filled->last = NULL;
    mutualExclusion->last = NULL;
    empty->last = NULL;

    *count = bufSize; 
    *producerPosition = 0;
    *consumerPosition = 0;
    int x;                
    for(x = 0; x < producerCount; x++) { 
            //if child
            if(fork() == 0) {
                    int producer;

                    while(1) //endless loop
                    {
                            down(empty);
                            //lock
                            down(mutualExclusion); 
                            producer = *producerPosition;
                            bufLoc[*producerPosition] = producer;
                            printf("Producer %c Produced: %d\n", x+65, producer); // Turn i into a letter by adding 65
                            *producerPosition = (*producerPosition+1) % *count;
                            up(mutualExclusion); 
                            //unlock
                            up(filled);
                    }
            }
    }
    
    for(x = 0; x < consumerCount; x++) {
            //if child
            if(fork() == 0) {

                    int consumer;
    
                    while(1) //endless loop
                    {
                            down(filled);
                            //lock
                            down(mutualExclusion); 
                            consumer = bufLoc[*consumerPosition]; // Just pulling out number for now, could be changed in the future
                            printf("Consumer %c Consumed: %d\n", x+65, consumer); // Turn i into a letter by adding 65
                            *consumerPosition = (*consumerPosition+1) % *count;
                            up(mutualExclusion);
                            //unlock
                            up(empty);
                    }
            }
    }
    wait(&waitStatus);
    return 0;
}
