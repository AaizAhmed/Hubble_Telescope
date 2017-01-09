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
		
		System.out.println(Runtime.getRuntime().availableProcessors());

	  for (int x = 8; x < 9; x++) {
			
		Buffer buf = new Buffer (x); 
		buf.start();
		
	    Satellite sat = new Satellite (buf);      
	    sat.start();
	 
	    for (int y = 1; y < 6; y++) {	    
	    	
	     Receiver rec = new Receiver (buf);		    
	     rec.start();
	      
	      Processing process = new Processing ();
	      
	      try {
	    	  
	         // Wait for receiver to finish
	         
	         rec.join() ;
	         

	      } catch ( InterruptedException e ) {
	         System.out.println("Oh look! an exception!") ;
	      }  
	      
//	      System.out.println ("Before displaying Buffer 2 elements" );
	     
	      if (rec.getB2().size() > 0) {
	    	  
	      process.mergeSort (rec.getB2(), (int) Math.pow(10, y));
	      process.normalize(rec.getB2());	
	      }
	      
	      /* To display the sorted values in the second buffer for testing
	      for (int i = 0; i < process.normalized.size(); i++) {  
	    	 
	    	  System.out.println (rec.getB2().get(i) );  	 
	    	  
	      } */
	      
	        System.out.println ("Yes Done!");
			System.out.println ("Normalized:\n");
			
			process.createImage((int) Math.pow(2, x), (int) Math.pow(10, y)); //  Add T
			System.out.println ("Image Created:\n");

			//System.currenttimesmillis ; 			
	    }
	    		//Stopping/finishing the execution Satellite thread
		         sat.keepRunning = false;	       
		         sat.stop(); 		         
	  }
	}

}
