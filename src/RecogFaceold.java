import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.MalformedURLException;

import javax.swing.border.*;
import java.sql.*;

class RecogFaceold extends JFrame implements ActionListener
{
	private JButton btnStartCam,btnDetect,btnTemp;
	JPicsPanel pp;
	JCameraPanel cameraPan;
	boolean running=false;
	String result;
	String command;
	static String temparture;
	JDesktopPane desktop;

	RecogFaceold(String cmd,JDesktopPane pan)
	{
		command=cmd;
		desktop=pan;
		setSize(270,340);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setTitle("User Identification");
	//	 setAlwaysOnTop( true );

		btnStartCam = new JButton("Start Webcam");
		btnStartCam.addActionListener(this);
		btnStartCam.setBounds(6,250,120,25);

		btnTemp = new JButton("Temperature");
		btnTemp.addActionListener(this);
		btnTemp.setEnabled(false);
		btnTemp.setBounds(136,250,120,25);

		btnDetect = new JButton("Detect Face");
		btnDetect.addActionListener(this);
		btnDetect.setEnabled(false);
		btnDetect.setBounds(12,280,240,25);

		JPanel btnPan = new JPanel();

		add(btnStartCam);
        add(btnTemp);
		add(btnDetect);

		cameraPan = new JCameraPanel();
		pp = new JPicsPanel("jclient.jpg"); // the sequence of snaps appear here
		cameraPan.add(pp,JPanel.CENTER_ALIGNMENT);
		cameraPan.setBounds(6,10,250,230);
		add(cameraPan,"Center");

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				if(running)
				{
					pp.closeDown();

					dispose();
				}
				else
					dispose();
			}
		});
	}
	public void actionPerformed(ActionEvent e)
	{
		Object obj=e.getSource();
		if(obj == btnStartCam)
		{
			if(!running)
			{
				new Thread(pp).start();
				running=true;
				btnTemp.setEnabled(true);
				btnStartCam.setEnabled(false);
			}
		}
		else
        if(obj == btnTemp)
		{
		    try
		    {
    	    /*	Database main = new Database();
			    main.DBcall();
	*/	    }
		    catch(Exception e1)
		    {
				System.out.println("temperr"+e1);
			}
   		  btnTemp.setEnabled(false);
		  btnDetect.setEnabled(true);
		}
		else
		if(obj == btnDetect)
		{
			try 
			{
		    	/*Database main = new Database();
		    	temparture=main.read();*/
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
			
		   if(running)	 
			{/*
				pp.closeDown();
				try
				{
  				   result=ComparingImages.findRecord("./UserData","jclient");
  			  	   dispose();  						
				}
				catch (Exception ex) 
				{ 
					ex.printStackTrace(); 
				}
				running=false;
				if(result != null)
				{
				 {
					 try 
					 {
						new SimpleWrite().openDoor("1");
					 }
					 catch (Exception e1) 
					 {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			   	   System.out.println("Record Detected:..");
    			   
			   	   JOptionPane.showMessageDialog(null,"Family Member Detected \n\t\t Please Wait.. Door Opening Soon","Family Member",JOptionPane.WARNING_MESSAGE);

			   	   // UserRecord depMon = new UserRecord(result);
  		   	      // depMon.setVisible(true);
				 }
	   		    } 
			    else
			   {
			    	 try 
					 {
						new SimpleWrite().openDoor("1");
					 }
					 catch (Exception e1) 
					 {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				   	System.out.println("Record Not Detected:..");				   
			   	
			    	AudioClip ac = null;
					try 
					{
						new SimpleWrite().openDoor("2");

						ac = Applet.newAudioClip(new File(Login.Location+"Bell.wav").toURL());
					    Thread.sleep(1000);
					}
					catch (InterruptedException e1) 
					{
						// TODO Auto-generated catch block
							e1.printStackTrace();
					} 
					catch (MalformedURLException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					ac.play();
			    	JOptionPane.showMessageDialog(null,"   Guest Detected \n\t\t Please Wait.. Door Opening Soon","Visitor",JOptionPane.WARNING_MESSAGE);

			    	String visitorphotopath="jclient.jpg";
			    	EmailAttachmentSender.mailsentwithphoto(Login.OwnerEmail,visitorphotopath);
      	   }
	 	*/}
			else
				JOptionPane.showMessageDialog(null,"Webcam Not Started \n \t Start Webcam" );
		}

	}
	class JCameraPanel extends JPanel
	{
		JCameraPanel()
		{
			setSize(250,230);
		}
		public void paint(Graphics g)
		{
			EtchedBorder border=new EtchedBorder(EtchedBorder.LOWERED,Color.gray ,Color.lightGray );
			Font f=new Font("Times New Roman",Font.BOLD,30);
			g.setFont(f);
			border.paintBorder(this, g, 0, 0,getWidth(),getHeight());
			g.drawString("WebCam",70,110);
		}
	}
	public String getResult()
	{
		return result;
	}
}