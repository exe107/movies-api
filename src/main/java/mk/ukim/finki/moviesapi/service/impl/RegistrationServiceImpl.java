package mk.ukim.finki.moviesapi.service.impl;

import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mk.ukim.finki.moviesapi.exception.PasswordMismatchException;
import mk.ukim.finki.moviesapi.exception.UsernameAlreadyExistsException;
import mk.ukim.finki.moviesapi.mapper.UsersMapper;
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
  private UsersMapper usersMapper;

  /**
   * Constructor.
   *
   * @param usersService {@link UsersService}
   * @param passwordEncoder {@link PasswordEncoder}
   * @param usersMapper {@link UsersMapper}
   */
  public RegistrationServiceImpl(
      UsersService usersService, PasswordEncoder passwordEncoder, UsersMapper usersMapper) {

    this.usersService = usersService;
    this.passwordEncoder = passwordEncoder;
    this.usersMapper = usersMapper;
  }

  @Override
  public void register(RegistrationDetailsDto registrationDetails) {
    UserEntity existingUser = usersService.getUser(registrationDetails.getUsername());

    if (existingUser != null) {
      throw new UsernameAlreadyExistsException();
    }

    UserEntity user = new UserEntity();
    user.setName(registrationDetails.getName());
    user.setSurname(registrationDetails.getSurname());
    user.setUsername(registrationDetails.getUsername());
    user.setPassword(passwordEncoder.encode(registrationDetails.getPassword()));
    user.setRatedMovies(Collections.emptyList());
    user.setWatchlist(Collections.emptyList());
    usersService.saveUser(user);
  }

  @Override
  public UserDto login(HttpServletRequest request, LoginCredentialsDto loginCredentials)
      throws ServletException {

    String username = loginCredentials.getUsername();
    String password = loginCredentials.getPassword();
    request.login(username, password);

    UserEntity userEntity = usersService.getUser(username);

    return usersMapper.mapToUser(userEntity);
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
