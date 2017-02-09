/**
 * This class process the data in the buffer.
 * @author Aaiz Ahmed <aaiza2@umbc.edu>
 * @version May 8, 2014
 * @project CMSC 341 - Spring 2014 - Project #5 Hubble Satellite.
 * @section 01
 */
package project5;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.*;

public class Processing extends Thread {

	Buffer buf;	
	private LinkedList<Integer> normalized; 
	private int limit;
	ForkJoinPool manager = new ForkJoinPool();

	/**
	 * Constructor:
	 */
	public Processing () 
	{
		normalized = new LinkedList<Integer>();
	}

	/**
	 * This method performs the merge sort algorithm.
	 * @param list
	 * @param limit
	 */
	public void mergeSort(int[] array, int limit) 
	{
		this.limit = limit;
		
		if (array.length <= limit) 
		{
			insertionSort (array);
			return;
		}		
		
		manager.invoke( new SortTask(array) );
	}

	/**
	 * Inner class to implement fork/join
	 * @author Aaiz N Ahmed.
	 */
	@SuppressWarnings("serial")
	private class SortTask extends RecursiveAction 
	{
		int array[];

		public SortTask( int[] splitArray ) 
		{
			this.array = splitArray;	
		}

		/**
		 * This method keeps dividing the problem until it can be
		 * passed to the insertSort. 
		 */
		protected void compute() 
		{
			if (array.length <= limit)
			{	insertionSort (array);	}

			else {
				
				int mid = this.array.length / 2; 
				
				int[] leftArray = Arrays.copyOfRange(array, 0, mid);
				int[] rightArray = Arrays.copyOfRange(array, mid, array.length);
				
				SortTask task1 = new SortTask( leftArray );
				SortTask task2 = new SortTask( rightArray );
				invokeAll(task1, task2);				

				merge(leftArray, rightArray, array);
			}
		}

		private void merge(int[] left, int[] right, int[] original)
		{
			int iLeft = 0;   // index into left array
			int iRight = 0;   // index into right array

			for (int i = 0; i < original.length; i++) 
			{
				if ( iRight >= right.length || (iLeft < left.length && left[iLeft] <= right[iRight]) ) 
				{
					original[i] = left[iLeft];    // take from left
					iLeft++;
				} 
				else 
				{
					original[i] = right[iRight];   // take from right
					iRight++;
				}
			}
		}
	}
	//End of SortTask class.

	/**
	 * Simple insertion sort.
	 * @param subArray an array of integers.
	 */
	private void insertionSort(int[] subArray)
	{
		for (int i = 1; i < subArray.length; i++)
		{
			int toCompare = subArray[i];
			int j = i;
			
			while (j>0 && (subArray[j-1] > toCompare))
			{
				subArray[j] = subArray[j-1];
				j--;
			}
			subArray[j] = toCompare;
		}
	}
	
	public void normalize (int[] array) 
	{
		int min = array[0];
		int max = array[array.length - 1];
		int a = 0; int b = 255;

		for ( int i = 0; i < array.length; i++) 
		{
			int value = array[i]; 
			value = (int) ( (b - a)*(value - min) ) / (max- min) + a;

			normalized.add(value);
		}
	}

	public void createImage(int N , int T) 
	{
		String filename = String.format("output_image_N%d_T%d.jpg", N, T); // Add T
		Image.save_image(normalized, N, filename);
	}

	/*
	 * Main method for unit testing.
	 */
	public static void main (String [] args) {

		Random rand = new Random();
		Processing proc = new Processing();

		int N = 1024;		
		int [] data = new int[N];
		
		for (int i = 0; i < N; i++) 
		{
			int temp = rand.nextInt(4097);
			data[i] = temp;
			//System.out.println (proc.data.get(i));		
		}   
		System.out.println ("After filling the buffer:\n");

		proc.mergeSort (data, 10);

		System.out.println ("After sort:\n");
		for (int i = 0; i < N; i++) 
		{
			System.out.println ( data[i]);
		} 

		proc.normalize(data);		
		System.out.println ("Normalized:\n");		

		for (int i = 0; i < proc.normalized.size(); i++) 
		{
			System.out.println ( proc.normalized.get(i));
		} 

		System.out.println ("Image Created\n");
		//proc.createImage(N, 100);
	}
} 
