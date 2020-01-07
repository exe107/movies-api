package mk.ukim.finki.moviesapi.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mk.ukim.finki.moviesapi.model.LoginCredentials;
import mk.ukim.finki.moviesapi.model.RegistrationDetails;
import mk.ukim.finki.moviesapi.model.UserPersonalDetails;

public interface RegistrationService {

  boolean register(RegistrationDetails registrationDetails);

  UserPersonalDetails login(HttpServletRequest request, LoginCredentials loginCredentials)
      throws ServletException;
}
