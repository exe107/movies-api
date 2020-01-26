package mk.ukim.finki.moviesapi.config;

import mk.ukim.finki.moviesapi.security.provider.UserDetailsAuthenticationProvider;
import mk.ukim.finki.moviesapi.security.service.UserDetailsServiceImpl;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private UsersService usersService;

  public SecurityConfig(UsersService usersService) {
    super();
    this.usersService = usersService;
  }

  /**
   * Configures a {@link UserDetailsAuthenticationProvider} with a custom user details service and
   * password encoder.
   *
   * @return the configured {@link DaoAuthenticationProvider}
   */
  @Bean
  public AuthenticationProvider userDetailsAuthenticationProvider() {
    return new UserDetailsAuthenticationProvider(
        userDetailsService(usersService), passwordEncoder());
  }

  @Bean
  public UserDetailsService userDetailsService(UsersService usersService) {
    return new UserDetailsServiceImpl(usersService);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authenticationProvider(userDetailsAuthenticationProvider())
        .csrf()
        .disable() // consider enabling csrf
        .logout()
        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT));
  }
}
