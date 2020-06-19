package mk.ukim.finki.moviesapi.service.impl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mk.ukim.finki.moviesapi.exception.UsernameAlreadyExistsException;
import mk.ukim.finki.moviesapi.factory.UserFactory;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.model.rest.LoginCredentialsDto;
import mk.ukim.finki.moviesapi.model.rest.RegistrationDetailsDto;
import mk.ukim.finki.moviesapi.model.rest.UserDto;
import mk.ukim.finki.moviesapi.service.RegistrationService;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

  private UsersService usersService;
  private UserFactory userFactory;

  /**
   * Constructor.
   *
   * @param usersService {@link UsersService}
   * @param userFactory {@link UserFactory}
   */
  public RegistrationServiceImpl(UsersService usersService, UserFactory userFactory) {

    this.usersService = usersService;
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
}
