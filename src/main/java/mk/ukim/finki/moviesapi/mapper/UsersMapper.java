package mk.ukim.finki.moviesapi.mapper;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.MovieDto;
import mk.ukim.finki.moviesapi.model.dto.UserDto;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRatingOutDto;
import mk.ukim.finki.moviesapi.model.dto.UserPersonalDetailsDto;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {

  private MoviesMapper moviesMapper;

  public UsersMapper(MoviesMapper moviesMapper) {
    this.moviesMapper = moviesMapper;
  }

  /**
   * Maps to {@link UserDto} from {@link UserEntity}.
   *
   * @param userEntity the DB entity
   * @return the mapped {@link UserDto}
   */
  public UserDto mapToUser(UserEntity userEntity) {

    UserPersonalDetailsDto personalDetails =
        new UserPersonalDetailsDto(userEntity.getName(), userEntity.getSurname());

    List<UserMovieRatingOutDto> movieRatings =
        moviesMapper.mapToUserMovieRatings(userEntity.getRatedMovies());

    List<MovieDto> watchList = moviesMapper.mapToUserWatchlist(userEntity.getWatchlist());

    return new UserDto(userEntity.getUsername(), personalDetails, movieRatings, watchList);
  }
}
