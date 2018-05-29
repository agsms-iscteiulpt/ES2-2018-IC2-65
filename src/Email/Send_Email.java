package Email;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mafalda.ambc@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText("Enviou a seguinte mensagem para o administrador da plataforma de otimizacao: " + "\n" + text);

			Transport.send(message);

			JOptionPane.showMessageDialog(null, "E-mail successfully sent!");

			System.out.println("Email sent!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public Send_Email(String email, String problem_type, String fileName, String filePath) {
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
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String subject = "Otimizacao em curso: " + problem_type + " " + dateFormat.format(date);

			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("mafalda.ambc@gmail.com"));
			InternetAddress[] address = {new InternetAddress(email)};
			msg.addRecipient(RecipientType.BCC, new InternetAddress("mafalda.ambc@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);

			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText("Muito obrigado por usar esta plataforma de otimizacao. Sera informado por email sobre o progresso do processo de otimizacao, quando o processo de otimizacao tiver atingido 25%, 50%, 75% do total do (numero de avaliacoes ou) tempo estimado, e tambem quando o processo tiver terminado, com sucesso ou devido a  ocorrencia de erros.");

			// create the second message part
			MimeBodyPart mbp2 = new MimeBodyPart();

			// attach the file to the message
			FileDataSource fds = new FileDataSource(filePath);
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(fds.getName());

			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);

			// add the Multipart to the message
			msg.setContent(mp);

			// set the Date: header
			msg.setSentDate(new Date());

			// send the message
			Transport.send(msg);

		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
		}
		
		System.out.println("Email sent!");
	}
}