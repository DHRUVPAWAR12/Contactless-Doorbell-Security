import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class ViewOneUser extends JFrame implements ActionListener
{
	private JPanel jpRec = new JPanel();
	private JLabel lbNo, lbName, lbDate, lbMob,lbEmail,lbSociety,lbAddress;
	private JTextField txtNo, txtName, txtDate, txtMobile,txtEmail,txtBuilding,txtAddress,txtRec;
	private JButton btnFirst, btnBack, btnNext, btnLast;

	private int recCount = 0;
	private int rows = 0;
	private	int total = 0;

	//String Type Array use to Load Records From File.
	private String records[][] = new String [500][9];

	private FileInputStream fis;
	private DataInputStream dis;

	ViewOneUser ()
	{
		super ("User Indivisual Detail");
		setSize (350, 340);
		setResizable(false);
		setLocationRelativeTo(null);

		jpRec.setLayout (null);

		lbNo = new JLabel ("Flat No:");
		lbNo.setForeground (Color.black);
		lbNo.setBounds (15, 20, 80, 25);

        lbName = new JLabel ("User Name:");
		lbName.setForeground (Color.black);
        lbName.setBounds (15, 55, 80, 25);

		lbDate = new JLabel ("Date of Birth:");
		lbDate.setForeground (Color.black);
		lbDate.setBounds (15, 90, 100, 25);

		lbMob = new JLabel ("Mobile No:");
		lbMob.setForeground (Color.black);
		lbMob.setBounds (15, 125, 80, 25);

		lbEmail = new JLabel ("Email Id:");
		lbEmail.setForeground (Color.black);
		lbEmail.setBounds (15, 160, 80, 25);

		lbAddress = new JLabel ("Building:");
		lbAddress.setForeground (Color.black);
		lbAddress.setBounds (15, 195, 80, 25);

		lbSociety = new JLabel ("Address:");
		lbSociety.setForeground (Color.black);
		lbSociety.setBounds (15, 230, 80, 25);

		txtNo = new JTextField ();
		txtNo.setEnabled (false);
		txtNo.setBounds (125, 20, 200, 25);

		txtName = new JTextField ();
		txtName.setEnabled (false);
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

		txtAddress = new JTextField ();
		txtAddress.setEnabled (false);
		txtAddress.setBounds (125, 195, 200, 25);

		txtBuilding = new JTextField ();
		txtBuilding.setEnabled (false);
		txtBuilding.setBounds (125, 230, 200, 25);

		//Aligninig The Navigation Buttons.
		btnFirst = new JButton ("<<");
		btnFirst.setBounds (15, 265, 50, 25);
		btnFirst.addActionListener (this);

		btnBack = new JButton ("<");
		btnBack.setBounds (65, 265, 50, 25);
		btnBack.addActionListener (this);

		btnNext = new JButton (">");
		btnNext.setBounds (225, 265, 50, 25);
		btnNext.addActionListener (this);

		btnLast = new JButton (">>");
		btnLast.setBounds (275, 265, 50, 25);
		btnLast.addActionListener (this);

		txtRec = new JTextField ();
		txtRec.setEnabled (false);
		txtRec.setHorizontalAlignment (JTextField.CENTER);
		txtRec.setBounds (130, 265, 70, 25);

		//Adding the All the Controls to Panel.
		jpRec.add (lbNo);
		jpRec.add (txtNo);
		jpRec.add (lbName);
		jpRec.add (txtName);
		jpRec.add (lbDate);
		jpRec.add (txtDate);
		jpRec.add (txtAddress);
		jpRec.add (txtBuilding);
		jpRec.add (lbAddress);
		jpRec.add (lbSociety);
		jpRec.add (lbMob);
		jpRec.add (lbEmail);
		jpRec.add (txtMobile);
		jpRec.add (txtEmail);
		jpRec.add (btnFirst);
		jpRec.add (btnBack);
		jpRec.add (btnNext);
		jpRec.add (btnLast);
		jpRec.add (txtRec);

		getContentPane().add (jpRec);

		populateArray ();
		showRec (0);
		setVisible (true);
	}

	public void actionPerformed (ActionEvent ae)
	{
		Object obj = ae.getSource();

		if (obj == btnFirst)
		{
			recCount = 0;
			showRec (recCount);
		}
		else if (obj == btnBack)
		{
			recCount = recCount - 1;
			if (recCount < 0)
			{
				recCount = 0;
				showRec (recCount);
				JOptionPane.showMessageDialog (this, "You are on First Record.","UserSystem - 1st Record", JOptionPane.PLAIN_MESSAGE);
			}
			else
			{
				showRec (recCount);
			}
		}
		else if (obj == btnNext)
		{
			recCount = recCount + 1;
			if (recCount == total)
			{
				recCount = total - 1;
				showRec (recCount);
				JOptionPane.showMessageDialog (this, "You are on Last Record.","UserSystem - End of Records", JOptionPane.PLAIN_MESSAGE);
			}
			else
			{
				showRec (recCount);
			}
		}
		else if (obj == btnLast)
		{
			recCount = total - 1;
			showRec (recCount);
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
		}
	}

	//Function which display Record from Array onto the Form.
	public void showRec (int intRec)
	{
		txtNo.setText (records[intRec][0]);
		txtName.setText (records[intRec][1]);
		txtDate.setText (records[intRec][2] + ", " + records[intRec][3] + ", " + records[intRec][4]);
		txtMobile.setText (records[intRec][5]);
		txtEmail.setText (records[intRec][6]);
		txtAddress.setText (records[intRec][7]);
		txtBuilding.setText (records[intRec][8]);

		if (total == 0)
		{
			txtRec.setText (intRec + "/" + total); //Showing Record No. and Total Records.
			txtDate.setText ("");
		}
		else
		{
			txtRec.setText ((intRec + 1) + "/" + total); //Showing Record No. and Total Records.
		}
	}
}