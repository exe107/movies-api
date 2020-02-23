package mk.ukim.finki.moviesapi.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.moviesapi.factory.ReviewFactory;
import mk.ukim.finki.moviesapi.model.dto.ReviewOutDto;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.ReviewEntity;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.repository.ReviewRepository;
import mk.ukim.finki.moviesapi.service.MoviesService;
import mk.ukim.finki.moviesapi.service.ReviewsService;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class ReviewsServiceImpl implements ReviewsService {

  private UsersService usersService;
  private MoviesService moviesService;
  private ReviewRepository reviewRepository;
  private ReviewFactory reviewFactory;

  /**
   * Constructor.
   *
   * @param usersService {@link UsersService}
   * @param moviesService {@link MoviesService}
   * @param reviewRepository {@link ReviewRepository}
   * @param reviewFactory {@link ReviewFactory}
   */
  public ReviewsServiceImpl(
      UsersService usersService,
      MoviesService moviesService,
      ReviewRepository reviewRepository,
      ReviewFactory reviewFactory) {

    this.usersService = usersService;
    this.moviesService = moviesService;
    this.reviewRepository = reviewRepository;
    this.reviewFactory = reviewFactory;
  }

  @Override
  public List<ReviewOutDto> getMovieReviews(String movieId) {
    MovieEntity movieEntity = moviesService.getMovie(movieId);

    if (movieEntity == null) {
      return Collections.emptyList();
    }

    return movieEntity.getReviews().stream()
        .filter(ReviewEntity::isApproved)
        .map(reviewFactory::createReviewOutDto)
        .sorted(Comparator.comparing(ReviewOutDto::getDate).reversed())
        .collect(Collectors.toList());
  }

  @Override
  public ReviewOutDto addReview(String movieId, String username, String review) {

    MovieEntity movieEntity = moviesService.getMovie(movieId);
    UserEntity user = usersService.getUser(username);
    ReviewEntity reviewEntity = new ReviewEntity();
    reviewEntity.setReview(review);
    reviewEntity.setMovie(movieEntity);
    reviewEntity.setUser(user);
    reviewEntity.setDate(new Date());
    reviewEntity.setApproved(user.isAdmin());
    reviewRepository.save(reviewEntity);

    return reviewFactory.createReviewOutDto(reviewEntity);
  }

  @Override
  public void approveReview(String username, String movieId) {
    ReviewEntity reviewEntity = reviewRepository.findByUserUsernameAndMovieId(username, movieId);
    reviewEntity.setApproved(true);
    reviewRepository.save(reviewEntity);
  }

  @Override
  public void deleteReview(String username, String movieId) {
    reviewRepository.deleteByUserUsernameAndMovieId(username, movieId);
  }

  @Override
  public List<ReviewEntity> getAllPendingReviews() {
    return reviewRepository.findAllByApprovedFalse();
  }
}
