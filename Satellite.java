/**
 * This class collects data for the satellite and performs other functions.
 * @author Aaiz Ahmed <aaiza2@umbc.edu>
 * @version May 4, 2014
 * @project CMSC 341 - Spring 2014 - Project #5 Hubble Satellite.
 * @section 01
 */
package project5;

import java.util.Random;

public class Satellite extends Thread {	

	private Random rand = new Random();	
	int randomInt;
	Buffer buff;
	private boolean keepRunning;
	
	/**
	 * Constructor: Builds up the object.
	 * @param buffer
	 */
	Satellite (Buffer b) 
	{		buff = b;
	}

	public void run() 
	{		
		while (true) 
		{
			try 
			{
				if (!buff.isFull())
				{
					randomInt = rand.nextInt(4097);
					//System.out.println("Adding: " + randomInt);
					buff.add( randomInt );
				}						
			}
			catch (InterruptedException e)
			{
				System.out.println("Satellite thread interepted while sleepying.\n" + e.getMessage());
			}
		}		
	}
	
	public void interept() {	keepRunning = false; 	}
	
	public int getRandomInt () {	return randomInt;	}

}
