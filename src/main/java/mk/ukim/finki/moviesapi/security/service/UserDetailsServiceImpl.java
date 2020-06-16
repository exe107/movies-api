package mk.ukim.finki.moviesapi.security.service;

import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserDetailsServiceImpl implements UserDetailsService {

  private UsersService usersService;

  public UserDetailsServiceImpl(UsersService usersService) {
    this.usersService = usersService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return usersService.getUser(username);
  }
}
