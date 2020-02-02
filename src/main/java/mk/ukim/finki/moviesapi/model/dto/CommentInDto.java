package mk.ukim.finki.moviesapi.model.dto;

import lombok.Getter;

@Getter
public class CommentInDto extends MovieDetails {

  private String comment;

  public CommentInDto(MovieDto movie, String comment) {
    super(movie);
    this.comment = comment;
  }
}
