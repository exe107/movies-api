package mk.ukim.finki.moviesapi.factory;

import static mk.ukim.finki.moviesapi.security.constants.SecurityConstants.ROLE_USER;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import mk.ukim.finki.moviesapi.model.jpa.ReviewEntity;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.model.rest.MovieDto;
import mk.ukim.finki.moviesapi.model.rest.RegistrationDetailsDto;
import mk.ukim.finki.moviesapi.model.rest.ReviewOutDto;
import mk.ukim.finki.moviesapi.model.rest.UserDto;
import mk.ukim.finki.moviesapi.model.rest.UserMovieRatingOutDto;
import mk.ukim.finki.moviesapi.model.rest.UserPersonalDetailsDto;
import mk.ukim.finki.moviesapi.repository.ReviewRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

  private ReviewRepository reviewRepository;
  private MovieFactory movieFactory;
  private ReviewFactory reviewFactory;
  private PasswordEncoder passwordEncoder;

  /**
   * Constructor.
   *
   * @param reviewRepository the review repository
   * @param movieFactory movie factory
   * @param reviewFactory review factory
   * @param passwordEncoder the Spring Security password encoder
   */
  public UserFactory(
      ReviewRepository reviewRepository,
      MovieFactory movieFactory,
      ReviewFactory reviewFactory,
      PasswordEncoder passwordEncoder) {

    this.reviewRepository = reviewRepository;
    this.movieFactory = movieFactory;
    this.reviewFactory = reviewFactory;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Creates a {@link UserDto} from {@link UserEntity}.
   *
   * @param userEntity the DB entity
   * @return the mapped {@link UserDto}
   */
  public UserDto createUserDto(UserEntity userEntity) {

    UserPersonalDetailsDto personalDetails =
        new UserPersonalDetailsDto(
            userEntity.getName(), userEntity.getSurname(), userEntity.getEmail());

    List<UserMovieRatingOutDto> movieRatings =
        movieFactory.createUserMovieRatings(userEntity.getRatedMovies());

    List<MovieDto> watchList = movieFactory.createUserWatchlist(userEntity.getWatchlist());

    List<ReviewOutDto> pendingReviews;
    boolean isAdmin = userEntity.isAdmin();

    if (isAdmin) {
      pendingReviews =
          reviewRepository.findAllByApprovedFalse().stream()
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

  /**
   * Creates a {@link UserEntity} from {@link RegistrationDetailsDto}.
   *
   * @param registrationDetails the registration details
   */
  public UserEntity createUserEntity(RegistrationDetailsDto registrationDetails) {

    UserEntity user = new UserEntity();
    user.setName(registrationDetails.getName());
    user.setSurname(registrationDetails.getSurname());
    user.setEmail(registrationDetails.getEmail());
    user.setUsername(registrationDetails.getUsername());
    user.setPassword(passwordEncoder.encode(registrationDetails.getPassword()));
    user.setRatedMovies(Collections.emptyList());
    user.setWatchlist(Collections.emptyList());
    user.setReviews(Collections.emptyList());

    Set<String> authorities = new HashSet<>(Collections.singletonList(ROLE_USER));
    user.setAuthorities(authorities);

    return user;
  }
}
