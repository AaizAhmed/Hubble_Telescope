/**
 * This class contains main to run this project.
 * @author Aaiz Ahmed <aaiza2@umbc.edu>
 * @version May 7, 2014
 * @project CMSC 341 - Spring 2014 - Project #5 Hubble Satellite.
 * @section 01
 */
package project5;

//-----------------------------------------------------------------
//http://www.programcreek.com/2009/02/notify-and-wait-example/
//-----------------------------------------------------------------

public class Project5 {
	
	public static void main (String [] args) {
		
		System.out.println("Available processors (cores): " + 
							Runtime.getRuntime().availableProcessors());
		
		System.out.println("Available memory (bytes): " + 
				Runtime.getRuntime().freeMemory() + "\n");
		
		Buffer buf = new Buffer (); 
		buf.start();
		
	    Satellite sat = new Satellite (buf);      
	    sat.start();
	    
	    Receiver rec = new Receiver (buf, 256);		    
	    rec.start();
	    
	    Processing process = new Processing ();

	  int size, run = 1;
	  
	  for (int x = 8; x < 9; x++) {		
		
		size = (int) Math.pow(2, x);
		
		buf = new Buffer(x);
		rec = new Receiver (buf, size*size);	    
	    
	    for (int y = 1; y < 3; y++) {	
	    	
	     System.out.println("Run #" + run + ": i = " + x + ", j = " + y + ", N = " + size
	    		 		  + ", B1 = " + size*size*2 + ", B2 = " + size*size + ", T = " + (int) Math.pow(10, y));
	    	 
	      try {	    	  
	         // Wait for receiver to finish	         
	         rec.join() ;	         

	      } catch ( InterruptedException e ) {
	         System.out.println("Oh look! an exception!") ;
	      }      
			
			//process.createImage((int) Math.pow(2, x), (int) Math.pow(10, y)); //  Add T
			System.out.println ("Image Created:\n");
			run++;
	    }
	    		//Stopping/finishing the execution Satellite thread
		         //sat.keepRunning = false;	       
		         sat.interept();
		         sat.stop();
	  }
	}

}
