package mk.ukim.finki.moviesapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MailDto {

  private MailCredentialsDto sender;
  private String recipient;
  private String subject;
  private String message;
  private MimeType mimeType;
}
