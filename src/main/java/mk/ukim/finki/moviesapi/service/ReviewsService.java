package mk.ukim.finki.moviesapi.service;

import java.util.List;
import mk.ukim.finki.moviesapi.model.rest.ReviewOutDto;

public interface ReviewsService {

  List<ReviewOutDto> getMovieReviews(String movieId);

  ReviewOutDto addReview(String movieId, String username, String review);

  void approveReview(String username, String movieId);

  void deleteReview(String username, String movieId);

  void rejectReview(String username, String movieId);
}
