package Email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Send_Email {
	public Send_Email() {
		final String username = "mafalda.ambc@gmail.com";
		final String password = "mafalda1996";
		
		Properties props = new Properties();
		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password); //username and password
				}
			});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mafalda.ambc@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("mambc@iscte-iul.pt"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail, \n\n this is just a test! " +
					"\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}