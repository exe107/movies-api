package mk.ukim.finki.moviesapi.service.impl;

import java.util.List;
import mk.ukim.finki.moviesapi.factory.UserFactory;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.model.rest.InitializationDataDto;
import mk.ukim.finki.moviesapi.model.rest.UserDto;
import mk.ukim.finki.moviesapi.service.InitializationDataService;
import mk.ukim.finki.moviesapi.service.MoviesOfTheDayService;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class InitializationDataServiceImpl implements InitializationDataService {

  private UsersService usersService;
  private UserFactory userFactory;
  private MoviesOfTheDayService moviesOfTheDayService;

  /**
   * Constructor.
   *
   * @param usersService {@link UsersService}
   * @param userFactory {@link UserFactory}
   * @param moviesOfTheDayService {@link MoviesOfTheDayService}
   */
  public InitializationDataServiceImpl(
      UsersService usersService,
      UserFactory userFactory,
      MoviesOfTheDayService moviesOfTheDayService) {

    this.usersService = usersService;
    this.userFactory = userFactory;
    this.moviesOfTheDayService = moviesOfTheDayService;
  }

  @Override
  public InitializationDataDto createInitializationData(Authentication authentication) {

    UserDto user = null;

    if (authentication != null) {
      String username = (String) authentication.getPrincipal();
      UserEntity userEntity = usersService.getUser(username);
      user = userFactory.createUserDto(userEntity);
    }

    List<String> movieIdsForToday = moviesOfTheDayService.getMovieIdsForToday();

    return new InitializationDataDto(user, movieIdsForToday);
  }
}
