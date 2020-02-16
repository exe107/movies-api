package mk.ukim.finki.moviesapi.service.impl;

import java.util.Date;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import mk.ukim.finki.moviesapi.factory.MovieFactory;
import mk.ukim.finki.moviesapi.model.dto.MovieDto;
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
  private MovieFactory movieFactory;

  /**
   * Constructor.
   *
   * @param movieRepository {@link MovieRepository}
   * @param movieRatingRepository {@link MovieRatingRepository}
   * @param usersService {@link UsersService}
   * @param movieFactory {@link MovieFactory}
   */
  public MoviesServiceImpl(
      MovieRepository movieRepository,
      MovieRatingRepository movieRatingRepository,
      UsersService usersService,
      MovieFactory movieFactory) {

    this.movieRepository = movieRepository;
    this.movieRatingRepository = movieRatingRepository;
    this.usersService = usersService;
    this.movieFactory = movieFactory;
  }

  @Override
  public MovieEntity getMovie(String movieId) {
    return movieRepository.findById(movieId).orElse(null);
  }

  @Override
  public void saveMovie(MovieDto movie) {

    Optional<MovieEntity> existingMovieEntity = movieRepository.findById(movie.getId());

    if (!existingMovieEntity.isPresent()) {
      MovieEntity movieEntity = movieFactory.mapToMovieEntity(movie);
      saveMovie(movieEntity);
    }
  }

  @Override
  public void saveMovie(MovieEntity movieEntity) {
    movieRepository.save(movieEntity);
  }

  @Override
  public void saveRating(String username, String movieId, Integer rating) {
    MovieRatingKey primaryKey = new MovieRatingKey(username, movieId);
    MovieRatingEntity movieRating = new MovieRatingEntity();
    movieRating.setId(primaryKey);
    movieRating.setDate(new Date());
    movieRating.setRating(rating);

    UserEntity user = usersService.getUser(username);
    movieRating.setUser(user);

    MovieEntity movie = movieRepository.findById(movieId).orElseThrow(EntityNotFoundException::new);
    movieRating.setMovie(movie);
    movieRatingRepository.save(movieRating);
  }

  @Override
  public void deleteRating(String username, String movieId) {
    movieRatingRepository.deleteById(new MovieRatingKey(username, movieId));
  }

  @Override
  public void addMovieToWatchlist(String username, String movieId) {
    UserEntity user = usersService.getUser(username);

    MovieEntity movie = movieRepository.findById(movieId).orElseThrow(EntityNotFoundException::new);
    user.addMovieToWatchlist(movie);
    usersService.saveUser(user);
  }

  @Override
  public void removeMovieFromWatchlist(String username, String movieId) {
    UserEntity user = usersService.getUser(username);
    MovieEntity movie = movieRepository.findById(movieId).orElseThrow(EntityNotFoundException::new);
    user.removeMovieFromWatchlist(movie);
    usersService.saveUser(user);
  }
}
