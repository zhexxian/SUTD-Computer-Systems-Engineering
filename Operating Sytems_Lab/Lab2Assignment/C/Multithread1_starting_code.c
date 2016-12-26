/**
 * CSE Lab project 2 -- C version
 * 
 */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define SIZE	100
#define NUMBER_OF_THREADS	10

void *get_max_from_array(void *params);	/* thread that performs basic maximun searching algorithm for subthread*/
void *get_max_from_result(void *params);	/* thread that performs maximun searching for final result */

int array[SIZE] = {};

int temp_result[NUMBER_OF_THREADS] = {};   /*store the temp maximum value for each thread*/

int maximun;                 /* final maximun value for whole array*/

typedef struct
{
	int start_index;
	int end_index;
	int temp_resultID;      // identify sub array ID
} parameters;

int main (int argc, const char * argv[]) 
{

    int i = 0;

    FILE *infile;
    infile = fopen("./input_1.txt", "r");

    while(!feof(infile))
    {
	fscanf(infile,"%d",&array[i]);
	i++;
    }

    fclose(infile);
    
    /* TODO: define threads array (tips: pthread_t) */
	
	
	/* TODO: create N threads */
	parameters *data[NUMBER_OF_THREADS]; 
	for (int idx = 0; idx < NUMBER_OF_THREADS; idx++) {
		data[idx] = (parameters *) malloc (sizeof(parameters));

	}
	/* TODO: wait threads end */

	
	/* create new thread for final maximun searching */
	pthread_t findMax;
	parameters *data1;
	data1 = (parameters *) malloc(sizeof(parameters));
	data1->start_index = 0;
	data1->end_index = SIZE-1;
	data1->temp_resultID = 0;
	pthread_create(&findMax, NULL, get_max_from_result, data1);
	
	/* wait for the final maximun search thread to finish */
	pthread_join(findMax, NULL);

	/* output the maximum value */
	printf("Maximun value is: %d \n", maximun);
	
    return 0;
}

/**
 * Maximum value search function.
 *
 * This thread can essentially use any algorithm for finding max value, or you can write by yourself
 * You can write any function format. such as write a general get_max function but not two seperate functions as below.
 */


/* write your get max value from origin array, will be used by multi-threads */
void *get_max_from_array(void *params) {
	/* TODO: add function content */
	pthread_exit(NULL);
}


/* write your get max value from the new array which contains maximun value of all thread */
void *get_max_from_result(void *params) {
	/* TODO: add function content */
	pthread_exit(NULL);
}



