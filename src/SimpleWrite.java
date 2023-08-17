import java.io.*;
import java.util.*;
import gnu.io.*;

public class SimpleWrite 
{
	private static SerialPort serialPort;
	private static OutputStream   serialOut;	
	private static  CommPortIdentifier port;
	 CommPort commPort;
	static String portName="COM3";
	static int speed=9600;

	public  void openDoor(String i) throws Exception 
	{
	  
	  if(port==null)
	   port = CommPortIdentifier.getPortIdentifier(portName); 
	  if(commPort==null)
		  commPort=  port.open(new SimpleWrite().getClass().getName(),2000);
      serialPort = (SerialPort) commPort;
      serialPort.setSerialPortParams(speed, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
      serialOut=serialPort.getOutputStream();
      System.out.println("  ########### OPEN:");
	  
	  Thread.sleep(5000); 
	   
  	    String str=i;
  		 
  	    serialOut.write(i.getBytes());
	 	serialOut.flush();
	    System.out.println("  ########### SND: "+i.toString());
	 // Thread.sleep(6000);
	    
	  commPort.close();
	  serialPort.close();
	  
	}
	   public static void main(String args[])
	   {
         	try {
				new SimpleWrite().openDoor("2");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
}
