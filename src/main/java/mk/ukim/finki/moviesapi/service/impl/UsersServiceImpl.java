package mk.ukim.finki.moviesapi.service.impl;

import mk.ukim.finki.moviesapi.exception.PasswordMismatchException;
import mk.ukim.finki.moviesapi.factory.UserFactory;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.model.rest.PasswordChangeDto;
import mk.ukim.finki.moviesapi.model.rest.UserDto;
import mk.ukim.finki.moviesapi.model.rest.UserPersonalDetailsDto;
import mk.ukim.finki.moviesapi.repository.UserRepository;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

  private UserRepository userRepository;
  private UserFactory userFactory;
  private PasswordEncoder passwordEncoder;

  /**
   * Constructor.
   *
   * @param userRepository the user repository
   * @param userFactory the user factory
   * @param passwordEncoder the Spring Security password encoder
   */
  public UsersServiceImpl(
      UserRepository userRepository, UserFactory userFactory, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userFactory = userFactory;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserEntity getUser(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void saveUser(UserEntity user) {
    userRepository.save(user);
  }

  @Override
  public UserDto editProfile(String username, UserPersonalDetailsDto personalDetails) {
    UserEntity userEntity = userRepository.findByUsername(username);
    userEntity.setName(personalDetails.getName());
    userEntity.setSurname(personalDetails.getSurname());
    userEntity.setEmail(personalDetails.getEmail());
    userRepository.save(userEntity);

    return userFactory.createUserDto(userEntity);
  }

  @Override
  public void changePassword(String username, PasswordChangeDto passwordDetails) {
    UserEntity userEntity = userRepository.findByUsername(username);

    boolean matches =
        passwordEncoder.matches(passwordDetails.getOldPassword(), userEntity.getPassword());

    if (!matches) {
      throw new PasswordMismatchException();
    }

    userEntity.setPassword(passwordEncoder.encode(passwordDetails.getNewPassword()));
    userRepository.save(userEntity);
  }
}
