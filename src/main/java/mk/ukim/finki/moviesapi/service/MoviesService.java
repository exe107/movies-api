package mk.ukim.finki.moviesapi.service;

import mk.ukim.finki.moviesapi.model.dto.MovieDto;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;

public interface MoviesService {

  MovieEntity getMovie(String movieId);

  void saveMovie(MovieDto movie);

  void saveMovie(MovieEntity movieEntity);

  void saveRating(String username, String movieId, Integer rating);

  void deleteRating(String username, String movieId);

  void addMovieToWatchlist(String username, String movieId);

  void removeMovieFromWatchlist(String username, String movieId);
}
