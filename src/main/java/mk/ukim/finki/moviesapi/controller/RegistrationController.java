package mk.ukim.finki.moviesapi.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import mk.ukim.finki.moviesapi.exception.RegisterException;
import mk.ukim.finki.moviesapi.model.dto.LoginCredentialsDto;
import mk.ukim.finki.moviesapi.model.dto.RegistrationDetailsDto;
import mk.ukim.finki.moviesapi.model.dto.UserDto;
import mk.ukim.finki.moviesapi.model.dto.UserPersonalDetailsDto;
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
   * @return the user's {@link UserPersonalDetailsDto} if the username is not already taken. Otherwise
   *     returns a 409 code response.
   */
  @PostMapping("register")
  public ResponseEntity<UserDto> register(
      HttpServletRequest request, @RequestBody RegistrationDetailsDto registrationDetails)
      throws ServletException {

    boolean registered = registrationService.register(registrationDetails);

    if (registered) {
      LoginCredentialsDto loginCredentials =
          new LoginCredentialsDto(
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
   * @return the user's {@link UserPersonalDetailsDto} if the entered credentials were valid. Otherwise
   *     returns a 401 code response.
   */
  @PostMapping("login")
  public ResponseEntity<UserDto> login(
      HttpServletRequest request, @RequestBody LoginCredentialsDto credentials)
      throws ServletException {

    UserDto user = registrationService.login(request, credentials);
    return ResponseEntity.ok(user);
  }
}
