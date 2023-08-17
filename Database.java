import java.io.*;
import java.applet.*;
import java.util.*;
import java.sql.*;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.JOptionPane;

public class Database implements SerialPortEventListener
{
	SerialPort serialPort;

	private static final String PORT_NAMES[] = {"COM6"};
	private BufferedReader input;
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;
	static String rf="";
	static int flag=1;
	static int DBflag=0;
    static String inputLine="";

	public void initialize()
	{
        System.setProperty("gnu.io.rxtx.SerialPorts", "COM4");

		CommPortIdentifier portId = null;

		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		while (portEnum.hasMoreElements())
		 {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES)
			{
				if (currPortId.getName().equals(portName))
				{
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null)
		{
			System.out.println("Could not find COM port.");
		    JOptionPane.showMessageDialog(null,"Temparature Sensor Not Detected","Wrong User",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return;
		}

		try
		{
			serialPort = (SerialPort) portId.open(this.getClass().getName(),TIME_OUT);

			serialPort.setSerialPortParams(DATA_RATE,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

			output = serialPort.getOutputStream();

			serialPort.addEventListener(this);

			serialPort.notifyOnDataAvailable(true);
		}
		catch (Exception e){}
	}

	public synchronized void close()
	{
		if (serialPort != null)
		{
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public synchronized void serialEvent(SerialPortEvent oEvent)
	{
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
		{
			try
			{
				inputLine=input.readLine();
				//System.out.println(inputLine);

                double val=Double.parseDouble(inputLine);
                val+=8;
                if(val>99.9)
                {
                    System.out.println("  "+val);
				    AudioClip ac = Applet.newAudioClip(new File("Images/alert.wav").toURL());
				    ac.play();
				    Thread.sleep(2000);
				     System.exit(0);
			    }
			    else
			    {
				     rf=rf+val;
				     if(!rf.equals("nan"))
				    {
                       insert(rf);
             			serialPort.close();
 				       /*DBflag++;
                       if(DBflag==5)
                       {
            	         read();
            	         System.exit(0);
				        }*/
			         }
			          rf = "";
			     	 inputLine="";
		       }
			}
			catch (Exception e){}
		}
	}

	public String read()
	{
      String val="";	
      try
      {
          Class.forName("com.mysql.jdbc.Driver");
          Connection con=DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/gps","root","tiger");

          Statement stmt=con.createStatement();
          ResultSet rs=stmt.executeQuery("select * from sensor");
          
          while(rs.next())
          val=rs.getString(1).trim();;
          con.close();
         //System.out.println(val);
         //  FileWriter fw=new FileWriter("temp.txt");
         //fw.write(val);
         //fw.close();
      }
	     catch(Exception e2){System.out.println(e2);}
      return(val);
   }

	private void insert(String value)
	{
		Connection conn = getConnection();

		String selectquery = "update sensor set value=? where flag=?";

		PreparedStatement pstmt4 = null;

     	try
    	{
  		    pstmt4 = conn.prepareStatement(selectquery);
			pstmt4.setString(1, value);
			pstmt4.setString(2, "1");
			System.out.println("Inserted in DB: "+value);//pstmt4.toString());
			pstmt4.executeUpdate();
			conn.close();
		}
		catch (Exception e)
		{
						// TODO: handle exception
		}
	}
	String username = "root";
	String password = "tiger";

	static final String DB_URL = "jdbc:mysql://localhost/gps";

	public Connection getConnection()
	{
		Connection conn = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, username, password);
            if(flag==1)
            // System.out.println("Connected To Database");
             flag++;
		}
		catch (Exception e)
		{
			System.out.println(e);//Unable to connect Database");
		}
		return conn;
	}


	//public static void main(String[] args) throws Exception
	public void DBcall() throws Exception
	{
		Database main = new Database();
		main.initialize();

		/*Thread t=new Thread()
		{
			public void run()
			{
				try
				{
					Thread.sleep(1000);
			    }
				catch (InterruptedException ie) {}
			}
		};
		t.start();*/
		//System.out.println("Data Reading Started !!");
	}
}