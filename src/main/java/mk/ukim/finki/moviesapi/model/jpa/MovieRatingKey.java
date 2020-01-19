package mk.ukim.finki.moviesapi.model.jpa;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieRatingKey implements Serializable {

  private String username;
  private String movieId;

  @Override
  public int hashCode() {
    return this.username.hashCode() ^ this.movieId.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof MovieRatingKey)) {
      return false;
    }

    MovieRatingKey other = (MovieRatingKey) obj;

    return username.equals(other.getUsername()) && movieId.equals(other.getMovieId());
  }
}
