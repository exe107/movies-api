package mk.ukim.finki.moviesapi.controller;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.MovieDto;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRatingInDto;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRatingOutDto;
import mk.ukim.finki.moviesapi.service.MoviesService;
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
public class MovieRatingsController {

  private MoviesService moviesService;

  public MovieRatingsController(MoviesService moviesService) {
    this.moviesService = moviesService;
  }

  /**
   * Returns the movie ratings for the given username.
   *
   * @param username the request username
   * @return list of movie ratings
   */
  @GetMapping("movies/ratings/{username}")
  public List<UserMovieRatingOutDto> userRatings(@PathVariable String username) {
    return moviesService.getUserRatedMovies(username);
  }

  /**
   * Save the user rating for the given movie.
   *
   * @param movieRating dto containing movie data as well as the rating
   * @param authentication the authentication object
   */
  @PostMapping("movies/ratings")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void rateMovie(
      @RequestBody UserMovieRatingInDto movieRating,
      @AuthenticationPrincipal Authentication authentication) {

    String username = (String) authentication.getPrincipal();
    MovieDto movie = movieRating.getMovie();
    moviesService.saveRating(username, movie.getId(), movieRating.getRating());
  }

  /**
   * Deletes the user rating for the given movie id.
   *
   * @param movieId the movie id
   */
  @DeleteMapping("movies/ratings/{movieId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteRating(
      @AuthenticationPrincipal Authentication authentication, @PathVariable String movieId) {

    String username = (String) authentication.getPrincipal();
    moviesService.deleteRating(username, movieId);
  }
}
