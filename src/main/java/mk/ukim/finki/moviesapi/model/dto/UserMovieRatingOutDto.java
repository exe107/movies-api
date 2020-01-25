package mk.ukim.finki.moviesapi.model.dto;

import java.util.Date;
import lombok.Getter;

@Getter
public class UserMovieRatingOutDto extends MovieDetails {

  private Integer rating;
  private Date date;

  /**
   * Constructor.
   *
   * @param movie movie details
   * @param rating user's rating for the movie
   * @param date date when the movie was rated
   */
  public UserMovieRatingOutDto(MovieDto movie, Integer rating, Date date) {
    super(movie);
    this.rating = rating;
    this.date = date;
  }
}
