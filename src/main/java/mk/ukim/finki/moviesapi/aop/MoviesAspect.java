package mk.ukim.finki.moviesapi.aop;

import mk.ukim.finki.moviesapi.model.dto.MovieDetails;
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
  public void saveMovieInDatabase(MovieDetails movieDetails) {
    moviesService.saveMovie(movieDetails.getMovie());
  }

  @Before("execution(* mk.ukim.finki.moviesapi.controller.*.*(..))" + " && args(movieDto, ..)")
  public void saveMovieInDatabase(MovieDto movieDto) {
    moviesService.saveMovie(movieDto);
  }
}
