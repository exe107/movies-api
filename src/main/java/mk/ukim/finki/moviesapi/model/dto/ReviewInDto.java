package mk.ukim.finki.moviesapi.model.dto;

import lombok.Getter;

@Getter
public class ReviewInDto extends MovieDetailsDto {

  private String review;

  public ReviewInDto(MovieDto movie, String review) {
    super(movie);
    this.review = review;
  }
}
