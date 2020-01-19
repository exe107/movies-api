package mk.ukim.finki.moviesapi.security.provider;

import mk.ukim.finki.moviesapi.exception.LoginException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDetailsAuthenticationProvider implements AuthenticationProvider {

  private UserDetailsService userDetailsService;
  private PasswordEncoder passwordEncoder;

  /**
   * Constructor.
   *
   * @param userDetailsService {@link UserDetailsService}
   * @param passwordEncoder {@link PasswordEncoder}
   */
  public UserDetailsAuthenticationProvider(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {

    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    UsernamePasswordAuthenticationToken token =
        (UsernamePasswordAuthenticationToken) authentication;

    String username = (String) token.getPrincipal();
    String password = (String) token.getCredentials();
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    if (userDetails == null) {
      throw new LoginException();
    }

    boolean passwordMatches = passwordEncoder.matches(password, userDetails.getPassword());

    if (!passwordMatches) {
      throw new LoginException();
    }

    return new UsernamePasswordAuthenticationToken(username, password);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.equals(authentication);
  }
}
