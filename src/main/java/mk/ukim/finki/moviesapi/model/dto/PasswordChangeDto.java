package mk.ukim.finki.moviesapi.model.dto;

import lombok.Getter;

@Getter
public class PasswordChangeDto {

  private String oldPassword;
  private String newPassword;
}
