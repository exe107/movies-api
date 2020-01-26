package mk.ukim.finki.moviesapi.mapper;

import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.moviesapi.model.dto.MovieDto;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRatingOutDto;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieRatingEntity;
import org.springframework.stereotype.Component;

@Component
public class MoviesMapper {

  /**
   * Maps from {@link MovieDto} to {@link MovieEntity}.
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
        movie.getRuntime());
  }

  public List<UserMovieRatingOutDto> mapToUserMovieRatings(List<MovieRatingEntity> movieRatings) {
    return movieRatings.stream().map(this::mapToUserMovieRating).collect(Collectors.toList());
  }

  private UserMovieRatingOutDto mapToUserMovieRating(MovieRatingEntity movieRatingEntity) {
    MovieEntity movieEntity = movieRatingEntity.getMovie();
    MovieDto movie = mapToUserMovie(movieEntity);

    return new UserMovieRatingOutDto(
        movie, movieRatingEntity.getRating(), movieRatingEntity.getDate());
  }

  public List<MovieDto> mapToUserWatchlist(List<MovieEntity> watchlist) {
    return watchlist.stream().map(this::mapToUserMovie).collect(Collectors.toList());
  }

  private MovieDto mapToUserMovie(MovieEntity movieEntity) {
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
