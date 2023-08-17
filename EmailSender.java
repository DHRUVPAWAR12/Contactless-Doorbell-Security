import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender
{
  public static void mailsent(String emailid,String key)
  {
	final String username = "projectsystem21@gmail.com";
		final String password = "ujhrlmnhbgbosgyk";
	
		final String to = emailid;

	Properties props = new Properties();
  	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");
	props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

	Session session = Session.getInstance(props,new javax.mail.Authenticator() 
	{
	  protected PasswordAuthentication getPasswordAuthentication() 
	  {
		return new PasswordAuthentication(username, password);
	  }
	});

	try 
	{
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username));
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		message.setSubject("OTP For Registration !!!");
		message.setText("Dear User,"+ "\n\n Your Security OTP Key is:   "+key);
		Transport.send(message);
		System.out.println("Email Send");
	} 
	catch (MessagingException e) 
	{
		throw new RuntimeException(e);
	}
  }
 }	