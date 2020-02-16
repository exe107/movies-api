package mk.ukim.finki.moviesapi.factory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.moviesapi.model.dto.MovieDto;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRatingOutDto;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieRatingEntity;
import org.springframework.stereotype.Component;

@Component
public class MovieFactory {

  /**
   * Creates a {@link MovieEntity} from {@link MovieDto}.
   *
   * @param movie the movie details
   * @return the mapped {@link MovieEntity}
   */
  public MovieEntity mapToMovieEntity(MovieDto movie) {
    return new MovieEntity(
        movie.getId(),
        movie.getName(),
        movie.getYear(),
        movie.getGenres(),
        movie.getImageUrl(),
        movie.getRating(),
        movie.getRuntime(),
        Collections.emptyList());
  }

  public List<UserMovieRatingOutDto> createUserMovieRatings(List<MovieRatingEntity> movieRatings) {
    return movieRatings.stream().map(this::createUserMovieRating).collect(Collectors.toList());
  }

  private UserMovieRatingOutDto createUserMovieRating(MovieRatingEntity movieRatingEntity) {
    MovieEntity movieEntity = movieRatingEntity.getMovie();
    MovieDto movie = createUserMovie(movieEntity);

    return new UserMovieRatingOutDto(
        movie, movieRatingEntity.getRating(), movieRatingEntity.getDate());
  }

  public List<MovieDto> createUserWatchlist(List<MovieEntity> watchlist) {
    return watchlist.stream().map(this::createUserMovie).collect(Collectors.toList());
  }

  private MovieDto createUserMovie(MovieEntity movieEntity) {
    return new MovieDto(
        movieEntity.getId(),
        movieEntity.getName(),
        movieEntity.getYear(),
        movieEntity.getGenres(),
        movieEntity.getImageUrl(),
        movieEntity.getRating(),
        movieEntity.getRuntime());
  }
}
