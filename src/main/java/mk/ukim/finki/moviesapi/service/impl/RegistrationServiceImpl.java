package mk.ukim.finki.moviesapi.service.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mk.ukim.finki.moviesapi.model.dto.LoginCredentials;
import mk.ukim.finki.moviesapi.model.dto.RegistrationDetails;
import mk.ukim.finki.moviesapi.model.dto.UserPersonalDetails;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.service.MoviesService;
import mk.ukim.finki.moviesapi.service.RegistrationService;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

  private UsersService usersService;
  private PasswordEncoder passwordEncoder;
  private MoviesService moviesService;

  /**
   * Constructor.
   *
   * @param usersService {@link UsersService}
   * @param passwordEncoder {@link PasswordEncoder}
   * @param moviesService {@link MoviesService}
   */
  public RegistrationServiceImpl(
      UsersService usersService,
      PasswordEncoder passwordEncoder,
      MoviesService moviesService) {

    this.usersService = usersService;
    this.passwordEncoder = passwordEncoder;
    this.moviesService = moviesService;
  }

  @Override
  public boolean register(RegistrationDetails registrationDetails) {
    UserEntity existingUser = usersService.getUser(registrationDetails.getUsername());

    if (existingUser != null) {
      return false;
    }

    UserEntity user = new UserEntity();
    user.setName(registrationDetails.getName());
    user.setSurname(registrationDetails.getSurname());
    user.setUsername(registrationDetails.getUsername());
    user.setPassword(passwordEncoder.encode(registrationDetails.getPassword()));
    usersService.saveUser(user);

    return true;
  }

  @Override
  public UserPersonalDetails login(HttpServletRequest request, LoginCredentials loginCredentials)
      throws ServletException {

    String username = loginCredentials.getUsername();
    String password = loginCredentials.getPassword();
    request.login(username, password);

    UserEntity user = usersService.getUser(username);

    return new UserPersonalDetails(user.getName(), user.getSurname());
  }
}
