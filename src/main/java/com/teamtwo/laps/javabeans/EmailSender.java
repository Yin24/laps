package com.teamtwo.laps.javabeans;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import org.hibernate.validator.constraints.Email;

public class EmailSender {
	
	private ArrayList<String> recipients;
	private String subjectMessage;
	private String bodyMessage;
	
	public static EmailSender getEmailSender() {
		EmailSender emailSender = new EmailSender();
		emailSender.recipients = new ArrayList<>();
		emailSender.subjectMessage = "Default subject";
		emailSender.bodyMessage = "Default text";
		return emailSender;
	}
	
	public EmailSender setRecipients(ArrayList<String> recipients) {
		this.recipients.clear();
		this.recipients.addAll(recipients);
		return this;
	}
	
	public EmailSender addRecipient(String recipient) {
		recipients.add(recipient);
		return this;
	}
	
	public EmailSender setSubject(String subject) {
		this.subjectMessage = subject;
		return this;
	}
	
	public EmailSender setMessage(String message) {
		this.bodyMessage = message;
		return this;
	}
	
	public void send() {
		if (recipients.size() == 0) {
			recipients.add("sa44lapsteamtwo@gmail.com");
		}
		
		sendEmail(recipients.toArray(new String[0]), subjectMessage, bodyMessage);
	}
	
	public static void sendEmail(String[] to, String subject, String body) {
		final String USER_NAME = "sa44lapsteamtwo"; // GMail user name (just the
		// part before "@gmail.com")
		final String PASSWORD = "lapsteamtwo"; // GMail password
		String from = USER_NAME;
		String pass = PASSWORD;
		
		sendFromGMail(from, pass, to, subject, body);
	}

	public static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
