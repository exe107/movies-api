package mk.ukim.finki.moviesapi.service;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.ReviewOutDto;
import mk.ukim.finki.moviesapi.model.jpa.ReviewEntity;

public interface ReviewsService {

  List<ReviewOutDto> getMovieReviews(String movieId);

  List<ReviewEntity> getAllPendingReviews();

  ReviewOutDto addReview(String movieId, String username, String review);

  void approveReview(String adminUsername, String reviewUsername, String movieId);

  void deleteReview(String username, String movieId);

  void rejectReview(String adminUsername, String reviewUsername, String movieId);
}
