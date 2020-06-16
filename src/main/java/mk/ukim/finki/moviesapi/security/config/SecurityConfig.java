package mk.ukim.finki.moviesapi.security.config;

import static mk.ukim.finki.moviesapi.security.constants.SecurityConstants.ROLE_ADMIN;
import static mk.ukim.finki.moviesapi.security.constants.SecurityConstants.ROLE_USER;

import mk.ukim.finki.moviesapi.security.provider.UserDetailsAuthenticationProvider;
import mk.ukim.finki.moviesapi.security.service.UserDetailsServiceImpl;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

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

  /**
   * Defines the application's {@link RoleHierarchy}.
   *
   * @return the role hierarchy
   */
  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

    String hierarchy = String.format("%s > %s", ROLE_ADMIN, ROLE_USER);
    roleHierarchy.setHierarchy(hierarchy);

    return roleHierarchy;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authenticationProvider(userDetailsAuthenticationProvider())
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .logout()
        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT))
        .and()
        .authorizeRequests()
        .mvcMatchers("register", "login")
        .not()
        .authenticated()
        .mvcMatchers("logout")
        .authenticated()
        .mvcMatchers("**/watchlist/**")
        .hasAuthority(ROLE_USER)
        .mvcMatchers(HttpMethod.GET, "reviews/{movieId:tt[0-9]+}")
        .permitAll()
        .mvcMatchers("reviews/approve", "reviews/reject")
        .hasAuthority(ROLE_ADMIN)
        .mvcMatchers("reviews/**")
        .hasAuthority(ROLE_USER);
  }
}
