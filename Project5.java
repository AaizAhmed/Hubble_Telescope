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

		for (int x = 8; x < 12; x++) {

			Buffer buf = new Buffer (x); 
			buf.start();

			Satellite sat = new Satellite (buf);			
			sat.start();			
			
			for (int y = 1; y < 6; y++) {
				
				System.out.printf("Run #%d, i=%d, j=%d, N=%d, B1=%d, B2=%d, T=%d\n", run, x, y, 
						(int) Math.pow(2, x), buf.size(), buf.size()/2, (int) Math.pow(10, y));

				Receiver rec = new Receiver (buf);	
				
				long timeD = System.currentTimeMillis();			
				rec.start();				
				
				Processing process = new Processing ();

				try 
				{	    	           
					rec.join(); // Wait for receiver to finish	
					
					timeD = System.currentTimeMillis() - timeD;			
					System.out.println ("Time deleting: "+ timeD +" ms\n");
				} 
				catch (InterruptedException e ) 
				{
					System.out.println("Thread Interrupted: " + e.getMessage() );
				}					

				if (rec.getBuffer().length > 0) 
				{
					long time = System.currentTimeMillis();		
										
					process.mergeSort (rec.getBuffer(), (int) Math.pow(10, y));						
					time = System.currentTimeMillis() - time;					
					System.out.println ("Time mergesort: "+ time +" ms");

					process.normalize (rec.getBuffer());	
				}

				process.createImage((int) Math.pow(2, x), (int) Math.pow(10, y)); 
				
				try 
				{
					process.join();					
				} 
				catch (InterruptedException e) 
				{					
					e.printStackTrace();
				}
								
				System.out.println ("Saving image: images/output_N" + Math.pow(2, x) +"_T" + Math.pow(10, y) + ".jpg\n");
				run++;			
			}				
		}
		
		System.out.println("Do I run?");
		System.exit(0);
	}

}
