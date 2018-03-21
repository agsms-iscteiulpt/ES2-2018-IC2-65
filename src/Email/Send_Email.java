package Email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class Send_Email {
	public Send_Email(String email, String subject, String text) {
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
					InternetAddress.parse("mafalda.ambc@gmail.com"));
			message.setSubject(subject);
			message.setText("Esta mensagem foi enviada pelo utilizador com o email: " + email + "\n" + text);

			Transport.send(message);
			
//			JOptionPane.showMessageDialog(null, "E-mail successfully sent!");

//			System.out.println("Email sent!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mafalda.ambc@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText("Enviou a seguinte mensagem para o administrador da plataforma de otimização: " + "\n" + text);

			Transport.send(message);
			
			JOptionPane.showMessageDialog(null, "E-mail successfully sent!");

			System.out.println("Email sent!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
}