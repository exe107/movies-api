package mk.ukim.finki.moviesapi.controller;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.Movie;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRating;
import mk.ukim.finki.moviesapi.service.MoviesService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviesController {

  private MoviesService moviesService;

  public MoviesController(MoviesService moviesService) {
    this.moviesService = moviesService;
  }

  /**
   * Returns the movie ratings for the given username.
   *
   * @param username the request username
   * @return list of movie ratings
   */
  @GetMapping("movies/ratings/{username}")
  public List<UserMovieRating> userRatings(@PathVariable String username) {
    return moviesService.getUserRatedMovies(username);
  }

  /**
   * Save the user rating for the given movie.
   *
   * @param authentication the authentication object
   * @param movieRating dto containing movie data as well as the rating
   */
  @PostMapping("movies/ratings")
  public void rateMovie(
      @AuthenticationPrincipal Authentication authentication,
      @RequestBody UserMovieRating movieRating) {

    String username = (String) authentication.getPrincipal();
    Movie movie = movieRating.getMovie();
    moviesService.saveRating(movie.getId(), username, movieRating.getRating());
  }
}
