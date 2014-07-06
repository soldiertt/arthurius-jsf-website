package be.arthurius.core.utils;

import java.security.Security;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import be.arthurius.core.model.User;

public class ArthuriusUtils {
	
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_PORT = "465";
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	
	public static String getRandomChuck() {
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		System.out.println(myRandom);
		return myRandom;
	}
	
	public static void postMailUserRegistration(String type, User user) throws MessagingException {
	     
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		String subject = "";
		String content = "";
		
		if (type.equals("confirmemail")) {
			subject = "Please confirm you email on Arthurius.be";
			content = "Thanks for register on Arthurius.be.<br>At this time you still cannot sign in until you have not confirm your email address.<p>"
				+ "Your username : " + user.getUserName() + "<br>"
				+ "To complete your registration please click on the link below :<br>"
				+ "<a href=\"http://213.214.49.70/Arthurius-web/pages/userreg_confirmemail.jsf?email=" + user.getEmail() + "&chuck=" + user.getChuckCode() + "\">"
				+ "http://213.214.49.70/Arthurius-web/pages/userreg_confirmemail.jsf?email=" + user.getEmail() + "&chuck=" + user.getChuckCode() + "</a><p>Please visit <a href=\"www.arthurius.be\">Arthurius</a>";
		} else if (type.equals("accountvalidated")) {
			subject = "Your registration confirmation on Arthurius.be";
			content = "Please visit <a href=\"www.arthurius.be\">Arthurius</a>";
		}
		
		boolean debug = false;

	     //Set the host smtp address
	    Properties props = new Properties();
	    props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");

	    // create some properties and get the default Session
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("soldiertt@gmail.com", "jlb@lacoil");
					}
				});
	    session.setDebug(debug);

	    // create a message
	    Message msg = new MimeMessage(session);

	    // set the from and to address
	    InternetAddress addressFrom = new InternetAddress("arthurius-lame@gmail.com");
	    msg.setFrom(addressFrom);

	    InternetAddress[] addressTo = new InternetAddress[1]; 
	    addressTo[0] = new InternetAddress(user.getEmail());
	    msg.setRecipients(Message.RecipientType.TO, addressTo);
	   
	    // Optional : You can also set your custom headers in the Email if you Want
	    //msg.addHeader("MyHeaderName", "myHeaderValue");

	    // Setting the Subject and Content Type
	    msg.setSubject(subject);
	    msg.setContent(content, "text/html");
	    Transport.send(msg);
	}

}
