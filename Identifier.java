import javax.swing.*;

public class Identifier
{
	public static void main(String args[])
   {
	  JDesktopPane desktop = new JDesktopPane ();
 	  desktop.putClientProperty ("JDesktopPane.dragMode", "outline");

 	  RecogFace f=new RecogFace("User Identify",desktop);
 	  f.setVisible(true);
   }
}