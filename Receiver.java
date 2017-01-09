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
	private ArrayList<Integer> test;	
	public boolean keepRunning;
	private int size;
	
	/**
	 * Constructor: 
	 * @param buffer
	 */
	public Receiver (Buffer b, int size) {
		
		buff = b;
		this.size = size;
		received = new ArrayList<Integer> (size);	
		test = new ArrayList<Integer> (size);	
		keepRunning = true;
	}
	
	public void run () {	
		
		while(keepRunning)
		{
			try
			{
				if (buff.elements() >= size/2)
				{					
					removeFromBuffer();
				}
				else
				{	synchronized (buff){
						buff.notify();
						Thread.sleep(0);
					}					
				}
			}
			catch (InterruptedException e) 
			{				
				System.out.println("Receiver thread interepted while sleepying.\n" + e.getMessage());
			} 
		}
	}
	
	/**
	 * Adding the data to the 2nd buffer and removing from the first one.
	 */
	public void removeFromBuffer () {		
		
		for (int i = 0; i < buff.size()/2; i++) {	
		
			int number = buff.getBuffer().get(i);
			buff.remove(i);
			received.add(number);
			
			System.out.println("Buffer element at " + i + " is " + received.get(i));				
	   
		}		
	}
	
	public void removeFromTest () {
		
		for (int i = 0; i < test.size(); i++)
		{
			int number = test.get(i);
			test.remove(i);
			received.add(number);
			
			System.out.println("Buffer element at " + i + " is " + received.get(i));
		}
	}
	
	public void addToTest (int i)
	{
		test.add(i);
	}
	
	public void interept() {	keepRunning = false; 	}
	
	public ArrayList<Integer> getBuffer () { return received; }
	
	public static void main (String[] args) {
		
		Buffer buff = new Buffer(1);
		Receiver r = new Receiver (buff, 10);
		
		for (int i = 0; i < 10; i++) {
			
			r.addToTest(i);
		}		
		
		r.start();
				
		//r.interept();		
	}
}
