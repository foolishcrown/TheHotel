/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.dtos;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Shang
 */
public class MailSender {

    public static void sendEmail(String recepient, String code) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
//        mail.smtp.auth
//        mail.smtp.starttls.enable
//        mail.smtp.host - smtp.mail.com
//        mail.smtp.port - 587
        String myAccountEmail = "shang2856@gmail.com";
        String password = "Dcvfasxz2856";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = preparedMessage(session, myAccountEmail, recepient, code);
        Transport.send(message);

    }

    private static Message preparedMessage(Session session, String myAccountEmail, String recepient, String code) throws AddressException, MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
        message.setSubject("Confirm Code for Booking Order");
        message.setContent("<h1>" + code + "</h1>", "text/html");
        return message;
    }

}
