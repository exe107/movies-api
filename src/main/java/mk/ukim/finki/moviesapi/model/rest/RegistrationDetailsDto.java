package mk.ukim.finki.moviesapi.model.rest;

import lombok.Getter;

@Getter
public class RegistrationDetailsDto {

  private String name;
  private String surname;
  private String email;
  private String username;
  private String password;
}
