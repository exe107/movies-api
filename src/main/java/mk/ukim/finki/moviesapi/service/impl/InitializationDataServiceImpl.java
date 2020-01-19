package mk.ukim.finki.moviesapi.service.impl;

import java.util.List;
import mk.ukim.finki.moviesapi.mapper.MoviesMapper;
import mk.ukim.finki.moviesapi.model.dto.InitializationData;
import mk.ukim.finki.moviesapi.model.dto.User;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRating;
import mk.ukim.finki.moviesapi.model.dto.UserPersonalDetails;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.service.InitializationDataService;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class InitializationDataServiceImpl implements InitializationDataService {

  private UsersService usersService;
  private MoviesMapper moviesMapper;

  public InitializationDataServiceImpl(UsersService usersService, MoviesMapper moviesMapper) {
    this.usersService = usersService;
    this.moviesMapper = moviesMapper;
  }

  @Override
  public InitializationData createInitializationData(String username) {
    UserEntity userEntity = usersService.getUser(username);

    UserPersonalDetails personalDetails =
        new UserPersonalDetails(userEntity.getName(), userEntity.getSurname());

    List<UserMovieRating> movieRatings =
        moviesMapper.mapToUserMovieRatings(userEntity.getRatedMovies());

    User user = new User(personalDetails, movieRatings);
    return new InitializationData(user);
  }
}
