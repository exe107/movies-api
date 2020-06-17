package mk.ukim.finki.moviesapi.controller;

import java.util.List;
import mk.ukim.finki.moviesapi.model.rest.MovieDto;
import mk.ukim.finki.moviesapi.model.rest.ReviewInDto;
import mk.ukim.finki.moviesapi.model.rest.ReviewOutDto;
import mk.ukim.finki.moviesapi.model.rest.UserMovieIdentifierDto;
import mk.ukim.finki.moviesapi.service.ReviewsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewsController {

  private ReviewsService reviewsService;

  public ReviewsController(ReviewsService reviewsService) {
    this.reviewsService = reviewsService;
  }

  @GetMapping("reviews/{movieId}")
  public List<ReviewOutDto> getMovieReviews(@PathVariable String movieId) {
    return reviewsService.getMovieReviews(movieId);
  }

  /**
   * Adds a review for the given movie id. If the currently authenticated user is an administrator
   * the review is automatically approved. Otherwise it is added in a pending state waiting to be
   * approved by an administrator.
   *
   * @param review the review's details
   * @param authentication the authentication object
   * @return {@link ReviewOutDto}
   */
  @PostMapping("reviews/add")
  public ReviewOutDto addReview(
      @RequestBody ReviewInDto review, @AuthenticationPrincipal Authentication authentication) {

    String username = (String) authentication.getPrincipal();
    MovieDto movie = review.getMovie();

    return reviewsService.addReview(movie.getId(), username, review.getReview());
  }

  /**
   * Approves the review written from the user specified in {@link UserMovieIdentifierDto}.
   *
   * @param identifier dto containing the movie and the user who wrote the review about it
   */
  @PostMapping("reviews/approve")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void approveReview(@RequestBody UserMovieIdentifierDto identifier) {

    reviewsService.approveReview(identifier.getUsername(), identifier.getMovieId());
  }

  /**
   * Rejects the review written from the user specified in {@link UserMovieIdentifierDto}.
   *
   * @param identifier dto containing the movie and the user who wrote the review about it
   */
  @PostMapping("reviews/reject")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void rejectReview(@RequestBody UserMovieIdentifierDto identifier) {

    reviewsService.rejectReview(identifier.getUsername(), identifier.getMovieId());
  }

  /**
   * Deletes the user's review for the given movie id.
   *
   * @param authentication the authentication object
   * @param movieId the movie id
   */
  @DeleteMapping("reviews/{movieId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteReview(
      @AuthenticationPrincipal Authentication authentication, @PathVariable String movieId) {

    String username = (String) authentication.getPrincipal();
    reviewsService.deleteReview(username, movieId);
  }
}
