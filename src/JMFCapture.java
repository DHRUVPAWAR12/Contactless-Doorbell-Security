import java.awt.*;import java.awt.image.*;import java.io.*;import java.util.*;import java.awt.event.*;import javax.media.*;import javax.media.control.*;import javax.media.format.*;import javax.media.util.*;public class JMFCapture implements ControllerListener{  private static final String CAP_DEVICE = "vfw:Microsoft WDM Image Capture (Win32):0";  private static final String CAP_LOCATOR = "vfw://0";  private static final int MAX_TRIES = 7;  private static final int TRY_PERIOD = 1000;   // ms  private int size;       // x/y- dimensions of final BufferedImage  private double scaleFactor;  // snap --> final image scaling  private Player p;  private FrameGrabbingControl fg;  private BufferToImage bufferToImage = null;  private boolean closedDevice;  private Object waitSync = new Object();  private boolean stateTransitionOK = true;  public JMFCapture(int sz)  {    size = sz;    closedDevice = true;   // since device is not available yet    try    {      MediaLocator ml = findMedia(CAP_DEVICE);      p = Manager.createRealizedPlayer(ml);    }    catch (Exception e)    {      System.out.println("Failed to create player");      System.exit(0);    }    p.addControllerListener(this);    // create the frame grabber    fg =  (FrameGrabbingControl) p.getControl(                            "javax.media.control.FrameGrabbingControl");    if (fg == null)    {      System.out.println("Frame grabber could not be created");      System.exit(0);    }    p.start();    if (!waitForStart())    {      System.err.println("Failed to start the player.");      System.exit(0);    }    waitForBufferToImage();  }  private MediaLocator findMedia(String requireDeviceName)  {    Vector devices = CaptureDeviceManager.getDeviceList(null);    if (devices == null)    {      System.out.println("Devices list is null");      System.exit(0);    }    if (devices.size() == 0)    {      System.out.println("No devices found");      System.exit(0);    }    for (int i = 0; i < devices.size(); i++)    {      CaptureDeviceInfo devInfo = (CaptureDeviceInfo) devices.elementAt(i);      String devName = devInfo.getName();      if (devName.equals(requireDeviceName))      {		System.out.println("Found device: " + requireDeviceName);        return devInfo.getLocator();   // this method may not work      }    }    return new MediaLocator(CAP_LOCATOR);  }  private boolean waitForStart()  {	  synchronized (waitSync)	  {         try         {             while (p.getState() != Controller.Started && stateTransitionOK)             waitSync.wait();         }         catch (Exception e) {}    }    return stateTransitionOK;  }  private void waitForBufferToImage()  {    int tryCount = MAX_TRIES;    while (tryCount > 0)    {      if (hasBufferToImage())   // initialization succeeded        break;      try      {		  Thread.sleep(TRY_PERIOD);      }      catch (InterruptedException e)      {		  System.out.println(e);	  }      tryCount--;    }    if (tryCount == 0)    {      System.out.println("Giving Up");      System.exit(0);    }    closedDevice = false;   // device now available  }  private boolean hasBufferToImage()  {    Buffer buf = fg.grabFrame();     // take a snap    if (buf == null)    {      System.out.println("No grabbed frame");      return false;    }    VideoFormat vf = (VideoFormat) buf.getFormat();    if (vf == null)    {      //System.out.println("No video format");      return false;    }    int width = vf.getSize().width;     // the image's dimensions    int height = vf.getSize().height;    if (width > height)      scaleFactor = ((double) size) / width;    else      scaleFactor = ((double) size) / height;    bufferToImage = new BufferToImage(vf);    return true;  }  // end of hasBufferToImage()  synchronized public BufferedImage grabImage()  {    if (closedDevice)      return null;    Buffer buf = fg.grabFrame();    if (buf == null)    {      System.out.println("No grabbed buffer");      return null;    }    Image im = bufferToImage.createImage(buf);    if (im == null)    {      System.out.println("No grabbed image");      return null;    }    return makeBIM(im);  }  private BufferedImage makeBIM(Image im)  {    BufferedImage copy = new BufferedImage(size, size,                                        BufferedImage.TYPE_INT_RGB);    Graphics2D g2d = copy.createGraphics();    g2d.scale(scaleFactor, scaleFactor);    g2d.drawImage(im,0,0,null);    g2d.dispose();    return copy;  }  // end of makeBIM()  synchronized public void close()  {	  p.close();     closedDevice = true;  }  public boolean isClosed()  {  return closedDevice;  }  public void controllerUpdate(ControllerEvent evt)  {    if (evt instanceof StartEvent) {   // the player has started      synchronized (waitSync) {        stateTransitionOK = true;        waitSync.notifyAll();      }    }    else if (evt instanceof ResourceUnavailableEvent) {      synchronized (waitSync) {  // there was a problem getting a player resource        stateTransitionOK = false;        waitSync.notifyAll();      }    }  }}