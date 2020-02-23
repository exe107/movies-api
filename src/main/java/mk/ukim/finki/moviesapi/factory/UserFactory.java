package mk.ukim.finki.moviesapi.factory;

import static mk.ukim.finki.moviesapi.security.constants.SecurityConstants.ROLE_ADMIN;

import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.moviesapi.model.dto.MovieDto;
import mk.ukim.finki.moviesapi.model.dto.ReviewOutDto;
import mk.ukim.finki.moviesapi.model.dto.UserDto;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRatingOutDto;
import mk.ukim.finki.moviesapi.model.dto.UserPersonalDetailsDto;
import mk.ukim.finki.moviesapi.model.jpa.ReviewEntity;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.service.ReviewsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

  private ReviewsService reviewsService;
  private MovieFactory movieFactory;
  private ReviewFactory reviewFactory;

  /**
   * Constructor.
   *
   * @param reviewsService reviews service
   * @param movieFactory movie factory
   * @param reviewFactory review factory
   */
  public UserFactory(
      ReviewsService reviewsService, MovieFactory movieFactory, ReviewFactory reviewFactory) {

    this.reviewsService = reviewsService;
    this.movieFactory = movieFactory;
    this.reviewFactory = reviewFactory;
  }

  /**
   * Creates a {@link UserDto} from {@link UserEntity}.
   *
   * @param userEntity the DB entity
   * @return the mapped {@link UserDto}
   */
  public UserDto createUserDto(UserEntity userEntity) {

    UserPersonalDetailsDto personalDetails =
        new UserPersonalDetailsDto(userEntity.getName(), userEntity.getSurname());

    List<UserMovieRatingOutDto> movieRatings =
        movieFactory.createUserMovieRatings(userEntity.getRatedMovies());

    List<MovieDto> watchList = movieFactory.createUserWatchlist(userEntity.getWatchlist());

    List<ReviewOutDto> pendingReviews;
    boolean isAdmin = userEntity.isAdmin();

    if (isAdmin) {
      pendingReviews =
          reviewsService.getAllPendingReviews().stream()
              .map(reviewFactory::createReviewOutDto)
              .collect(Collectors.toList());
    } else {
      pendingReviews =
          userEntity.getReviews().stream()
              .filter(ReviewEntity::isPending)
              .map(reviewFactory::createReviewOutDto)
              .collect(Collectors.toList());
    }

    return new UserDto(
        userEntity.getUsername(),
        isAdmin,
        personalDetails,
        movieRatings,
        watchList,
        pendingReviews);
  }
}
