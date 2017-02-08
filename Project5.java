/**
 * This class contains main to run this project.
 * @author Aaiz Ahmed <aaiza2@umbc.edu>
 * @version May 7, 2014
 * @project CMSC 341 - Spring 2014 - Project #5 Hubble Satellite.
 * @section 01
 */
package project5;

public class Project5 {

	public static void main (String [] args) {

		int run = 1;
		System.out.println ("Available processors (cores): " + Runtime.getRuntime().availableProcessors());
		System.out.println ("Available memory (bytes): " + Runtime.getRuntime().freeMemory() + "\n");

		for (int x = 8; x < 9; x++) {

			Buffer buf = new Buffer (x); 
			buf.start();

			Satellite sat = new Satellite (buf);      
			sat.start();

			for (int y = 1; y < 3; y++) {	    

				Receiver rec = new Receiver (buf);		    
				rec.start();

				Processing process = new Processing ();
				//process.start();

				try 
				{	    	           
					rec.join(); // Wait for receiver to finish	         
				} 
				catch (InterruptedException e ) 
				{
					System.out.println("Thread Interrupted: " + e.getMessage() );
				}				

				System.out.printf("Run #%d, i=%d, j=%d, N=%d, B1=%d, B2=%d, T=%d\n", run, x, y, 
						(int) Math.pow(2, x), buf.size(), buf.size()/2, (int) Math.pow(10, y));

				if (rec.getB2().size() > 0) {

					long time = System.currentTimeMillis();
					
					process.mergeSort (rec.getB2(), (int) Math.pow(10, y));					
					
					time = System.currentTimeMillis() - time;
					
					System.out.println ("Time mergesort: "+ time +"ms");

					process.normalize(rec.getB2());	
				}

				/* To display the sorted values in the second buffer for testing
	      for (int i = 0; i < process.normalized.size(); i++) {  

	    	  System.out.println (rec.getB2().get(i) );  	 

	      } */

				//	        System.out.println ("Yes Done!");
				//			System.out.println ("Normalized:\n");

				//process.createImage((int) Math.pow(2, x), (int) Math.pow(10, y)); 
				
				try 
				{
					process.join();					
				} 
				catch (InterruptedException e) 
				{					
					e.printStackTrace();
				}
				
				process.stop();
				//process.keepRunning = false;
				
				System.out.println ("Saving image: images/output_N" + Math.pow(2, x) +"_T" + Math.pow(10, y) + ".jpg\n");
				run++;			
			}

			//Stopping the Satellite thread
			//sat.keepRunning = false;				
		}
		
		System.out.println("Do I run?");
		System.exit(0);
	}

}
