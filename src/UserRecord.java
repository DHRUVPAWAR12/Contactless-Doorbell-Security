import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class UserRecord extends JFrame
{
	private JPanel jpDep = new JPanel();
	private JLabel lbNo, lbName, lbDate, lbMobile,lbEmail,lbBuilding,lbAddress,lbUserTemp;
	private JTextField txtNo, txtName,txtDate, txtMobile,txtEmail,txtBuilding,txtAddress,txtUserTemp;
	boolean found = false;

	private int recCount = 0;
	private int rows = 0;
	private	int total = 0;

	private String records[][] = new String [500][9];

	private FileInputStream fis;
	private DataInputStream dis;
	String acno;

	UserRecord (String rs)
	{
		super ("User Identifier");
		acno=rs;
		setResizable(false);
		setSize (335, 350);

  	     Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
	     setLocation((d.width- 335)/2, (d.height- 310)/2);

	     addWindowListener(new WindowAdapter()
         {
		    public void windowClosing(WindowEvent e)
		   {
			    System.exit(0);
		   	    dispose();
		    }
         });

	     jpDep.setLayout (null);

		lbNo = new JLabel ("Flat No:");
		lbNo.setForeground (Color.black);
		lbNo.setBounds (15, 20, 80, 25);

		lbName = new JLabel ("User Name:");
		lbName.setForeground (Color.black);
	    lbName.setBounds (15, 55, 85, 25);

		lbDate = new JLabel ("Date of Birth:");
		lbDate.setForeground (Color.black);
		lbDate.setBounds (15, 90, 80, 25);

		lbMobile = new JLabel ("Mobile No:");
		lbMobile.setForeground (Color.black);
		lbMobile.setBounds (15, 125, 80, 25);

		lbEmail = new JLabel ("Email Id:");
		lbEmail.setForeground (Color.black);
		lbEmail.setBounds (15, 160, 80, 25);

		lbBuilding = new JLabel ("Building:");
		lbBuilding.setForeground (Color.black);
		lbBuilding.setBounds (15, 195, 80, 25);

		lbAddress = new JLabel ("Address:");
		lbAddress.setForeground (Color.black);
		lbAddress.setBounds (15, 230, 80, 25);

		lbUserTemp = new JLabel ("Temperture:");
		lbUserTemp.setForeground (Color.black);
		lbUserTemp.setBounds (15, 265, 80, 25);

		txtNo = new JTextField ();
		txtNo.setForeground (Color.RED);
		txtNo.setEnabled (false);
		txtNo.setBounds (105, 20, 205, 25);

		txtName = new JTextField ();
		txtName.setEnabled (false);
		txtName.setBounds (105, 55, 205, 25);

		txtDate = new JTextField ();
		txtDate.setEnabled (false);
		txtDate.setBounds (105, 90, 205, 25);
		
		txtMobile = new JTextField ();
		txtMobile.setEnabled (false);
		txtMobile.setBounds (105, 125, 205, 25);

		txtEmail = new JTextField ();
		txtEmail.setEnabled (false);
		txtEmail.setBounds (105, 160, 205, 25);
		
		txtBuilding = new JTextField ();
		txtBuilding.setEnabled (false);
		txtBuilding.setBounds (105, 195, 205, 25);

		txtAddress = new JTextField ();
		txtAddress.setEnabled (false);
		txtAddress.setBounds (105, 230, 205, 25);

		txtUserTemp = new JTextField ();
		txtUserTemp.setEnabled (false);
		txtUserTemp.setBounds (105, 265, 205, 25);

		jpDep.add (lbNo);
		jpDep.add (txtNo);

		jpDep.add (lbName);
		jpDep.add (txtName);

		jpDep.add (lbDate);
		jpDep.add (txtDate);

		jpDep.add (lbMobile);
		jpDep.add (txtMobile);

		jpDep.add (lbEmail);
		jpDep.add (txtEmail);

		jpDep.add (lbBuilding);
		jpDep.add (txtBuilding);

		jpDep.add (lbAddress);
		jpDep.add (txtAddress);

        jpDep.add (lbUserTemp);
		jpDep.add (txtUserTemp);

		getContentPane().add (jpDep);

		populateArray ();	//Load All Existing Records in Memory.

		for (int x = 0; x < total; x++)
		{
			if (records[x][6].equals (acno.replace(".jpg","")))
			{
				found = true;
				showRec(x);
				break;
			}
		}
		if (found == false)
		{
			JOptionPane.showMessageDialog (this, "Not a Registered User !!! ","Wrong User", JOptionPane.PLAIN_MESSAGE);
		  	setVisible (false);
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
		}
	}

	public void showRec (int intRec)
	{
		try
		{
	     	txtNo.setText (records[intRec][0]);
	     	txtName.setText (records[intRec][1]);
	    	txtDate.setText (records[intRec][2]+" - "+records[intRec][3]+" , "+records[intRec][4]);
	    	txtMobile.setText(records[intRec][5]);
	    	txtEmail.setText(records[intRec][6]);
	    	txtBuilding.setText(records[intRec][7]);
	    	txtAddress.setText(records[intRec][8]);
            txtUserTemp.setText(RecogFace.temparture);
       }
       catch(Exception e){System.out.println(e);}
	}
	}