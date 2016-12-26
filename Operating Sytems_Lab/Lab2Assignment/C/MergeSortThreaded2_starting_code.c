/**
 * CSE lab project 2 -- C version
 */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>


#define SIZE	100
#define NUMBER_OF_THREADS	1000

void *sorter(void *params);	/* thread that performs sorting for subthread*/
void *merger(void *params);	/* thread that performs merging for final result */

int array[SIZE] = {};

// int temp_result[NUMBER_OF_THREADS][];   /*store the temp maximum value for each thread*/

int maximun;                 /* final maximun value for whole array*/

int merge_size;

typedef struct
{
	int start_index;
	int end_index;
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
    // printf("%d",array[0]);
    
	pthread_t workers[NUMBER_OF_THREADS];
	
	clock_t start_clock, end_clock;
	start_clock = clock();
	/* establish the sorting thread */
	parameters *data[NUMBER_OF_THREADS]; 
	for (int idx = 0; idx < NUMBER_OF_THREADS; idx++) {
		// TODO: create N sorting threads, notice the situation of SIZE/NUMBER_OF_THREADS being not integer
		data[idx] = (parameters *) malloc (sizeof(parameters)); 
	}

	/* TODO: now wait for the sorting threads to finish, consider free memory */
	
	
	/* establish the final merge threads
	*  You can merge all the sorted arrays in couples, then deal the residual array. Then do this recursively
	*/

	/* TODO: create threads for merge two sorted arrays (recursively) */
	
	/* TODO: deal with the odd number of sorted array */


	/* TODO: merge the final two array get the full sorted array */

	

	/* output the time consumption */
	end_clock = clock();
	float period_time = (float)(end_clock - start_clock) / CLOCKS_PER_SEC;
	printf ("Total time consumption: %.6f seconds \n", period_time);
	// print all value of sorted array
	printf("Final result: \n");
	for (i = 0; i < SIZE; i++) {
		printf("%d  ",array[i]);
	}
	printf("\n");
	
    return 0;
}

/**
 * Merger and sorter function.
 *
 * This thread can essentially use any algorithm, or you can write by yourself
 */

// sort an unordered array  ascendingly
void *sorter(void *params) {
	/* TODO: add function content */
	pthread_exit(NULL);
}


// merge two sorted array to a new sorted array
void *merger(void *params) {
	/* TODO: add function content */
	pthread_exit(NULL);
}



