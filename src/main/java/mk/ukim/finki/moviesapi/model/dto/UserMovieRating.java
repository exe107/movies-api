package mk.ukim.finki.moviesapi.model.dto;

import lombok.Getter;

@Getter
public class UserMovieRating extends MovieDetails {

  private Integer rating;

  public UserMovieRating(Movie movie, Integer rating) {
    super(movie);
    this.rating = rating;
  }
}
