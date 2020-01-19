package mk.ukim.finki.moviesapi.service;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.Movie;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRating;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieRatingEntity;

public interface MoviesService {

  MovieEntity saveMovie(Movie movie);

  MovieRatingEntity saveRating(String movieId, String username, Integer rating);

  List<UserMovieRating> getUserRatedMovies(String username);

  void deleteRating(String movieId, String username);
}
