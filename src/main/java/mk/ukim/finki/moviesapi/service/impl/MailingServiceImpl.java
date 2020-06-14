package mk.ukim.finki.moviesapi.service.impl;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import mk.ukim.finki.moviesapi.model.mail.MailCredentialsDto;
import mk.ukim.finki.moviesapi.model.mail.MailDto;
import mk.ukim.finki.moviesapi.model.mail.MimeType;
import mk.ukim.finki.moviesapi.service.MailingService;
import org.springframework.stereotype.Service;

@Service
public class MailingServiceImpl implements MailingService {

  @Override
  public void sendMail(MailDto mail) throws MessagingException {

    MailCredentialsDto sender = mail.getSender();
    Session session = getSession(sender);

    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(sender.getEmail()));
    message.setRecipient(RecipientType.TO, new InternetAddress(mail.getRecipient()));
    message.setSubject(mail.getSubject());

    MimeType mimeType = mail.getMimeType();
    message.setContent(mail.getMessage(), mimeType.getType());

    Transport.send(message);
  }

  private Session getSession(MailCredentialsDto sender) {
    Properties properties = createSmtpProperties();

    return Session.getInstance(
        properties,
        new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(sender.getEmail(), sender.getPassword());
          }
        });
  }

  private Properties createSmtpProperties() {
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");

    return properties;
  }
}
