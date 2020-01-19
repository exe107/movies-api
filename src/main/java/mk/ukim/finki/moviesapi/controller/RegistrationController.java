package mk.ukim.finki.moviesapi.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import mk.ukim.finki.moviesapi.exception.LoginException;
import mk.ukim.finki.moviesapi.exception.RegisterException;
import mk.ukim.finki.moviesapi.model.dto.LoginCredentials;
import mk.ukim.finki.moviesapi.model.dto.RegistrationDetails;
import mk.ukim.finki.moviesapi.model.dto.UserPersonalDetails;
import mk.ukim.finki.moviesapi.service.RegistrationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

  private RegistrationService registrationService;

  /**
   * Constructor.
   *
   * @param registrationService service for registering the user
   */
  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  /**
   * Registers the user if the provided username is not already taken.
   *
   * @param request the http request
   * @param registrationDetails the user's entered registration details
   * @return the user's {@link UserPersonalDetails} if the username is not already taken. Otherwise
   *     returns a 409 code response.
   */
  @PostMapping("register")
  public ResponseEntity<UserPersonalDetails> register(
      HttpServletRequest request, @RequestBody RegistrationDetails registrationDetails) {

    boolean registered = registrationService.register(registrationDetails);

    if (registered) {
      LoginCredentials loginCredentials =
          new LoginCredentials(
              registrationDetails.getUsername(), registrationDetails.getPassword());

      return login(request, loginCredentials);
    } else {
      throw new RegisterException();
    }
  }

  /**
   * Logs in the user with the given credentials.
   *
   * @param request the http request
   * @param credentials the entered credentials
   * @return the user's {@link UserPersonalDetails} if the entered credentials were valid. Otherwise
   *     returns a 401 code response.
   */
  @PostMapping("login")
  public ResponseEntity<UserPersonalDetails> login(
      HttpServletRequest request, @RequestBody LoginCredentials credentials) {

    try {
      UserPersonalDetails user = registrationService.login(request, credentials);
      return ResponseEntity.ok(user);
    } catch (ServletException exception) {
      throw new LoginException();
    }
  }
}
