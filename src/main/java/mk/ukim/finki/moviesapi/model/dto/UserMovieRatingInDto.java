package mk.ukim.finki.moviesapi.model.dto;

import lombok.Getter;

@Getter
public class UserMovieRatingInDto extends MovieDetails {

  private Integer rating;

  public UserMovieRatingInDto(MovieDto movie, Integer rating) {
    super(movie);
    this.rating = rating;
  }
}
