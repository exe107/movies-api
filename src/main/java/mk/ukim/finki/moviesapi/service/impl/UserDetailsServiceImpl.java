package mk.ukim.finki.moviesapi.service.impl;

import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UsersService usersService;

  public UserDetailsServiceImpl(UsersService usersService) {
    this.usersService = usersService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return usersService.getUser(username);
  }
}
