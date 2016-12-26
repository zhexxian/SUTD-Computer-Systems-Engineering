import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MultiThread {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		// define thread number
		int NumOfThread = Integer.valueOf(args[1]);
		// read data from txt
		Scanner fileIn = new Scanner(new File(args[0]));
		ArrayList<Integer> array = new ArrayList<Integer>();
		ArrayList<Integer> arrayOfMax = new ArrayList<Integer>();
		ArrayList<SimpleThread> arrayOfThreads = new ArrayList<SimpleThread>();
		ArrayList<ArrayList<Integer>> subarrays = new ArrayList<ArrayList<Integer>>();
		int threadMax;
		int NumOfElement=0;
		while(fileIn.hasNextInt()){
			array.add(fileIn.nextInt());
			NumOfElement++;
		}
		fileIn.close();
		
		for (int i=0; i<NumOfElement; i+= NumOfElement/NumOfThread) {
			// partition the array list into N part
			subarrays.add(new ArrayList<Integer>(array.subList(i, i + NumOfElement/NumOfThread)));
		}

		System.out.println(subarrays);

		for (int i=0; i<NumOfThread; i++) {
			// run SimpleThread with N threads
			arrayOfThreads.add(new SimpleThread(subarrays.get(i)));
		}

		for (SimpleThread thread : arrayOfThreads) {
			thread.start();
		}

		for (SimpleThread thread : arrayOfThreads) {
			thread.join();
		}

		for (SimpleThread thread : arrayOfThreads) {
			// get the N max values
			threadMax = thread.getMax();
			// show the N max values
			System.out.println("Max value of thread #" + arrayOfThreads.indexOf(thread) + " is:" + threadMax);
			arrayOfMax.add(threadMax);
		}
		
		System.out.println("Overall max value is:" + Collections.max(arrayOfMax));

	}
}

//extend thread
class SimpleThread extends Thread {
	private ArrayList<Integer> list;
	private int max;

	public int getMax() {
		return max;
	}

	SimpleThread(ArrayList<Integer> array) {
		list = array;
	}

	public void run() {
		// implement actions here
		max = Collections.max(list);
	}
}