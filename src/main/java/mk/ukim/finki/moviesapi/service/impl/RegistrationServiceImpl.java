package mk.ukim.finki.moviesapi.service.impl;

import static mk.ukim.finki.moviesapi.security.constants.SecurityConstants.ROLE_USER;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mk.ukim.finki.moviesapi.exception.PasswordMismatchException;
import mk.ukim.finki.moviesapi.exception.UsernameAlreadyExistsException;
import mk.ukim.finki.moviesapi.factory.UserFactory;
import mk.ukim.finki.moviesapi.model.dto.LoginCredentialsDto;
import mk.ukim.finki.moviesapi.model.dto.PasswordChangeDto;
import mk.ukim.finki.moviesapi.model.dto.RegistrationDetailsDto;
import mk.ukim.finki.moviesapi.model.dto.UserDto;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.service.RegistrationService;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

  private UsersService usersService;
  private PasswordEncoder passwordEncoder;
  private UserFactory userFactory;

  /**
   * Constructor.
   *
   * @param usersService {@link UsersService}
   * @param passwordEncoder {@link PasswordEncoder}
   * @param userFactory {@link UserFactory}
   */
  public RegistrationServiceImpl(
      UsersService usersService, PasswordEncoder passwordEncoder, UserFactory userFactory) {

    this.usersService = usersService;
    this.passwordEncoder = passwordEncoder;
    this.userFactory = userFactory;
  }

  @Override
  public void register(RegistrationDetailsDto registrationDetails) {
    UserEntity existingUser = usersService.getUser(registrationDetails.getUsername());

    if (existingUser != null) {
      throw new UsernameAlreadyExistsException();
    }

    UserEntity user = userFactory.createUserEntity(registrationDetails);
    usersService.saveUser(user);
  }

  @Override
  public UserDto login(HttpServletRequest request, LoginCredentialsDto loginCredentials)
      throws ServletException {

    String username = loginCredentials.getUsername();
    String password = loginCredentials.getPassword();
    request.login(username, password);

    UserEntity userEntity = usersService.getUser(username);

    return userFactory.createUserDto(userEntity);
  }

  @Override
  public void changePassword(String username, PasswordChangeDto passwordDetails) {
    UserEntity userEntity = usersService.getUser(username);

    boolean matches =
        passwordEncoder.matches(passwordDetails.getOldPassword(), userEntity.getPassword());

    if (!matches) {
      throw new PasswordMismatchException();
    }

    userEntity.setPassword(passwordEncoder.encode(passwordDetails.getNewPassword()));
    usersService.saveUser(userEntity);
  }
}
