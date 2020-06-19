package mk.ukim.finki.moviesapi.service;

import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.model.rest.PasswordChangeDto;
import mk.ukim.finki.moviesapi.model.rest.UserDto;
import mk.ukim.finki.moviesapi.model.rest.UserPersonalDetailsDto;

public interface UsersService {

  UserEntity getUser(String username);

  void saveUser(UserEntity user);

  UserDto editProfile(String username, UserPersonalDetailsDto personalDetails);

  void changePassword(String username, PasswordChangeDto passwordDetails);
}
