package mk.ukim.finki.moviesapi.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mk.ukim.finki.moviesapi.model.dto.LoginCredentials;
import mk.ukim.finki.moviesapi.model.dto.RegistrationDetails;
import mk.ukim.finki.moviesapi.model.dto.UserPersonalDetails;

public interface RegistrationService {

  boolean register(RegistrationDetails registrationDetails);

  UserPersonalDetails login(HttpServletRequest request, LoginCredentials loginCredentials)
      throws ServletException;
}
