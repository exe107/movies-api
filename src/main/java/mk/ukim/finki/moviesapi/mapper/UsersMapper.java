package mk.ukim.finki.moviesapi.mapper;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.User;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRating;
import mk.ukim.finki.moviesapi.model.dto.UserPersonalDetails;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {

  private MoviesMapper moviesMapper;

  public UsersMapper(MoviesMapper moviesMapper) {
    this.moviesMapper = moviesMapper;
  }

  /**
   * Maps to {@link User} from {@link UserEntity}.
   *
   * @param userEntity the DB entity
   * @return the mapped {@link User}
   */
  public User mapToUser(UserEntity userEntity) {

    UserPersonalDetails personalDetails =
        new UserPersonalDetails(userEntity.getName(), userEntity.getSurname());

    List<UserMovieRating> movieRatings =
        moviesMapper.mapToUserMovieRatings(userEntity.getRatedMovies());

    return new User(personalDetails, movieRatings);
  }
}
