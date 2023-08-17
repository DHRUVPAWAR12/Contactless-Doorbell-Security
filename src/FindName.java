import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;

public class FindName extends JFrame implements ActionListener
{
	private JPanel jpFind = new JPanel();
	private JLabel lbNo, lbName, lbDate, lbMobile,lbEmail,lbBuildling,lbAddress;
	private JTextField txtNo, txtName, txtDate, txtMobile,txtEmail,txtBuilding,txtAddress;
	private JButton btnFind, btnCancel;

	private int count = 0;
	private int rows = 0;
	private	int total = 0;

	//String Type Array use to Load Records From File.
	private String records[][] = new String [500][9];

	private FileInputStream fis;
	private DataInputStream dis;

	FindName ()
	{
		//super(Title, Resizable, Closable, Maximizable, Iconifiable)
		super ("Search User [By Name]");
		setSize (350, 330);
		setResizable(false);
		setLocationRelativeTo(null);

		jpFind.setLayout (null);

		lbNo = new JLabel ("User No:");
		lbNo.setForeground (Color.black);
		lbNo.setBounds (15, 20, 80, 25);

        lbName = new JLabel ("User Name:");
		lbName.setForeground (Color.black);
        lbName.setBounds (15, 55, 80, 25);

		lbDate = new JLabel ("Date of Birth:");
		lbDate.setForeground (Color.black);
		lbDate.setBounds (15, 90, 100, 25);

		lbMobile = new JLabel ("Mobile No:");
		lbMobile.setForeground (Color.black);
		lbMobile.setBounds (15, 125, 80, 25);

		lbEmail = new JLabel ("Email Id:");
		lbEmail.setForeground (Color.black);
		lbEmail.setBounds (15, 160, 80, 25);
		
		lbBuildling = new JLabel ("Building");
		lbBuildling.setForeground (Color.black);
		lbBuildling.setBounds (15, 195, 80, 25);

		lbAddress = new JLabel ("Address:");
		lbAddress.setForeground (Color.black);
		lbAddress.setBounds (15, 230, 80, 25);

		txtNo = new JTextField ();
		txtNo.setEnabled (false);
		txtNo.setBounds (125, 20, 200, 25);

		txtName = new JTextField ();
		txtName.setBounds (125, 55, 200, 25);

		txtDate = new JTextField ();
		txtDate.setEnabled (false);
		txtDate.setBounds (125, 90, 200, 25);

		txtMobile = new JTextField ();
		txtMobile.setEnabled (false);
		txtMobile.setBounds (125, 125, 200, 25);

		txtEmail = new JTextField ();
		txtEmail.setEnabled (false);
		txtEmail.setBounds (125, 160, 200, 25);
		
		txtBuilding = new JTextField ();
		txtBuilding.setEnabled (false);
		txtBuilding.setBounds (125, 195, 200, 25);

		txtAddress = new JTextField ();
		txtAddress.setEnabled (false);
		txtAddress.setBounds (125, 230, 200, 25);

		btnFind = new JButton ("Search");
		btnFind.setBounds (20, 265, 120, 25);
		btnFind.addActionListener (this);

		btnCancel = new JButton ("Cancel");
		btnCancel.setBounds (200,265, 120, 25);
		btnCancel.addActionListener (this);

		//Adding the All the Controls to Panel.
		jpFind.add (lbNo);
		jpFind.add (txtNo);
		jpFind.add (lbName);
		jpFind.add (txtName);
		jpFind.add (lbDate);
		jpFind.add (txtDate);
		jpFind.add (txtBuilding);
		jpFind.add (txtAddress);
		jpFind.add (lbBuildling);
		jpFind.add (lbAddress);
		jpFind.add (lbMobile);
		jpFind.add (lbEmail);
		jpFind.add (txtMobile);
		jpFind.add (txtEmail);
		jpFind.add (btnFind);
		jpFind.add (btnCancel);

		//Adding Panel to Window.
		getContentPane().add (jpFind);

		populateArray ();	//Load All Existing Records in Memory.

		//In the End Showing the New Account Window.
		setVisible (true);

	}

	//Function use By Buttons of Window to Perform Action as User Click Them.
	public void actionPerformed (ActionEvent ae)
	{
		Object obj = ae.getSource();

		if (obj == btnFind)
		{
			if (txtName.getText().equals ("")) {
				JOptionPane.showMessageDialog (this, "Please! Provide User Name to Search.","UserSystem - EmptyField", JOptionPane.PLAIN_MESSAGE);
				txtName.requestFocus();
			}
			else
			{
				rows = 0;
				populateArray ();	//Load All Existing Records in Memory.
				findRec ();		//Finding if Account No. Exist or Not.
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
			}
			else
			{
				try
				{
					dis.close();
					fis.close();
				}
				catch (Exception exp) { }
			}
		}
	}

	void findRec ()
	{
		boolean found = false;
		for (int x = 0; x < total; x++)
		{
			if (records[x][1].equalsIgnoreCase (txtName.getText()))
			{
				found = true;
				showRec (x);
				break;
			}
		}
		if (found == false)
		{
			JOptionPane.showMessageDialog (this, "User " + txtName.getText () + " doesn't Exist.","UserSystem - WrongNo", JOptionPane.PLAIN_MESSAGE);
			dispose();
		}
	}

	public void showRec (int intRec)
	{
		txtNo.setText (records[intRec][0]);
		txtName.setText (records[intRec][1]);
		txtDate.setText (records[intRec][2] + ", " + records[intRec][3] + ", " + records[intRec][4]);
		txtMobile.setText (records[intRec][5]);
		txtEmail.setText (records[intRec][6]);
		txtBuilding.setText (records[intRec][7]);
		txtAddress.setText (records[intRec][8]);
	}
}