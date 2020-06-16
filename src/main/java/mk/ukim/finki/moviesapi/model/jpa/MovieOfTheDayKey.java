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
public class MovieOfTheDayKey implements Serializable {

  private String date;
  private String movieId;

  @Override
  public int hashCode() {
    return this.date.hashCode() ^ this.movieId.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof MovieOfTheDayKey)) {
      return false;
    }

    MovieOfTheDayKey other = (MovieOfTheDayKey) obj;

    return date.equals(other.getDate()) && movieId.equals(other.getMovieId());
  }
}
