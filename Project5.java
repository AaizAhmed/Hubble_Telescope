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

	  int run = 1;
	  System.out.println ("Available processors (cores): " + Runtime.getRuntime().availableProcessors());
	  System.out.println ("Available memory (bytes): " + Runtime.getRuntime().freeMemory() + "\n");
	  
	  for (int x = 8; x < 9; x++) {
			
		Buffer buf = new Buffer (x); 
		buf.start();
		
	    Satellite sat = new Satellite (buf);      
	    sat.start();
	 
	    for (int y = 1; y < 2; y++) {	    
	    	
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
	      
	      System.out.println ("Run #" + run + ": i=" + x + ",j=" + y + ",N=" + (int) Math.pow(2, x)+
	    		               ",B1="+ buf.size() + ",B2=" + buf.size()/2 +",T=" + (int) Math.pow(10, y));
	      
	      if (rec.getBuffer().size() > 0) {
	    	
	      long t1 = System.currentTimeMillis();
	      
	      process.mergeSort (rec.getBuffer(), (int) Math.pow(10, y));
	      long t2 = System.currentTimeMillis();
	      System.out.println ("Time mergesort: "+ (t2-t1) +"ms");
	      
	      process.normalize(rec.getBuffer());	
	      }
	      
	      /* To display the sorted values in the second buffer for testing
	      for (int i = 0; i < process.normalized.size(); i++) {  
	    	 
	    	  System.out.println (rec.getB2().get(i) );  	 
	    	  
	      } */
	      
//	        System.out.println ("Yes Done!");
//			System.out.println ("Normalized:\n");
			
			process.createImage((int) Math.pow(2, x), (int) Math.pow(10, y)); 
			
//			System.out.println ("Image Created:\n");
			System.out.println ("Saving image: output_N" + Math.pow(2, x) +"_T" + Math.pow(10, y) + ".jpg\n");
			run++;			
	    }
	    		//Stopping/finishing the execution Satellite thread
		         //sat.keepRunning = false;	       
		         sat.stop(); 		         
	  }
	}

}
