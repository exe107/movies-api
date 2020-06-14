package mk.ukim.finki.moviesapi.model.rest;

import lombok.Getter;

@Getter
public class PasswordChangeDto {

  private String oldPassword;
  private String newPassword;
}
