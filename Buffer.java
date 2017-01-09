/**
 * This class stores the data in an array.
 * @author Aaiz Ahmed <aaiza2@umbc.edu>
 * @version May 4, 2014
 * @project CMSC 341 - Spring 2014 - Project #5 Hubble Satellite.
 * @section 01
 */
package project5;
import java.util.ArrayList;

public class Buffer extends Thread {

	private ArrayList<Integer> B;	
	private int N = 256, size = 2 * (N*N);  

	/**
	 * Default constructor, N = size	
	 */
	public Buffer () {

		B = new ArrayList<Integer>(size);

	}

	public Buffer (int i) {

		N = (int) Math.pow(2, i);
		size = 2 * (N*N);
		B = new ArrayList<Integer>(size);		
	}

	public void run () {

		try {			

			Thread.sleep(0); 			
		}
		catch (InterruptedException e) 
		{ 		          
			System.out.println(  "Buffer thread is interepted when it is  sleeping.\n" + e.getMessage() ); 
		} 
	} 

	synchronized public int size () { return size; }

	synchronized public int elements() { return B.size(); }

	synchronized public void add (int data) {

		if (B.size() < size) { 

			B.add (data);  
			notify(); 
		}

	}

	synchronized public ArrayList<Integer> getBuffer () { return B; }

	synchronized public void remove (int index) { 		

		if (B.get(index) != null) {	

			B.remove(index);  
			notify();		
		}  		

	}	

	synchronized public boolean isFull() {

		if (B.size() >= size) { return true; }

		return false;
	}

	synchronized public void waitForData() {

		try {

			while ( B.size() < size/2 ) wait()  ;
		} 
		catch ( InterruptedException e ) {

			System.out.println("Hmm... an interrupt!") ;      
		}
	}

	synchronized public boolean isEmpty() {

		return B.isEmpty() ;
	}

	synchronized public void waitForSpace() {

		try {

			while ( B.size() >= size ) wait() ;
		} 
		catch ( InterruptedException e ) {

			System.out.println("Hmm... an interrupt!") ;      
		}
	}


}

