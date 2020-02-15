package mk.ukim.finki.moviesapi.model.dto;

import java.util.Date;
import lombok.Getter;

@Getter
public class PendingReviewOutDto extends BaseReviewOutDto {

  private String movieId;
  private String movie;

  /**
   * Constructor.
   *
   * @param id id
   * @param username username
   * @param review review
   * @param date date
   * @param movieId movie id
   * @param movie movie title
   */
  public PendingReviewOutDto(
      Long id, String username, String review, Date date, String movieId, String movie) {

    super(id, username, review, date);
    this.movieId = movieId;
    this.movie = movie;
  }
}
