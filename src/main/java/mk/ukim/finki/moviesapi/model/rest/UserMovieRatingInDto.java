package mk.ukim.finki.moviesapi.model.rest;

import lombok.Getter;

@Getter
public class UserMovieRatingInDto extends MovieDetailsDto {

  private Integer rating;

  public UserMovieRatingInDto(MovieDto movie, Integer rating) {
    super(movie);
    this.rating = rating;
  }
}
