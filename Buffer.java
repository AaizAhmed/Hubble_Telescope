/**
 * This class stores the data in an array.
 * @author Aaiz Ahmed <aaiza2@umbc.edu>
 * @version May 4, 2014
 * @project CMSC 341 - Spring 2014 - Project #5 Hubble Satellite.
 * @section 01
 */
package project5;
import java.util.LinkedList;

public class Buffer extends Thread 
{
	private LinkedList<Integer> B;	
	private int size;  

	public Buffer (int i) 
	{
		int N = (int) Math.pow(2, i);
		size = 2 * (N*N);
		
		B = new LinkedList<Integer>();		
	}

	public void run () 
	{
		try 
		{
			Thread.sleep(0); 			
		}
		catch (InterruptedException e) 
		{ 		          
			System.out.println(  "Buffer thread is interepted when it is  sleeping.\n" + e.getMessage() ); 
		} 
	}

	public synchronized int size () 
	{ return size; }

	public synchronized void add (int data) 
	{
		if (B.size() < size) 
		{	
			B.add (data);
			notify();
		}		
	}	

	@SuppressWarnings("null")
	public synchronized int remove () 
	{
		if (B.remove() != null) 
		{		
			notify();
			return B.remove();					
		}
		return (Integer) null; 
	}	

	public synchronized boolean isFull() 
	{	return B.size() >= size;	}
	
	public synchronized boolean isEmpty() 
	{	return B.isEmpty() ;	}

	public synchronized void waitForData() 
	{
		try 
		{
			while ( B.size() < size/2 ) wait();
		} 
		catch ( InterruptedException e ) 
		{
			System.out.println("Hmm... an interrupt!");      
		}
	}	

	public synchronized void waitForSpace() 
	{
		try 
		{
			while ( B.size() >= size ) wait();
		} 
		catch ( InterruptedException e ) 
		{
			System.out.println("Hmm... an interrupt!");      
		}
	}
}

