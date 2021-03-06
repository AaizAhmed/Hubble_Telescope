/**
 * This class collects data for the satellite and performs other functions.
 * @author Aaiz Ahmed <aaiza2@umbc.edu>
 * @version May 4, 2014
 * @project CMSC 341 - Spring 2014 - Project #5 Hubble Satellite.
 * @section 01
 */
package project5;

import java.util.Random;

public class Satellite extends Thread 
{
	private Random rand = new Random();	
	int randomInt;
	Buffer B1;
	
	/**
	 * Constructor: Builds up the object.
	 * @param buffer
	 */
	Satellite (Buffer b) 
	{	B1 = b;		}

	public void run() 
	{
		try {			 

			while (true) 
			{ 
				B1.waitForSpace() ;
				randomInt = rand.nextInt(4097);
				B1.add( randomInt );								

				Thread.sleep (0);
			}
		}	
		catch (InterruptedException e) 
		{
			System.out.println("Satellite thread interepted while sleepying.\n" + e.getMessage());
		} 
	}
}
