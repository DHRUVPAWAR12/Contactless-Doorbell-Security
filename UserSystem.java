import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.awt.PrintJob.*;

public class UserSystem extends JFrame implements ActionListener,Runnable//,ItemListener
{
	private JDesktopPane desktop = new JDesktopPane ();

	private JMenuBar bar;

	private JMenu mnuFile, mnuEdit, mnuView,mnuHelp;

	private JMenuItem addNew, printRec, end;				//File Menu Options.
	private	JMenuItem  withdraw, delRec, search, searchName;	//Edit Menu Options.
	private	JMenuItem oneByOne, allCustomer;				//View Menu Options.
	private	JMenuItem change, style, theme;					//Option Menu Options.
	private	JMenuItem content, keyHelp, about;				//Help Menu Options.

	private JPopupMenu popMenu = new JPopupMenu ();

	private JMenuItem open, report, with, del, find, all;

	private	JToolBar toolBar;

	private	JButton btnNew, btnWith, btnRec, btnDel, btnSrch, btnHelp, btnKey;

	private JPanel statusBar = new JPanel ();

	private JLabel welcome;
	private JLabel author;

	private java.util.Date currDate = new java.util.Date ();
	private SimpleDateFormat sdf = new SimpleDateFormat ("dd MMMM yyyy", Locale.getDefault());
	private String d = sdf.format (currDate);

	private int count = 0;
	private int rows = 0;
	private	int total = 0;

	private String records[][] = new String [500][8];

	private FileInputStream fis;
	private DataInputStream dis;

	public UserSystem (int i){}
	public void run(){}

	public UserSystem ()
	{
		super ("UserDetector [Pvt] Limited.");
//		setResizable(false);

		bar = new JMenuBar ();

		setIconImage (getToolkit().getImage ("Images/User.gif"));
		setLocation(80,40);
		setSize (Toolkit.getDefaultToolkit().getScreenSize().width-150,Toolkit.getDefaultToolkit().getScreenSize().height-120);
		setJMenuBar (bar);

		addWindowListener (new WindowAdapter ()
		{
			public void windowClosing (WindowEvent we)
			{
				quitApp ();
			}
		}
		);

		mnuFile = new JMenu ("File");
		mnuFile.setMnemonic ((int)'F');
		mnuEdit = new JMenu ("Edit");
		mnuEdit.setMnemonic ((int)'E');
		mnuView = new JMenu ("View");
		mnuView.setMnemonic ((int)'V');
		mnuHelp = new JMenu ("Help");
		mnuHelp.setMnemonic ((int)'H');

		addNew = new JMenuItem ("User Registration", new ImageIcon ("Images/Open.gif"));
		addNew.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		addNew.setMnemonic ((int)'N');
		addNew.addActionListener (this);

		printRec = new JMenuItem ("Print User Detail", new ImageIcon ("Images/New.gif"));
		printRec.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
		printRec.setMnemonic ((int)'R');
		printRec.addActionListener (this);

		end = new JMenuItem ("Quit System ?", new ImageIcon ("Images/export.gif"));
		end.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
		end.setMnemonic ((int)'Q');
		end.addActionListener (this);

		delRec = new JMenuItem ("Delete User", new ImageIcon ("Images/Delete.gif"));
		delRec.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
		delRec.setMnemonic ((int)'D');
		delRec.addActionListener (this);

		search = new JMenuItem ("Search By EMail.", new ImageIcon ("Images/find.gif"));
		search.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		search.setMnemonic ((int)'S');
		search.addActionListener (this);

		searchName = new JMenuItem ("Search By Name");
		searchName.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_M, Event.CTRL_MASK));
		searchName.setMnemonic ((int)'M');
		searchName.addActionListener (this);

		oneByOne = new JMenuItem ("View One By One");
		oneByOne.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		oneByOne.setMnemonic ((int)'O');
		oneByOne.addActionListener (this);

		allCustomer = new JMenuItem ("View All User", new ImageIcon ("Images/refresh.gif"));
		allCustomer.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
		allCustomer.setMnemonic ((int)'A');
		allCustomer.addActionListener (this);

		about = new JMenuItem ("About UserSystem", new ImageIcon ("Images/Save.gif"));
		about.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
		about.setMnemonic ((int)'C');
		about.addActionListener (this);

		mnuFile.add (addNew);
		mnuFile.addSeparator ();
		mnuFile.add (printRec);
		mnuFile.addSeparator ();
		mnuFile.add (end);

		mnuEdit.addSeparator ();
		mnuEdit.add (delRec);
		mnuEdit.addSeparator ();
		mnuEdit.add (search);
		mnuEdit.add (searchName);

		mnuView.add (oneByOne);
		mnuView.addSeparator ();
		mnuView.add (allCustomer);

		mnuHelp.addSeparator ();
		mnuHelp.add (about);

		bar.add (mnuFile);
		bar.add (mnuEdit);
		bar.add (mnuView);
		bar.add (mnuHelp);

		open = new JMenuItem ("User Registration", new ImageIcon ("Images/Open.gif"));
		open.addActionListener (this);

		report = new JMenuItem ("Print User Report", new ImageIcon ("Images/New.gif"));
		report.addActionListener (this);

		del = new JMenuItem ("Delete User", new ImageIcon ("Images/Delete.gif"));
		del.addActionListener (this);

		find = new JMenuItem ("Search User", new ImageIcon ("Images/find.gif"));
		find.addActionListener (this);

		all = new JMenuItem ("View All User", new ImageIcon ("Images/refresh.gif"));
		all.addActionListener (this);

		popMenu.add (open);
		popMenu.add (report);
		popMenu.add (del);
		popMenu.add (find);
		popMenu.add (all);

		addMouseListener (new MouseAdapter ()
		{
			public void mousePressed (MouseEvent me) { checkMouseTrigger (me); }
			public void mouseReleased (MouseEvent me) { checkMouseTrigger (me); }
			private void checkMouseTrigger (MouseEvent me)
			{
				if (me.isPopupTrigger ())
					popMenu.show (me.getComponent (), me.getX (), me.getY ());
			}
		}
		);

		btnNew = new JButton (new ImageIcon ("Images/NotePad.gif"));
		btnNew.setToolTipText ("User Registration");
		btnNew.addActionListener (this);

		btnRec = new JButton (new ImageIcon ("Images/Paproll.gif"));
		btnRec.setToolTipText ("Print User Record");
		btnRec.addActionListener (this);

		btnDel = new JButton (new ImageIcon ("Images/Toaster.gif"));
		btnDel.setToolTipText ("Delete User");
		btnDel.addActionListener (this);

		btnSrch = new JButton (new ImageIcon ("Images/Search.gif"));
		btnSrch.setToolTipText ("Search User");
		btnSrch.addActionListener (this);

		toolBar = new JToolBar ();
		toolBar.add (btnNew);
		toolBar.addSeparator ();
		toolBar.addSeparator ();
		toolBar.add (btnRec);
		toolBar.addSeparator ();
		toolBar.add (btnDel);
		toolBar.addSeparator ();
		toolBar.add (btnSrch);
		toolBar.addSeparator ();


 	   //JPanel panel = (JPanel)this.getContentPane();
		JPanel panel = new JPanel ();
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("Images/small.jpg"));// your image here
        panel.add(label);

		author = new JLabel (" " + "UserSystem [Pvt] Limited.", Label.LEFT);
		author.setForeground (Color.black);
		author.setToolTipText ("Program's Title");

		welcome = new JLabel ("Welcome Today is " + d + " ", JLabel.RIGHT);
		welcome.setForeground (Color.black);
		welcome.setToolTipText ("Welcoming the User & System Current Date");

		statusBar.setLayout (new BorderLayout());
		statusBar.add (author, BorderLayout.WEST);
		statusBar.add (welcome, BorderLayout.EAST);

		desktop.putClientProperty ("JDesktopPane.dragMode", "outline");

		getContentPane().add (toolBar, BorderLayout.NORTH);
		getContentPane().add (desktop, BorderLayout.CENTER);
		getContentPane().add (statusBar, BorderLayout.SOUTH);
		getContentPane().add (panel, BorderLayout.WEST);

		setVisible (true);

	}

	public void actionPerformed (ActionEvent ae)
	{

		Object obj = ae.getSource();

		if (obj == addNew || obj == open || obj == btnNew)
		{
			boolean b = openChildWindow ("User Registration");
			if (b == false)
			{
				NewAccountRegister newAcc = new NewAccountRegister();
				newAcc.setVisible(true);
			}

		}
		else if (obj == printRec || obj == btnRec || obj == report)
		{
			getEmailId ();
		}
		else if (obj == end)
		{
			quitApp ();
		}
     	else if (obj == delRec || obj == del || obj == btnDel)
		{
			boolean b = openChildWindow ("Delete User");
			if (b == false)
			{
				DeleteUser delCus = new DeleteUser ();
				delCus.setVisible(true);
			}
		}

		else if (obj == search || obj == find || obj == btnSrch)
		{
			boolean b = openChildWindow ("Search User [By No.]");
			if (b == false)
			{
				FindAccount fndAcc = new FindAccount ();
				fndAcc.setVisible(true);
			}
		}
		else if (obj == searchName)
		{
			boolean b = openChildWindow ("Search User [By Name]");
			if (b == false)
			{
				FindName fndNm = new FindName ();
				fndNm.setVisible(true);
			}
		}
		else if (obj == oneByOne)
		{
			boolean b = openChildWindow ("View User");
			if (b == false)
			{
				ViewOneUser vwOne = new ViewOneUser ();
				vwOne.setVisible(true);
			}
		}
		else if (obj == allCustomer || obj == all)
		{
			boolean b = openChildWindow ("View All Users");
			if (b == false)
			{
				ViewALLUser viewCus = new ViewALLUser ();
				viewCus.setVisible(true);
			}
		}
		else if (obj == about)
		{
//			String msg = "UserSystem [Pvt] Limited.\n\n" + "Created & Designed By:\n" +"Student_1 \n\n" + "Student_2 \n\n"+"Student_3";
//			JOptionPane.showMessageDialog (this, msg, "About UserSystem", JOptionPane.PLAIN_MESSAGE);
		 AboutFrame af=new AboutFrame();
		}
	}

	private void quitApp ()
	{
		try
		{
		    	int reply = JOptionPane.showConfirmDialog (this,"Are you really want to exit\nFrom UserSystem?","UserSystem - Exit", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (reply == JOptionPane.YES_OPTION)
				{
				setVisible (false);	//Hide the Frame.

				AudioClip ac = Applet.newAudioClip(new File("Images/Good_Bye.wav").toURL());
				ac.play();

				UserSystem b=new UserSystem(0);
				Thread t=new Thread(b);
				t.sleep(1000);
				System.exit (0);        //Close the Application.
				}
				else if (reply == JOptionPane.NO_OPTION)
				{
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			   }
		 }
   	    catch (Exception e) {}
     }

	private boolean openChildWindow (String title)
	{
		JInternalFrame[] childs = desktop.getAllFrames ();
		for (int i = 0; i < childs.length; i++)
		{
			if (childs[i].getTitle().equalsIgnoreCase (title))
			{
				childs[i].show ();
				return true;
			}
		}
		return false;
	}

	void getEmailId ()
	{
     	String printing;
		rows = 0;
		boolean b = populateArray ();
		if (b == false) { }
		else
		{
			try
			{
				printing = JOptionPane.showInputDialog (this, "Enter Email Id. to Print User Record.\n" +
				"(Tip: Email Must be valid", "UserSystem - PrintRecord", JOptionPane.PLAIN_MESSAGE);
				if (printing == null) { }
				if (printing.equals ("")) {
					JOptionPane.showMessageDialog (this, "Provide Email. to Print.",
						 "UserSystem - EmptyField", JOptionPane.PLAIN_MESSAGE);
					getEmailId ();
				}
				else
				{
					findRec (printing);
				}
			}
			catch (Exception e) { }
		}
	}

	boolean populateArray ()
	{
		boolean b = true;
		try
		{
			fis = new FileInputStream ("User.dat");
			dis = new DataInputStream (fis);
			while (true)
			{
				for (int i = 0; i < 8; i++)
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
				JOptionPane.showMessageDialog (null, "Records File is Empty.\nEnter Records First to Display.",
					 "UserSystem - EmptyFile", JOptionPane.PLAIN_MESSAGE);
				b = false;
			}
		}
		return b;
	}

	void findRec (String rec)
	{
		boolean found = false;
		for (int x = 0; x < total; x++)
		{
			if (records[x][6].equals (rec))
			{
				found = true;
				printRecord (makeRecordPrint (x));
				break;
			}
		}
		if (found == false)
		{
			JOptionPane.showMessageDialog (this, "User Email. " + rec + " doesn't Exist.",
					 "UserSystem - WrongNo", JOptionPane.PLAIN_MESSAGE);
			getEmailId ();
		}

	}

	String makeRecordPrint (int rec)
	{
		String data;
		String data0 = "               UserSystem [Pvt] Limited.               \n";	//Page Title.
		String data1 = "               User Detail Report.              \n\n";	//Page Header.
		String data2 = "  User No.:       " + records[rec][0] + "\n";
		String data3 = "  User Name:     " + records[rec][1] + "\n";
		String data4 = "  Date Of Birth:  " + records[rec][2] + ", " + records[rec][3] + ", " + records[rec][4] + "\n";
		String data5 = "  Mobile No:   " + records[rec][5] + "\n";
		String data6 = "  Email:   " + records[rec][6] + "\n";
		String data7 = "  Building:   " + records[rec][7] + "\n";
		String sep0 = " -----------------------------------------------------------\n";
		String sep1 = " -----------------------------------------------------------\n";
		String sep2 = " -----------------------------------------------------------\n";
		String sep3 = " -----------------------------------------------------------\n";
		data = data0 + sep0 + data1 + data2 + sep1 + data3 + sep2 + data4 + sep3 + data5 + sep3 + data6 + sep3 + data7 + sep3;
		return data;
	}

	void printRecord (String rec)
	{
		StringReader sr = new StringReader (rec);
		LineNumberReader lnr = new LineNumberReader (sr);
		Font typeface = new Font ("Times New Roman", Font.PLAIN, 12);
		Properties p = new Properties ();
		PrintJob pJob = getToolkit().getPrintJob (this, "Print User Detail Report", p);

		if (pJob != null)
		{
			Graphics gr = pJob.getGraphics ();
			if (gr != null)
			{
				FontMetrics fm = gr.getFontMetrics (typeface);
				int margin = 20;
				int pageHeight = pJob.getPageDimension().height - margin;
    				int fontHeight = fm.getHeight();
	    			int fontDescent = fm.getDescent();
    				int curHeight = margin;
				String nextLine;
				gr.setFont (typeface);

				try
				{
					do
					{
						nextLine = lnr.readLine ();
						if (nextLine != null)
						{
							if ((curHeight + fontHeight) > pageHeight) {	//New Page.
								gr.dispose();
								gr = pJob.getGraphics ();
								curHeight = margin;
							}
							curHeight += fontHeight;
							if (gr != null) {
								gr.setFont (typeface);
								gr.drawString (nextLine, margin, curHeight - fontDescent);
							}
						}
					}
					while (nextLine != null);
				}
				catch (EOFException eof) { }
				catch (Throwable t) { }
			}
			gr.dispose();
		}
		if (pJob != null)
			pJob.end ();
	}
}