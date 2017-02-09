/**
 * This class receives the data from the buffer and stores in an array/buffer.
 * @author Aaiz Ahmed <aaiza2@umbc.edu>
 * @version May 4, 2014
 * @project CMSC 341 - Spring 2014 - Project #5 Hubble Satellite.
 * @section 01
 */
package project5;
import java.util.ArrayList;

public class Receiver extends Thread {

	Buffer buf;
	private int[] B2;	
	public boolean keepRunning ;

	/**
	 * Constructor: 
	 * @param buffer
	 */
	public Receiver (Buffer b) 
	{
		buf = b;
		B2 = new int[ buf.size()/2 ];
	}

	public void run () {		

		try {

			keepRunning = true;

			while (keepRunning) 
			{
				buf.waitForData(); 			

				if (buf.isFull() ) 
				{
					removeB1(); 
					keepRunning = false;				
				}
				
				Thread.sleep(0);
			}

			//System.out.println("Finished Receiver: \n");
		}
		catch (InterruptedException e) {

			System.out.println("Receiver thread interepted while sleepying.\n" + e.getMessage());
		} 
	}

	/**
	 * Adding the data to the 2nd buffer and removing from the first one.
	 */
	public void removeB1 () {		

		for (int i = 0; i < buf.size()/2; i++) 
		{
			int number = buf.getBuffer().get(i);
			buf.remove(i);
			B2[i] = number;

			//System.out.println("Buffer element at " + i + " is " + buf.getBuffer().get(i));				

		}		
	}

	public int[] getBuffer () { return B2; }
}
