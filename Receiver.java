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

	Buffer buff;
	private ArrayList<Integer> received;

	public boolean keepRunning;

	/**
	 * Constructor: 
	 * @param buffer
	 */
	public Receiver (Buffer b) 
	{		
		buff = b;
		received = new ArrayList<Integer> ();			
		keepRunning = true;
	}

	public void run () 
	{		
		while(true)
		{
			try
			{				
				removeFromBuffer();	
				Thread.sleep(0);			
			}
			catch (InterruptedException e) 
			{				
				System.out.println("Receiver thread interepted while sleepying.\n" + e.getMessage());
			} 
		}
	}

	/**
	 * Adding the data to the 2nd buffer and removing from the first one.
	 * @throws InterruptedException 
	 */
	public void removeFromBuffer () throws InterruptedException 
	{
		System.out.println(buff.getBuffer());
		
		if ( !buff.isEmpty() )
		for (int i = 0; i < buff.size()/2; i++) 
		{
			int number = buff.getBuffer().get(i);
			
			System.out.println("Removing: " + number);
			
			buff.remove(i);
			received.add(number);

			//System.out.println("Buffer element at " + i + " is " + received.get(i));
		}

	}

	public void interept() {	keepRunning = false; 	}

	public ArrayList<Integer> getBuffer () { return received; }

	//	public static void main (String[] args) 
	//	{		
	//		Buffer buff = new Buffer(1);
	//		Receiver r = new Receiver (buff);
	//						
	//		r.start();
	//				
	//		//r.interept();		
	//	}
}
