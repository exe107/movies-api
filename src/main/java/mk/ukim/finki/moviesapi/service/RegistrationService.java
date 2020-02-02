package mk.ukim.finki.moviesapi.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mk.ukim.finki.moviesapi.model.dto.LoginCredentialsDto;
import mk.ukim.finki.moviesapi.model.dto.PasswordChangeDto;
import mk.ukim.finki.moviesapi.model.dto.RegistrationDetailsDto;
import mk.ukim.finki.moviesapi.model.dto.UserDto;

public interface RegistrationService {

  void register(RegistrationDetailsDto registrationDetails);

  UserDto login(HttpServletRequest request, LoginCredentialsDto loginCredentials)
      throws ServletException;

  void changePassword(String username, PasswordChangeDto passwordDetails);
}
