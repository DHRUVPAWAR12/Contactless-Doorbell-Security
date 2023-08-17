import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DeleteUser extends JFrame implements ActionListener
{
	private JPanel jpDel = new JPanel();
	private JLabel lbNo, lbName, lbDate, lbMobNo,lbEmail,lbBulidName,lbAddress;
	private JTextField txtNo, txtName, txtDate, txtMobNo,txtEmail,txtBuildingName,txtAddress;
	private JButton btnDel, btnCancel;

	private int recCount = 0;
	private int rows = 0;
	private	int total = 0;

	private String records[][] = new String [500][9];

	private FileInputStream fis;
	private DataInputStream dis;
	static boolean found = false;


	DeleteUser ()
	{
		super ("Delete User Record");
		setSize (335, 330);
		setResizable(false);
		setLocationRelativeTo(null);

		jpDel.setLayout (null);

		lbEmail = new JLabel ("Email Id:");
		lbEmail.setForeground (Color.black);
		lbEmail.setBounds (15, 20, 80, 25);		

        lbName = new JLabel ("User Name:");
		lbName.setForeground (Color.black);
        lbName.setBounds (15, 55, 90, 25);

		lbDate = new JLabel ("Date Of Birth:");
		lbDate.setForeground (Color.black);
		lbDate.setBounds (15, 90, 100, 25);
		
		lbMobNo = new JLabel ("Mobile No:");
		lbMobNo.setForeground (Color.black);
		lbMobNo.setBounds (15, 125, 80, 25);

		lbNo = new JLabel ("Flat No:");
		lbNo.setForeground (Color.black);
		lbNo.setBounds (15, 160, 80, 25);
		
		lbBulidName = new JLabel ("Society:");
		lbBulidName.setForeground (Color.black);
		lbBulidName.setBounds (15, 195, 80, 25);

		lbAddress = new JLabel ("Address:");
		lbAddress.setForeground (Color.black);
		lbAddress.setBounds (15,230, 80, 25);

		txtEmail = new JTextField ();
		txtEmail.setBounds (125, 20, 180, 25);

		txtName = new JTextField ();
		txtName.setEnabled (false);
		txtName.setBounds (125, 55, 180, 25);

		txtDate = new JTextField ();
		txtDate.setEnabled (false);
		txtDate.setBounds (125, 90, 180, 25);

		txtMobNo = new JTextField ();
		txtMobNo.setEnabled (false);
		txtMobNo.setBounds (125, 125, 180, 25);

		txtNo = new JTextField ();
		txtNo.setEnabled (false);
		txtNo.setHorizontalAlignment (JTextField.RIGHT);
		txtNo.setBounds (125, 160, 180, 25);

		txtBuildingName = new JTextField ();
		txtBuildingName.setEnabled (false);
		txtBuildingName.setBounds (125, 195, 180, 25);

		txtAddress = new JTextField ();
		txtAddress.setEnabled (false);
		txtAddress.setBounds (125, 230, 180, 25);

		btnDel = new JButton ("Delete");
		btnDel.setBounds (20, 265, 120, 25);
		btnDel.addActionListener (this);

		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (180, 265, 120, 25);
		btnCancel.addActionListener (this);

		jpDel.add (lbNo);
		jpDel.add (txtNo);
		
		jpDel.add (lbName);
		jpDel.add (txtName);
		
		jpDel.add (lbDate);
		jpDel.add (lbBulidName);
		jpDel.add (lbAddress);
		jpDel.add (lbDate);
		jpDel.add (txtDate);
		jpDel.add (lbMobNo);
		jpDel.add (lbEmail);
		jpDel.add (txtEmail);
		jpDel.add (txtMobNo);
		jpDel.add (txtBuildingName);
		jpDel.add (txtAddress);
		jpDel.add (btnDel);
		jpDel.add (btnCancel);


		txtEmail.addFocusListener (new FocusListener ()
		{
			public void focusGained (FocusEvent e) { }
			public void focusLost (FocusEvent fe)
			{
				if (txtEmail.getText().equals ("")) { }
				else
				{
					rows = 0;
					populateArray ();	//Load All Existing Records in Memory.
				}
			}
		}
		);

		getContentPane().add (jpDel);

		populateArray ();	//Load All Existing Records in Memory.

		setVisible (true);
	}

	public void actionPerformed (ActionEvent ae)
	{
		Object obj = ae.getSource();

		if (obj == btnDel)
		{
			if (txtEmail.getText().equals(""))
			{
				JOptionPane.showMessageDialog (this, "Please! Provide User EMail.","UserSystem - EmptyField", JOptionPane.PLAIN_MESSAGE);
				txtEmail.requestFocus();
			}
			else
			{
				findRec ();		//Finding if Account No. Already Exist or Not
				
				if(found==true)
   				deletion ();	//Confirm Deletion of Current Record.
			}
		}
		if (obj == btnCancel)
		{
			dispose();
		}
	}

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
			if (total == 0)
			{
				JOptionPane.showMessageDialog (null, "Records File is Empty.\nEnter Records First to Display.","UserSystem - EmptyFile", JOptionPane.PLAIN_MESSAGE);
				dispose();
			}
		}
	}

	void findRec ()
	{
		for (int x = 0; x < total; x++)
		{
			if (records[x][6].equals (txtEmail.getText()))
			{
				found = true;
				showRec (x);
				break;
			}
		}
		if (found == false)
		{
			String str = txtNo.getText ();
			JOptionPane.showMessageDialog (this, "User No. " + str + " doesn't Exist.","UserSystem - WrongNo", JOptionPane.PLAIN_MESSAGE);
		}
	}

	void showRec (int intRec)
	{
		txtNo.setText (records[intRec][0]);
		txtName.setText (records[intRec][1]);
		txtDate.setText (records[intRec][2] + ", " + records[intRec][3] + ", " + records[intRec][4]);
		txtMobNo.setText (records[intRec][5]);
        txtEmail.setText (records[intRec][6]);
		txtBuildingName.setText (records[intRec][7]);
		txtAddress.setText (records[intRec][8]);
		recCount = intRec;
	}

	void deletion ()
	{
		try
		{
		    	int reply = JOptionPane.showConfirmDialog (this,"Are you Sure you want to Delete\n" + txtName.getText () + " Record From UserSystem?","User System - Delete", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (reply == JOptionPane.YES_OPTION)
				{
					delRec ();	//Delete the Selected Contents of Array.
				}
		}
		catch (Exception e) {}
	}

	void delRec ()
	{
		try
		{
			if (records != null)
			{
				for(int i = recCount; i < total; i++)
				{
					for (int r = 0; r < 9; r++)
					{
						records[i][r] = records[i+1][r];
						if (records[i][r] == null) break;
					}
				}
				total = total - 1;
				deleteFile ();
			}
		}
		catch (ArrayIndexOutOfBoundsException ex) { }
	}

	void deleteFile ()
	{
		try
		{
			FileOutputStream fos = new FileOutputStream ("User.dat");
			DataOutputStream dos = new DataOutputStream (fos);
			if (records != null)
			{
				for (int i = 0; i < total; i++)
				{
					for (int r = 0; r < 9; r++)
					{
						dos.writeUTF (records[i][r]);
						if (records[i][r] == null) break;
					}
				}
				new File("./UserData/"+txtEmail.getText()+".jpg").delete();
				//System.out.println("./UserData/"+txtNo.getText());
				JOptionPane.showMessageDialog (this, "Record has been Deleted Successfuly.","UserSystem - Record Deleted", JOptionPane.PLAIN_MESSAGE);
				dispose();
			}
			else { }
			dos.close();
			fos.close();
		}
		catch (IOException ioe)
		{
			JOptionPane.showMessageDialog (this, "There are Some Problem with File","UserSystem - Problem", JOptionPane.PLAIN_MESSAGE);
		}
	}
}