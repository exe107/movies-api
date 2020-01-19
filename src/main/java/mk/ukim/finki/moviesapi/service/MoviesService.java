package mk.ukim.finki.moviesapi.service;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.Movie;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRating;

public interface MoviesService {

  void saveMovie(Movie movie);

  void saveRating(String movieId, String username, Integer rating);

  List<UserMovieRating> getUserRatedMovies(String username);
}
