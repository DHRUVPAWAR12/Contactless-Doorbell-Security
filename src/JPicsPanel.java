import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.text.DecimalFormat;
import java.io.*;
import javax.imageio.*;


class JPicsPanel extends JPanel implements Runnable
{
  private static final int SIZE = 300;  // x/y- dimensions of panel
  private static final int DELAY = 40;  // ms  (25 FPS; could be 25-35ms)

  private String SAVE_FNM = "jmfPic.jpg";
      // file location for a saved snap


  private BufferedImage image = null;
  private JMFCapture camera;
  private boolean running;

  // used for the average ms snap time info
  private int imageCount = 0;
  private long totalTime = 0;
  private DecimalFormat df;
  private Font msgFont;


  public JPicsPanel(String imgname)
  {
	SAVE_FNM=imgname;
    setBackground(Color.white);
    Dimension d = new Dimension(SIZE,SIZE);
    setMinimumSize(d);
    setPreferredSize(d);

    df = new DecimalFormat("0.#");  // 1 dp
    msgFont = new Font("SansSerif", Font.BOLD, 18);

    //new Thread(this).start();   // start updating the panel's image
  } // end of JPicsPanel()




  public void run()
  /* take a picture every DELAY ms */
  {
    camera = new JMFCapture(SIZE);

    long duration;
    BufferedImage im = null;
    running = true;

    while (running)
    {
	  long startTime = System.currentTimeMillis();
      // System.out.println("Start loading image " + (imageCount+1) + "...");
      im = camera.grabImage();  // take a snap
      duration = System.currentTimeMillis() - startTime;

      if (im == null)
        System.out.println("Problem loading image " + (imageCount+1));
      else {
        image = im;   // only update image if im contains something
        imageCount++;
        totalTime += duration;
        // System.out.println("Image " + imageCount + " loaded in " +
        //                     duration + " ms");
        repaint();
      }

      if (duration < DELAY) {
        try {
          Thread.sleep(DELAY-duration);  // wait until DELAY time has passed
        }
        catch (Exception ex) {}
      }
    }

    saveSnap(image, SAVE_FNM);   // save last image
    camera.close();    // close down the camera
  }  // end of run()
public void setImage(String fname)
{
	SAVE_FNM=fname;
}

  public void paintComponent(Graphics g)
  /* Draw the snap and add the average ms snap time at the
     bottom of the panel. */
  {
    super.paintComponent(g);
    g.drawImage(image, 0, 0, this);   // draw the snap
  } // end of paintComponent()


  public void closeDown()
  {
    running = false;
    while (!camera.isClosed())
    {
      try
      {
        Thread.sleep(DELAY);
      }
      catch (Exception ex) {}
    }
  } // end of closeDown()


  private void saveSnap(BufferedImage im, String fnm)
  {
    try
    {
      ImageIO.write(im, "jpg", new File(fnm));
    }
    catch(IOException e)
    {  System.out.println("Could not save image");  }
    }
}
