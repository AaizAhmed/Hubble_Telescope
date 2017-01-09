/**
 * This class process the data in the buffer.
 * @author Aaiz Ahmed <aaiza2@umbc.edu>
 * @version May 8, 2014
 * @project CMSC 341 - Spring 2014 - Project #5 Hubble Satellite.
 * @section 01
 */
package project5;
import java.util.ArrayList;
import java.util.concurrent.*;

@SuppressWarnings("unchecked")

public class Processing extends Thread {

	public ArrayList<Integer> normalized; 
	private int limit;
	ForkJoinPool manager = new ForkJoinPool();
	
	/**
	 * Constructor:
	 */
	public Processing () {
			
		normalized = new ArrayList<Integer>();
	}

	/**
	 * This method performs the merge sort algorithm.
	 * @param list
	 * @param limit
	 */
	public  void mergeSort(ArrayList<Integer> list , int limit) {

		this.limit = limit;
		int first = 0;
		int last = list.size();

		if (last - first < limit) {  

			insertionSort (list);
			return;
		}

		ArrayList<Integer> tmpList = new ArrayList<Integer>(list.size());
		manager.invoke(new SortTask(list, tmpList, first, last));
	}

	/**
	 * Inner class to implement fork/join
	 * @author Aaiz N Ahmed.
	 */
	private class SortTask extends RecursiveAction {

		ArrayList<Integer> list;
		ArrayList<Integer> tmpList;
		int first, last;

		public SortTask(ArrayList<Integer> list2, ArrayList<Integer> tmp, int lo, int hi) {

			this.list = list2;
			this.tmpList = tmp;
			this.first = lo;
			this.last = hi;
		}

		/**
		 * This method keeps dividing the problem until it can be
		 * passed to the insertSort. 
		 */
		protected void compute() {

			if (last - first < limit)
				insertionSort (list);

			else {
				int mid = (first + last) / 2; //System.out.println("I am in merge sort");
				// the two recursive calls are replaced by a call to invokeAll
				SortTask task1 = new SortTask(list, tmpList, first, mid);
				SortTask task2 = new SortTask(list, tmpList, mid+1, last);
				invokeAll(task1, task2);

			}
		}
	}

	/**
	 * Simple insertion sort.
	 * @param data2 an array of Comparable items.
	 */
	private static <AnyType extends Comparable<? super AnyType>>
	void insertionSort( ArrayList<Integer> data2 )
	{
		int j;

		for( int p = 1; p < data2.size(); p++ )
		{			
			AnyType tmp = (AnyType) data2.get(p);
			for( j = p; j > 0 && tmp.compareTo( (AnyType) data2.get(j - 1)  ) < 0; j-- )
				data2.set(j , data2.get(j - 1) );
			data2.set( j , (Integer) tmp );
		}
	} 
	
	public void normalize (ArrayList<Integer> list) {
		
	//	normalized = new ArrayList<Integer>();
		int min = list.get(0);
		int max = list.get(list.size() - 1);
		int a = 0; int b = 255;
		
		for ( int i = 0; i < list.size(); i++) {
			
			int value = list.get(i); 
			int index;
			
			index = (int) ( (b - a)*(value - min) ) / (max- min) + a;
			
			normalized.add(index);			
		}			
	}
	
	public void createImage(int N , int T) {
		
		 String filename = String.format("output_image_N%d_T%d.jpg", N, T); // Add T
	     Image.save_image(normalized, N, filename);
	}

	/*
	 * Main method for unit testing.
	 */
//	public static void main (String [] args) {
//
//		Random rand = new Random();
//		Processing proc = new Processing();
//
//		int N = 10;
//		for (int i = 0; i < N*N; i++) {
//
//			int temp = rand.nextInt(4097);
//			proc.data.add(temp);
//			System.out.println (proc.data.get(i));		
//		}   
//		System.out.println ("After filling the buffer:\n");
//		
//		proc.mergeSort (proc.data, 10);
//		
//		System.out.println ("After sort:\n");
//		for (int i = 0; i < N*N; i++) {    		
//
//			System.out.println ( proc.data.get(i));
//		} 
//		
//		proc.normalize(proc.data);		
//		System.out.println ("Normalized:\n");		
//		 		
//		for (int i = 0; i < proc.normalized.size(); i++) {
//		  
//			System.out.println ( proc.normalized.get(i));
//		} 
//		
//		System.out.println ("Image Created\n");
//		proc.createImage(N, 100);
//	}
} 
