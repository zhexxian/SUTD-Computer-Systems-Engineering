import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class MergeSortThreaded2 {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException  {
		// define thread number
		int NumOfThread = Integer.valueOf(args[1]);
		// read data from txt
		Scanner fileIn = new Scanner(new File(args[0]));
		ArrayList<Integer> array = new ArrayList<Integer>();
		ArrayList<Integer> mergedArray = new ArrayList<Integer>();
		ArrayList<Integer> sortedArray = new ArrayList<Integer>();
		ArrayList<Integer> threadSorted = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> subarrays = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> arrayOfSorted = new ArrayList<ArrayList<Integer>>();
		ArrayList<MergeThread> arrayOfThreads = new ArrayList<MergeThread>();
		int NumOfElement=0;
		while(fileIn.hasNextInt()){
			array.add(fileIn.nextInt());
			NumOfElement++;
		}
		fileIn.close();
		// partition the array into N part
		for (int i=0; i<NumOfElement; i+= NumOfElement/NumOfThread) {
			subarrays.add(new ArrayList<Integer>(array.subList(i, i + NumOfElement/NumOfThread)));
		}
		// start recording time
		long startTime = System.currentTimeMillis();
		// run MergeThread with N threads
		for (int i=0; i<NumOfThread; i++) {
			arrayOfThreads.add(new MergeThread(subarrays.get(i)));
		}

		for (MergeThread thread : arrayOfThreads) {
			thread.start();
		}

		for (MergeThread thread : arrayOfThreads) {
			thread.join();
		}

		for (MergeThread thread : arrayOfThreads) {
			threadSorted = thread.getInternal();
			arrayOfSorted.add(threadSorted);
		}
		// merge the N sorted array
		// get the final sorted list
		mergedArray = mergeArrays(arrayOfSorted);

		// end recording time
		long endTime = System.currentTimeMillis();
		// show the time
		long runningTime = endTime - startTime;
		System.out.println("Running time is " + runningTime + " milliseconds");

		// print the sorted array value
		System.out.println(mergedArray);
		System.out.println("\nFinish sorting!");

	}

	// merge sortedLists into a full sorted list
	public static ArrayList<Integer> mergeArrays(ArrayList<ArrayList<Integer>> sortedLists) throws InterruptedException, FileNotFoundException{
		// create sorted list, name as mergedList
		ArrayList<Integer> mergedList = new ArrayList<Integer>();
		int n = sortedLists.size();
		// merge the multiple sorted arrays
		for(ArrayList<Integer> list:sortedLists){
			for(Integer element:list){
				mergedList.add(element);
			}
		}

		MergeThread mt = new MergeThread(mergedList);
		mt.start();
		mt.join();
		mergedList = mt.getInternal();
		
		return mergedList;
	}
}

// extend thread
class MergeThread extends Thread {
	
	private ArrayList<Integer> list;
	//private ArrayList<Integer> helper;
	//private int n;

	public ArrayList<Integer> getInternal() {
		return list;
	}

	// implement merge sort here, recursive algorithm
	public void mergeSort(ArrayList<Integer> array) {
		// BUBBLE SORT
		int n = array.size();
		boolean swapped = true;
		Integer temp;
		while(swapped){
			swapped = false;
			for(int i = 1; i<n; i++){
				if(array.get(i-1)>array.get(i)){
					Collections.swap(array, i, i-1);
					swapped = true;
				}
			}
			n--;
		}	
	// MERGE SORT
	// 	if (low < high) {
	// 	      // Get the index of the element which is in the middle
	// 	      int middle = low + (high - low) / 2;
	// 	      // Sort the left side of the array
	// 	      mergeSort(low, middle,array);
	// 	      // Sort the right side of the array
	// 	      mergeSort(middle + 1, high,array);
	// 	      // Combine them both
	// 	      merge(low, middle, high, array);
 //      	}
 //    }

	// public void merge(int low, int middle, int high,ArrayList<Integer> array){
		
	// 	// Copy both parts into the helper array
	//     for (int i = low; i <= high; i++) {
	//       helper.set(i,array.get(i));
	//     }
	//     int i = low;
	//     int j = middle + 1;
	//     int k = low;
	//     // Copy the smallest values from either the left or the right side back
	//     // to the original array
	//     while (i <= middle && j <= high) {
	//       if (helper.get(i) <= helper.get(j)) {
	//         array.set(k,helper.get(i));
	//         i++;
	//       } else {
	//         array.set(k,helper.get(j));
	//         j++;
	//       }
	//       k++;
	//     }
	//     // Copy the rest of the left side of the array into the target array
	//     while (i <= middle) {
	//       array.set(k,helper.get(i));
	//       k++;
	//       i++;
	//     }
	 }


	MergeThread(ArrayList<Integer> array) {
		list = array;
		//n = array.size();
	}

	public void run() {
		// called by object.start()
		//mergeSort(0,n-1,list);
		mergeSort(list);
	}
}
