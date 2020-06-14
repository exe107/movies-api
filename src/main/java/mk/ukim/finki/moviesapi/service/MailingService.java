package mk.ukim.finki.moviesapi.service;

import javax.mail.MessagingException;
import mk.ukim.finki.moviesapi.model.mail.MailDto;

public interface MailingService {

  void sendMail(MailDto mail) throws MessagingException;
}
