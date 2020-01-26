package mk.ukim.finki.moviesapi.controller;

import mk.ukim.finki.moviesapi.model.dto.MovieDto;
import mk.ukim.finki.moviesapi.service.MoviesService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieWatchlistController {

  private MoviesService moviesService;

  public MovieWatchlistController(MoviesService moviesService) {
    this.moviesService = moviesService;
  }

  /**
   * Adds the movie to the user's watchlist.
   *
   * @param movie dto containing movie data
   * @param authentication the authentication object
   */
  @PostMapping("movies/watchlist")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addMovieToWatchlist(
      @RequestBody MovieDto movie, @AuthenticationPrincipal Authentication authentication) {

    String username = (String) authentication.getPrincipal();
    moviesService.addMovieToWatchlist(username, movie.getId());
  }

  /**
   * Removes the movie from the user's watchlist.
   *
   * @param authentication the authentication object
   * @param movieId the movie id
   */
  @DeleteMapping("movies/watchlist/{movieId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeMovieFromWatchlist(
      @AuthenticationPrincipal Authentication authentication, @PathVariable String movieId) {

    String username = (String) authentication.getPrincipal();
    moviesService.removeMovieFromWatchlist(username, movieId);
  }
}
