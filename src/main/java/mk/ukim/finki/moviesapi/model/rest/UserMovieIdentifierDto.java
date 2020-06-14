package mk.ukim.finki.moviesapi.model.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserMovieIdentifierDto {

  private String username;
  private String movieId;
}
