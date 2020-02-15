package mk.ukim.finki.moviesapi.mapper;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.MovieDto;
import mk.ukim.finki.moviesapi.model.dto.PendingReviewOutDto;
import mk.ukim.finki.moviesapi.model.dto.UserDto;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRatingOutDto;
import mk.ukim.finki.moviesapi.model.dto.UserPersonalDetailsDto;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {

  private MoviesMapper moviesMapper;
  private ReviewsMapper reviewsMapper;

  public UsersMapper(MoviesMapper moviesMapper, ReviewsMapper reviewsMapper) {
    this.moviesMapper = moviesMapper;
    this.reviewsMapper = reviewsMapper;
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

    List<PendingReviewOutDto> pendingReviews =
        reviewsMapper.mapToPendingReviews(userEntity.getReviews());

    return new UserDto(
        userEntity.getUsername(), personalDetails, movieRatings, watchList, pendingReviews);
  }
}
