package mk.ukim.finki.moviesapi.service.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mk.ukim.finki.moviesapi.model.LoginCredentials;
import mk.ukim.finki.moviesapi.model.RegistrationDetails;
import mk.ukim.finki.moviesapi.model.UserPersonalDetails;
import mk.ukim.finki.moviesapi.model.jpa.User;
import mk.ukim.finki.moviesapi.service.RegistrationService;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

  private UsersService usersService;
  private PasswordEncoder passwordEncoder;

  public RegistrationServiceImpl(UsersService usersService, PasswordEncoder passwordEncoder) {
    this.usersService = usersService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public boolean register(RegistrationDetails registrationDetails) {
    User existingUser = usersService.getUser(registrationDetails.getUsername());

    if (existingUser != null) {
      return false;
    }

    User user = new User();
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

    User user = usersService.getUser(username);

    return new UserPersonalDetails(user.getName(), user.getSurname());
  }
}
