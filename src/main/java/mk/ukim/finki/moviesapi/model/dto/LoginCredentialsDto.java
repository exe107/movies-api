package mk.ukim.finki.moviesapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginCredentialsDto {

  private String username;
  private String password;
}
