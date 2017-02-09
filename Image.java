package project5;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import java.util.* ;

public class Image {
	
   /* Save image from an Integer buffer (assues integers are between [0-255]) */
   public static int save_image(LinkedList<Integer> buffer, int N, String filename){
	   
         int width = N;
         int height = N;

         /* Make sure buffer is the correct size */
         if (buffer.size() != N*N){
            System.out.println("ERROR: Arrays of different sizes\n");
            return -1;
         }
           
         /* Create buffered image and loop through buffer adding pixels */
	 try {
          BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

          /*Loop through buffer and assign pixel value*/
          int i=0;
          int x=0, y=0;
          Integer value=0;
          Iterator<Integer> iter = buffer.iterator();
          
          while (iter.hasNext()) {
             value = iter.next();
             bufferedImage.setRGB((int)i%width, (int)i/width, (value << 16) + (value << 8) + value);
             i+=1;
          }

          //Save image
	  ImageIO.write(bufferedImage, "jpg", new File( filename ));
         }
         catch (IOException e) {
           System.out.println(e.getMessage());
         }

         return 1;
   }

  //For Unit Testing
//  public static void main(String[] args) {
//
//      //Set values of N and T
//      int N=512;
//      int T=1000;
//      Random rand = new Random ( System.current/Millis() ) ;
//
//      //Create List
//      ArrayList<Integer> buffer = new ArrayList<Integer>() ;
//
//      //Add random variables to list
//      int value=0;
//      for(int i=0;i<N*N;i++){
//          value = rand.nextInt(256);
//          buffer.add(value);
//      }
//
//      //Sort data
//      Collections.sort(buffer);
//
//      //Save image
//      String filename = String.format("output_image_N%d_T%d.jpg", N, T);
//      save_image(buffer, N, filename);
//   }
   
}
