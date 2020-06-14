package mk.ukim.finki.moviesapi.model.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MailCredentialsDto {

  private String email;
  private String password;
}
