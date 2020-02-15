package mk.ukim.finki.moviesapi.service;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.ApprovedReviewOutDto;
import mk.ukim.finki.moviesapi.model.dto.PendingReviewOutDto;

public interface ReviewsService {

  List<ApprovedReviewOutDto> getMovieReviews(String movieId);

  PendingReviewOutDto addReview(String movieId, String username, String review);

  void deleteReview(Long reviewId);
}
