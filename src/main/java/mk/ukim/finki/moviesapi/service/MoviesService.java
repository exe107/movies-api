package mk.ukim.finki.moviesapi.service;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.MovieDto;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRatingOutDto;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieRatingEntity;

public interface MoviesService {

  MovieEntity saveMovie(MovieDto movie);

  MovieRatingEntity saveRating(String movieId, String username, Integer rating);

  List<UserMovieRatingOutDto> getUserRatedMovies(String username);

  void deleteRating(String username, String movieId);

  void addMovieToWatchlist(String username, String movieId);

  void removeMovieFromWatchlist(String username, String movieId);
}
