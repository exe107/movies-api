package mk.ukim.finki.moviesapi.aop.aspect;

import mk.ukim.finki.moviesapi.model.dto.MovieDetailsDto;
import mk.ukim.finki.moviesapi.model.dto.MovieDto;
import mk.ukim.finki.moviesapi.service.MoviesService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MoviesAspect {

  private MoviesService moviesService;

  public MoviesAspect(MoviesService moviesService) {
    this.moviesService = moviesService;
  }

  @Before("execution(* mk.ukim.finki.moviesapi.controller.*.*(..))" + " && args(movieDetails, ..)")
  public void saveMovieInDatabase(MovieDetailsDto movieDetails) {
    moviesService.saveMovie(movieDetails.getMovie());
  }

  @Before("execution(* mk.ukim.finki.moviesapi.controller.*.*(..))" + " && args(movie, ..)")
  public void saveMovieInDatabase(MovieDto movie) {
    moviesService.saveMovie(movie);
  }
}
