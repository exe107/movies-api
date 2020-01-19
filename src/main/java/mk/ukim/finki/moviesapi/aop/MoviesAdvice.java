package mk.ukim.finki.moviesapi.aop;

import mk.ukim.finki.moviesapi.model.dto.MovieDetails;
import mk.ukim.finki.moviesapi.service.MoviesService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MoviesAdvice {

  private MoviesService moviesService;

  public MoviesAdvice(MoviesService moviesService) {
    this.moviesService = moviesService;
  }

  /**
   * Saves the movie in DB if it isn't present.
   *
   * @param movieDetails {@link MovieDetails}
   */
  @Before(
      "execution(* mk.ukim.finki.moviesapi.controller.MoviesController.*(..))"
          + " && args(movieDetails)")
  public void saveMovieInDatabase(MovieDetails movieDetails) {
    moviesService.saveMovie(movieDetails.getMovie());
  }
}
