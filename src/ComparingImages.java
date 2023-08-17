// note both images same then return 0.0
// high differance means differnt images
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.*;

public class ComparingImages
{
   public static String findRecord(String folderpath,String photopath)throws Exception
   {
        File folder=new File(folderpath);
        File file=new File(photopath+".jpg");

        System.out.println("File="+file);
        
    	String n[]=folder.list();

         double avg =0;
         double percentage =0;
         boolean flag=false;

         double minper=20;
         
         String filename=null;

         BufferedImage img1=null;

        BufferedImage img2=null;

	    for(int k=0;k<n.length;k++)
       {
           String path=folderpath+"\\"+n[k];

           img1 = ImageIO.read(new File(path));
           
           img2 = ImageIO.read(file);
          
           int w1 = img1.getWidth();
           int w2 = img2.getWidth();
    	   int h1 = img1.getHeight();
           int h2 = img2.getHeight();

	      if ((w1!=w2)||(h1!=h2))
	      {
		         System.out.println("Both images should have same dimwnsions");
	      }
	      else
	      {
	         long diff = 0;
	         for (int j = 0; j < h1; j++)
	         {
	            for (int i = 0; i < w1; i++)
	            {
	               int pixel1 = img1.getRGB(i, j);

	               Color color1 = new Color(pixel1, true);

	               int r1 = color1.getRed();
	               int g1 = color1.getGreen();
	               int b1 = color1.getBlue();

	               int pixel2 = img2.getRGB(i, j);

	               Color color2 = new Color(pixel2, true);

	               int r2 = color2.getRed();
	               int g2 = color2.getGreen();
	               int b2= color2.getBlue();

               long data = Math.abs(r1-r2)+Math.abs(g1-g2)+ Math.abs(b1-b2);
               diff = diff+data;
            }
         }
         avg = diff/(w1*h1*3);
         percentage = (avg/255)*100;
         
         System.out.println(n[k]+" "+(k+1)+" Difference: "+(float)percentage);
         
         if((Math.min(((float)percentage),((float)minper)))!=(float)minper)
          {
              minper=(float)percentage;
              filename=n[k];
          }           
	    }
      }
      return filename;
     }
}