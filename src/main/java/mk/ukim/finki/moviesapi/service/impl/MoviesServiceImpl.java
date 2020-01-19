package mk.ukim.finki.moviesapi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import mk.ukim.finki.moviesapi.mapper.MoviesMapper;
import mk.ukim.finki.moviesapi.model.dto.Movie;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRating;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieRatingEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieRatingKey;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.repository.MovieRatingRepository;
import mk.ukim.finki.moviesapi.repository.MovieRepository;
import mk.ukim.finki.moviesapi.service.MoviesService;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class MoviesServiceImpl implements MoviesService {

  private MovieRepository movieRepository;
  private MovieRatingRepository movieRatingRepository;
  private UsersService usersService;
  private MoviesMapper moviesMapper;

  /**
   * Constructor.
   *
   * @param movieRepository {@link MovieRepository}
   * @param movieRatingRepository {@link MovieRatingRepository}
   * @param usersService {@link UsersService}
   * @param moviesMapper {@link MoviesMapper}
   */
  public MoviesServiceImpl(
      MovieRepository movieRepository,
      MovieRatingRepository movieRatingRepository,
      UsersService usersService,
      MoviesMapper moviesMapper) {

    this.movieRepository = movieRepository;
    this.movieRatingRepository = movieRatingRepository;
    this.usersService = usersService;
    this.moviesMapper = moviesMapper;
  }

  @Override
  public MovieEntity saveMovie(Movie movie) {

    MovieEntity movieEntity = moviesMapper.mapToMovieEntity(movie);

    return movieRepository.save(movieEntity);
  }

  @Override
  public MovieRatingEntity saveRating(String movieId, String username, Integer rating) {
    MovieRatingKey primaryKey = new MovieRatingKey(username, movieId);
    MovieRatingEntity movieRating = new MovieRatingEntity();
    movieRating.setId(primaryKey);
    movieRating.setDate(new Date());
    movieRating.setRating(rating);

    UserEntity user = usersService.getUser(username);
    movieRating.setUser(user);

    Optional<MovieEntity> movie = movieRepository.findById(movieId);
    movieRating.setMovie(movie.get());

    return movieRatingRepository.save(movieRating);
  }

  @Override
  public List<UserMovieRating> getUserRatedMovies(String username) {
    List<MovieRatingEntity> movieRatings = movieRatingRepository.findAllByUserUsername(username);

    return moviesMapper.mapToUserMovieRatings(movieRatings);
  }

  @Override
  public void deleteRating(String movieId, String username) {
    List<MovieRatingEntity> movieRatings = movieRatingRepository.findAllByUserUsername(username);

    Optional<MovieRatingEntity> ratingToDelete =
        movieRatings.stream()
            .filter(movieRating -> movieRating.getId().getMovieId().equals(movieId))
            .findFirst();

    movieRatingRepository.delete(ratingToDelete.get());
  }
}
