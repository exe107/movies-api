package mk.ukim.finki.moviesapi.service;

import mk.ukim.finki.moviesapi.model.jpa.UserEntity;

public interface UsersService {

  UserEntity getUser(String username);

  UserEntity saveUser(UserEntity user);
}
