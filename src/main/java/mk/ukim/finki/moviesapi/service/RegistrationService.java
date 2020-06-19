package mk.ukim.finki.moviesapi.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mk.ukim.finki.moviesapi.model.rest.LoginCredentialsDto;
import mk.ukim.finki.moviesapi.model.rest.RegistrationDetailsDto;
import mk.ukim.finki.moviesapi.model.rest.UserDto;

public interface RegistrationService {

  void register(RegistrationDetailsDto registrationDetails);

  UserDto login(HttpServletRequest request, LoginCredentialsDto loginCredentials)
      throws ServletException;
}
