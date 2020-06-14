package mk.ukim.finki.moviesapi.factory;

import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.ReviewEntity;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.model.mail.MailCredentialsDto;
import mk.ukim.finki.moviesapi.model.mail.MailDto;
import mk.ukim.finki.moviesapi.model.mail.MimeType;
import mk.ukim.finki.moviesapi.model.rest.ReviewOutDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ReviewFactory {

  private String reviewMailSubject = "Movie review";

  /**
   * Creates a {@link ReviewOutDto} from {@link ReviewEntity}.
   *
   * @param reviewEntity the DB review
   * @return the mapped review
   */
  public ReviewOutDto createReviewOutDto(ReviewEntity reviewEntity) {
    UserEntity userEntity = reviewEntity.getUser();
    MovieEntity movieEntity = reviewEntity.getMovie();

    return new ReviewOutDto(
        movieEntity.getId(),
        userEntity.getUsername(),
        reviewEntity.getReview(),
        movieEntity.getName(),
        reviewEntity.getDate());
  }

  /**
   * Creates an approval mail to be sent to the writer of the review.
   *
   * @param administrator the administrator that approved the review
   * @param reviewEntity the review
   * @return {@link MailDto}
   */
  public MailDto createApprovedReviewMail(UserEntity administrator, ReviewEntity reviewEntity) {

    UserEntity reviewWriter = reviewEntity.getUser();
    MovieEntity movie = reviewEntity.getMovie();

    MailCredentialsDto sender =
        new MailCredentialsDto(administrator.getEmail(), administrator.getPassword());

    String messageTemplate =
        "<p>Your review for the movie \"%s\" has been "
            + "<span style=\"color: green\">approved</span>.</p>";

    String message = String.format(messageTemplate, movie.getName());

    return new MailDto(sender, reviewWriter.getEmail(), reviewMailSubject, message, MimeType.HTML);
  }

  /**
   * Creates a rejection mail with the given reason (if any) to be sent to the writer of the review.
   *
   * @param administrator the administrator that rejected the review
   * @param reviewEntity the review
   * @param reason the reason for rejection
   * @return {@link MailDto}
   */
  public MailDto createRejectedReviewMail(
      UserEntity administrator, ReviewEntity reviewEntity, String reason) {

    UserEntity reviewWriter = reviewEntity.getUser();
    MovieEntity movie = reviewEntity.getMovie();

    MailCredentialsDto sender =
        new MailCredentialsDto(administrator.getEmail(), administrator.getPassword());

    String messageTemplate =
        "<p>Your review for the movie \"%s\" has been "
            + "<span style=\"color: red\">rejected</span>";

    if (!StringUtils.isEmpty(reason)) {
      messageTemplate += " with the following reason:</p>\n" + reason;
    } else {
      messageTemplate += ".</p>";
    }

    String message = String.format(messageTemplate, movie.getName());

    return new MailDto(sender, reviewWriter.getEmail(), reviewMailSubject, message, MimeType.HTML);
  }
}
