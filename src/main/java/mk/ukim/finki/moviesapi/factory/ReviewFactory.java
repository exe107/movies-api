package mk.ukim.finki.moviesapi.factory;

import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.ReviewEntity;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.model.mail.MailCredentialsDto;
import mk.ukim.finki.moviesapi.model.mail.MailDto;
import mk.ukim.finki.moviesapi.model.mail.MimeType;
import mk.ukim.finki.moviesapi.model.rest.ReviewOutDto;
import org.springframework.stereotype.Component;

@Component
public class ReviewFactory {

  private String reviewMailSubject = "Movie review";
  private String email = "testdiplomsk@gmail.com";
  private String password = "Diplomsk@240";

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
   * @param reviewEntity the review
   * @return {@link MailDto}
   */
  public MailDto createApprovedReviewMail(ReviewEntity reviewEntity) {

    UserEntity reviewWriter = reviewEntity.getUser();
    MovieEntity movie = reviewEntity.getMovie();

    MailCredentialsDto sender = new MailCredentialsDto(email, password);

    String messageTemplate =
        "<p>Your review for the movie \"%s\" has been "
            + "<span style=\"color: green\">approved</span>.</p>";

    String message = String.format(messageTemplate, movie.getName());

    return new MailDto(sender, reviewWriter.getEmail(), reviewMailSubject, message, MimeType.HTML);
  }

  /**
   * Creates a rejection mail with the given reason (if any) to be sent to the writer of the review.
   *
   * @param reviewEntity the review
   * @return {@link MailDto}
   */
  public MailDto createRejectedReviewMail(ReviewEntity reviewEntity) {

    UserEntity reviewWriter = reviewEntity.getUser();
    MovieEntity movie = reviewEntity.getMovie();

    MailCredentialsDto sender = new MailCredentialsDto(email, password);

    String messageTemplate =
        "<p>Your review for the movie \"%s\" has been "
            + "<span style=\"color: red\">rejected</span>.</p>";

    String message = String.format(messageTemplate, movie.getName());

    return new MailDto(sender, reviewWriter.getEmail(), reviewMailSubject, message, MimeType.HTML);
  }
}
