package mk.ukim.finki.moviesapi.service;

import mk.ukim.finki.moviesapi.model.jpa.User;

public interface UsersService {

  User getUser(String username);

  User saveUser(User user);
}
