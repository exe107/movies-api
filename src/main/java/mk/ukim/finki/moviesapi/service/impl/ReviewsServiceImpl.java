package mk.ukim.finki.moviesapi.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import mk.ukim.finki.moviesapi.mapper.ReviewsMapper;
import mk.ukim.finki.moviesapi.model.dto.ApprovedReviewOutDto;
import mk.ukim.finki.moviesapi.model.dto.PendingReviewOutDto;
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
  private ReviewsMapper reviewsMapper;

  /**
   * Constructor.
   *
   * @param usersService {@link UsersService}
   * @param moviesService {@link MoviesService}
   * @param reviewRepository {@link ReviewRepository}
   * @param reviewsMapper {@link ReviewsMapper}
   */
  public ReviewsServiceImpl(
      UsersService usersService,
      MoviesService moviesService,
      ReviewRepository reviewRepository,
      ReviewsMapper reviewsMapper) {

    this.usersService = usersService;
    this.moviesService = moviesService;
    this.reviewRepository = reviewRepository;
    this.reviewsMapper = reviewsMapper;
  }

  @Override
  public List<ApprovedReviewOutDto> getMovieReviews(String movieId) {
    MovieEntity movieEntity = moviesService.getMovie(movieId);

    if (movieEntity == null) {
      return Collections.emptyList();
    }

    return reviewsMapper.mapToApprovedReviews(movieEntity.getReviews());
  }

  @Override
  public PendingReviewOutDto addReview(String movieId, String username, String review) {

    MovieEntity movieEntity = moviesService.getMovie(movieId);
    UserEntity user = usersService.getUser(username);
    ReviewEntity reviewEntity = new ReviewEntity();
    reviewEntity.setReview(review);
    reviewEntity.setMovie(movieEntity);
    reviewEntity.setUser(user);
    reviewEntity.setDate(new Date());
    reviewEntity.setApproved(false);
    reviewRepository.save(reviewEntity);

    return reviewsMapper.mapToPendingReview(reviewEntity);
  }

  @Override
  public void deleteReview(Long reviewId) {
    reviewRepository.deleteById(reviewId);
  }
}
