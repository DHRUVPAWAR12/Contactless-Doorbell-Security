import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;

public class ViewALLUser extends JFrame
{

	private JPanel jpShow = new JPanel ();

	private DefaultTableModel dtmCustomer;
	private JTable tbCustomer;
	private JScrollPane jspTable;

	private int row = 0;
	private int total = 0;

	private String rowData[][];

	private FileInputStream fis;
	private DataInputStream dis;

	ViewALLUser ()
	{
		super ("View All Users");
		setSize (500, 280);
		setResizable(false);
		setLocationRelativeTo(null);

		jpShow.setLayout (null);

		populateArray ();

		tbCustomer = makeTable ();
		jspTable = new JScrollPane (tbCustomer);
		jspTable.setBounds (8, 8, 475, 230);

		jpShow.add (jspTable);

		getContentPane().add (jpShow);

		setVisible (true);
	}

	void populateArray ()
	{
		String rows[][] = new String [500][9];
		try
		{
			fis = new FileInputStream ("User.dat");
			dis = new DataInputStream (fis);
			while (true)
			{
				for (int i = 0; i < 9; i++)
				{
					rows[row][i] = dis.readUTF ();
				}
				row++;
			}
		}
		catch (Exception ex)
		{
			total = row;
			rowData = new String [total][7];
			if (total == 0)
			{
				JOptionPane.showMessageDialog (null, "Records File is Empty.\nEnter Records to Display.","UserSystem - EmptyFile", JOptionPane.PLAIN_MESSAGE);
			}
			else
			{
				for (int i = 0; i < total; i++)
				{
					rowData[i][0] = rows[i][0];
					rowData[i][1] = rows[i][1];
					rowData[i][2] = rows[i][2] + ", " + rows[i][3] + ", " + rows[i][4];
					rowData[i][3] = rows[i][5];
					rowData[i][4] = rows[i][6];
					rowData[i][5] = rows[i][7];
					rowData[i][6] = rows[i][8];
	
				}
				try
				{
					dis.close();
					fis.close();
				}
				catch (Exception exp) { }
			}
		}
	}

	private JTable makeTable ()
	{
		//String Type Array use to Give Table Column Names.
		String cols[] = {"User No.", "User Name", "Date of Birth", "Mobile_No","Email Id","Society","Address"};

		dtmCustomer  = new DefaultTableModel (rowData, cols);
		tbCustomer = new JTable (dtmCustomer)
		{
			public boolean isCellEditable (int iRow, int iCol)
			{
				return false;	//Disable All Columns of Table.
			}
		};
		(tbCustomer.getColumnModel().getColumn(0)).setPreferredWidth (200);
		(tbCustomer.getColumnModel().getColumn(1)).setPreferredWidth (275);
		(tbCustomer.getColumnModel().getColumn(2)).setPreferredWidth (275);
		(tbCustomer.getColumnModel().getColumn(3)).setPreferredWidth (200);
		(tbCustomer.getColumnModel().getColumn(4)).setPreferredWidth (200);
		(tbCustomer.getColumnModel().getColumn(5)).setPreferredWidth (200);
		(tbCustomer.getColumnModel().getColumn(6)).setPreferredWidth (180);
		tbCustomer.setRowHeight (20);
		tbCustomer.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
		return tbCustomer;
	}
}