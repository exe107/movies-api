package mk.ukim.finki.moviesapi.mapper;

import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.moviesapi.model.dto.Movie;
import mk.ukim.finki.moviesapi.model.dto.UserMovieRating;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieRatingEntity;
import org.springframework.stereotype.Component;

@Component
public class MoviesMapper {

  public MovieEntity mapToMovieEntity(Movie movie) {
    return new MovieEntity(
        movie.getId(), movie.getName(), movie.getYear(), movie.getImageUrl(), movie.getRating());
  }

  public List<UserMovieRating> mapToUserMovieRatings(List<MovieRatingEntity> movieRatings) {
    return movieRatings.stream().map(this::mapToUserMovieRating).collect(Collectors.toList());
  }

  private UserMovieRating mapToUserMovieRating(MovieRatingEntity movieRatingEntity) {
    MovieEntity movieEntity = movieRatingEntity.getMovie();

    Movie movie =
        new Movie(
            movieEntity.getId(),
            movieEntity.getName(),
            movieEntity.getYear(),
            movieEntity.getImageUrl(),
            movieEntity.getRating());

    return new UserMovieRating(movie, movieRatingEntity.getRating());
  }
}
