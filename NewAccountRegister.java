import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.util.Random;

import javax.swing.border.*;
import javax.swing.event.*;
public class NewAccountRegister extends JFrame implements ActionListener
{
	private JPanel jpInfo = new JPanel();
	private JLabel lbNo, lbName, lbDate, lbMobile,lbEmail,lbBuildingName,lbBuildingAdd;
	private JTextField txtNo, txtName, txtMobile,txtEmail,txtBuildingName,txtBuildingAdd;
	private JComboBox cboMonth, cboDay, cboYear;
	private JButton btnSave, btnCancel;
	private JButton btnStartCam,btnCapture;
	private int count = 0;
	private int rows = 0;
	private	int total = 0;

	private String records[][] = new String [500][9];

	private String saves[][] = new String [500][9];

	private FileInputStream fis;
	private DataInputStream dis;
	JPicsPanel pp;
	boolean running=false,detectFace=false,save=false;
	NewAccountRegister ()
	{
		super ("User Registration");
		setSize (574, 340);
		setResizable(false);
		setLocationRelativeTo(null);
		jpInfo.setBounds (0, 0, 500, 115);
		jpInfo.setLayout (null);

		lbNo = new JLabel ("Flat No:");
		lbNo.setForeground (Color.black);
		lbNo.setBounds (265, 10, 90, 25);

        lbName = new JLabel ("User Name:");
		lbName.setForeground (Color.black);
        lbName.setBounds (265, 45, 90, 25);

		lbDate = new JLabel ("Date of Birth:");
		lbDate.setForeground (Color.black);
		lbDate.setBounds (265, 80, 90, 25);

		lbMobile = new JLabel ("Mobile No: ");
		lbMobile.setForeground (Color.black);
		lbMobile.setBounds (265, 115, 90, 25);

		lbEmail = new JLabel ("Email Id: ");
		lbEmail.setForeground (Color.black);
		lbEmail.setBounds (265, 150, 90, 25);

		lbBuildingName = new JLabel ("Building Name");
		lbBuildingName.setForeground (Color.black);
		lbBuildingName.setBounds (263, 185, 90, 25);

		lbBuildingAdd = new JLabel ("Address: ");
		lbBuildingAdd.setForeground (Color.black);
		lbBuildingAdd.setBounds (265, 220, 90, 25);

		txtNo = new JTextField ();
		txtNo.setHorizontalAlignment (JTextField.RIGHT);
		txtNo.setBounds (355, 10, 205, 25);

		txtName = new JTextField ();
		txtName.setHorizontalAlignment (JTextField.RIGHT);
		txtName.setBounds (355, 45, 205, 25);
		
		txtMobile = new JTextField ();
		txtMobile.setHorizontalAlignment (JTextField.RIGHT);
		txtMobile.setBounds (355, 115, 205, 25);

		txtEmail = new JTextField ();
		txtEmail.setHorizontalAlignment (JTextField.RIGHT);
		txtEmail.setBounds (355, 150, 205, 25);

		txtBuildingName = new JTextField ();
		txtBuildingName.setHorizontalAlignment (JTextField.RIGHT);
		txtBuildingName.setBounds (355, 185, 205, 25);

		txtBuildingAdd = new JTextField ();
		txtBuildingAdd.setHorizontalAlignment (JTextField.RIGHT);
		txtBuildingAdd.setBounds (355, 220, 205, 25);

		txtNo.addKeyListener (new KeyAdapter()
		{
			public void keyTyped (KeyEvent ke)
			{
				char c = ke.getKeyChar ();
				if (!((Character.isDigit (c) || (c == KeyEvent.VK_BACK_SPACE))))
				{
					getToolkit().beep ();
					ke.consume ();
      			}
    		}
  		}
		);

		txtName.addKeyListener (new KeyAdapter()
		{
			public void keyTyped (KeyEvent ke)
			{
				char c = ke.getKeyChar ();
				if (Character.isDigit (c))// || (c == KeyEvent.VK_BACK_SPACE))))
				{
					getToolkit().beep ();
					ke.consume ();
      			}
    		}
  		}
		);


		txtBuildingName.addKeyListener (new KeyAdapter()
		{
			public void keyTyped (KeyEvent ke)
			{
				char c = ke.getKeyChar ();
				if (Character.isDigit (c))// || (c == KeyEvent.VK_BACK_SPACE))))
				{
					getToolkit().beep ();
					ke.consume ();
      			}
    		}
  		}
		);

		txtMobile.addKeyListener (new KeyAdapter()
		{
			public void keyTyped (KeyEvent ke)
			{
				char c = ke.getKeyChar ();
				if (!((Character.isDigit (c) || (c == KeyEvent.VK_BACK_SPACE))))
				{
					getToolkit().beep ();
					ke.consume ();
      		    }
    	  }
  		}
		);

		String Months[] = {"January", "February", "March", "April", "May", "June","July", "August", "September", "October", "November", "December"};
		cboMonth = new JComboBox (Months);
		cboDay = new JComboBox ();
		cboYear = new JComboBox ();
		
		cboMonth.setBounds (355, 80, 92, 25);
		cboDay.setBounds (452, 80, 43, 25);
		cboYear.setBounds (500, 80, 60, 25);

		
		for (int i = 1; i <= 31; i++)
		{
			String days = "" + i;
			cboDay.addItem (days);
		}
		for (int i = 1990; i <= 2020; i++)
		{
			String years = "" + i;
			cboYear.addItem (years);
		}

		btnStartCam = new JButton("Start Camera");
		btnStartCam.setBounds(10,265,120,25);
		btnStartCam.addActionListener(this);

		btnCapture = new JButton("Capture Face");
		btnCapture.setBounds(150,265,120,25);
		btnCapture.setEnabled(false);
		btnCapture.addActionListener(this);

		btnSave = new JButton ("Save");
		btnSave.setBounds (285, 265, 120, 25);
		btnSave.setEnabled(false);
		btnSave.addActionListener (this);

		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (425, 265, 120, 25);
		btnCancel.addActionListener (this);

		CameraPanel cm=new CameraPanel();
		cm.setBounds(10,10,250,230);
		pp = new JPicsPanel("UserData/temp.jpg"); // the sequence of snaps appear here
		cm.add(pp,JPanel.CENTER_ALIGNMENT);

		jpInfo.add (lbNo);
		jpInfo.add (txtNo);
		
		jpInfo.add (lbName);
		jpInfo.add (txtName);
		
		jpInfo.add (lbDate);
		jpInfo.add (cboMonth);
		jpInfo.add (cboDay);
		jpInfo.add (cboYear);
		
		jpInfo.add (lbMobile);
		jpInfo.add (txtMobile);
		
		jpInfo.add (lbEmail);
		jpInfo.add (txtEmail);
		
		jpInfo.add(lbBuildingName);
		jpInfo.add(txtBuildingName);
		
		jpInfo.add(lbBuildingAdd);
		jpInfo.add(txtBuildingAdd);
		
		jpInfo.add (btnSave);
		jpInfo.add (btnCancel);
		jpInfo.add (cm);
		jpInfo.add (btnStartCam);
		jpInfo.add (btnCapture);

		getContentPane().add (jpInfo);

		setVisible (true);
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

	public void actionPerformed (ActionEvent ae)
	{
		Object obj = ae.getSource();

		if (obj == btnSave)
		{
	    	btnStartCam.setEnabled(true);
			btnCapture.setEnabled(false);
			btnSave.setEnabled(false);
			if(detectFace)
			{
				populateArray ();	//Load All Existing Records in Memory.
				findRec ();		//Finding if User No. Already Exist or Not.
			}
			else
				JOptionPane.showMessageDialog(null,"Detect The Face From Webcam");
		}
		if (obj == btnCancel)
		{
			txtClear ();
			setVisible (false);
			dispose();
		}
		if(obj == btnStartCam)
		{
			if (txtNo.getText().equals(""))
			{
				JOptionPane.showMessageDialog (this, "Please! Provide User number.","UserSystem - EmptyField", JOptionPane.PLAIN_MESSAGE);
				txtNo.requestFocus();
			}
			else if (txtName.getText().equals(""))
			{
				JOptionPane.showMessageDialog (this, "Please! Provide Name of User.","UserSystem - EmptyField", JOptionPane.PLAIN_MESSAGE);
				txtName.requestFocus ();
			}
			else if (txtMobile.getText().equals(""))
			{
				JOptionPane.showMessageDialog (this, "Please! Provide Mobile Number.","UserSystem - EmptyField", JOptionPane.PLAIN_MESSAGE);
				txtMobile.requestFocus ();
			}
			else if (txtBuildingName.getText().equals(""))
			{
				JOptionPane.showMessageDialog (this, "Please! Provide Building.","UserSystem - EmptyField", JOptionPane.PLAIN_MESSAGE);
				txtBuildingName.requestFocus ();
			}
			else if (txtBuildingAdd.getText().equals(""))
			{
					JOptionPane.showMessageDialog (this, "Please! Provide User Address.","UserSystem - EmptyField", JOptionPane.PLAIN_MESSAGE);
					txtBuildingAdd.requestFocus ();
			}
           else if ((txtMobile.getText().length())!=10)
			{
				JOptionPane.showMessageDialog (this, "Please! Provide Valid Mobile Number.","User Information System - EmptyField", JOptionPane.PLAIN_MESSAGE);
				txtMobile.requestFocus ();
			}
           else if (txtEmail.getText().equals(""))
			{
				JOptionPane.showMessageDialog (this, "Please! Provide Mail Id.","User Information System - EmptyField", JOptionPane.PLAIN_MESSAGE);
				txtMobile.requestFocus ();
			}
			else
			{
				boolean found=false;
				populateArray ();
				for (int x = 0; x < total; x++) 
				{
				
				 if (records[x][6].equals (txtEmail.getText()))
				 {
					found = true;
					JOptionPane.showMessageDialog (this, "Email. " + txtEmail.getText () + " is Already Exist.",
								"UserSystem - WrongNo", JOptionPane.PLAIN_MESSAGE);
					btnStartCam.setEnabled(true);
					btnCapture.setEnabled(false);
					btnSave.setEnabled(false);
					break;
				  }
			    }
				if(!found)
				{
				Random t = new Random();
			    String value = t.nextInt(9999) +"";  // generate 4 digit OTP 	    	     
			    if(value.length()==1)
			    	value="000"+value;
			    if(value.length()==2)
			    	value="00"+value;
			    if(value.length()==3)
			    	value="0"+value;
				System.out.println("OTP Send="+value);
			
				try
				{
					EmailSender.mailsent(txtEmail.getText().trim(),value);	
				}
				catch(Exception e)
				{
					System.out.println(e);
	                JOptionPane.showMessageDialog(this,  "ERROR---Email Not Send","Error", JOptionPane.ERROR_MESSAGE);
	                System.exit(0);
				}
				JOptionPane.showMessageDialog(null,"Email Send Successfully !!!");
				
				String key=JOptionPane.showInputDialog(this,"Enter OTP Send on Email ","OTP",JOptionPane.QUESTION_MESSAGE);			             
				
				if(!key.equals(value))
          		  JOptionPane.showMessageDialog(this,  "Invalid OTP","Error", JOptionPane.ERROR_MESSAGE);
                else 
                {   
			    btnStartCam.setEnabled(false);
			    btnCapture.setEnabled(true);
			    btnSave.setEnabled(false);

					if(!running)
					{
						pp.setImage("UserData/"+txtEmail.getText().trim()+".jpg");
						new Thread(pp).start();
						running=true;
					}
				}
               }	
			}
		}
		else
		if(obj == btnCapture)
		{
	    	btnStartCam.setEnabled(false);
			btnCapture.setEnabled(false);
			btnSave.setEnabled(true);

			if(running)
			{
				pp.closeDown();
				running=false;
				detectFace=true;
			}
			else
				JOptionPane.showMessageDialog(null,"Webcam Not Started \n \t Start Webcam");
		}

	}

	//Function use to load all Records from File when Application Execute.
	void populateArray ()
	{
		try
		{
			fis = new FileInputStream ("User.dat");
			dis = new DataInputStream (fis);
			while (true)
			{
				for (int i = 0; i < 9; i++)
				{
					records[rows][i] = dis.readUTF ();
				}
				rows++;
			}
		}
		catch (Exception ex)
		{
			total = rows;
			if (total == 0) { }
			else {
				try {
					dis.close();
					fis.close();
				}
				catch (Exception exp) { }
			}
		}

	}

	//Function use to Find Record by Matching the Contents of Records Array with ID TextBox.
	void findRec () {

		boolean found = false;
		for (int x = 0; x < total; x++) {
			if (records[x][6].equals (txtEmail.getText ())) {
				found = true;
				JOptionPane.showMessageDialog (this, "User No. " + txtEmail.getText () + " is Already Exist.",
							"UserSystem - WrongNo", JOptionPane.PLAIN_MESSAGE);
				txtClear ();
				break;
			}
		}
		if (found == false) {
			saveArray ();
			save=true;
		}

	}

	//Function use to add new Element to Array.
	void saveArray ()
	{
		saves[count][0] = txtNo.getText ();
		saves[count][1] = txtName.getText ();
		saves[count][2] = "" + cboMonth.getSelectedItem ();
		saves[count][3] = "" + cboDay.getSelectedItem ();
		saves[count][4] = "" + cboYear.getSelectedItem ();
		saves[count][5] = txtMobile.getText ();
		saves[count][6] = txtEmail.getText ();
		saves[count][7] = txtBuildingName.getText ();
		saves[count][8] = txtBuildingAdd.getText ();
		saveFile ();	//Save This Array to File.
		count++;
	}

	//Function use to Save new Record to the File.
	void saveFile ()
	{
		try
		{
			FileOutputStream fos = new FileOutputStream ("User.dat", true);
			DataOutputStream dos = new DataOutputStream (fos);
			dos.writeUTF (saves[count][0]);
			dos.writeUTF (saves[count][1]);
			dos.writeUTF (saves[count][2]);
			dos.writeUTF (saves[count][3]);
			dos.writeUTF (saves[count][4]);
			dos.writeUTF (saves[count][5]);
			dos.writeUTF (saves[count][6]);
			dos.writeUTF (saves[count][7]);
			dos.writeUTF (saves[count][8]);
			JOptionPane.showMessageDialog (this, "The Record has been Saved Successfully",
						"UserSystem - Record Saved", JOptionPane.PLAIN_MESSAGE);
			txtClear ();
			dos.close();
			fos.close();
			dispose();

		}
		catch (IOException ioe)
		{
			JOptionPane.showMessageDialog (this, "There are Some Problem with File",
						"UserSystem - Problem", JOptionPane.PLAIN_MESSAGE);
		}

	}

	//Function use to Clear all TextFields of Window.
	void txtClear ()
	{
		txtNo.setText ("");
		txtName.setText ("");
		txtMobile.setText ("");
		txtNo.requestFocus ();
	}

	class CameraPanel extends JPanel
	{
		CameraPanel(){}

		public void paint(Graphics g)
		{
			EtchedBorder border=new EtchedBorder(EtchedBorder.LOWERED,Color.gray ,Color.lightGray );
			Font f=new Font("Times New Roman",Font.BOLD,30);
			g.setFont(f);
			border.paintBorder(this, g, 0, 0,getWidth(),getHeight());
			g.drawString("WebCam",50,120);
		}
	}

}