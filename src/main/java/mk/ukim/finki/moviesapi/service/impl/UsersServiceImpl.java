package mk.ukim.finki.moviesapi.service.impl;

import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.repository.UserRepository;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

  private UserRepository userRepository;

  public UsersServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserEntity getUser(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void saveUser(UserEntity user) {
    userRepository.save(user);
  }
}
