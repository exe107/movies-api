package mk.ukim.finki.moviesapi.factory;

import mk.ukim.finki.moviesapi.model.dto.ReviewOutDto;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.ReviewEntity;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ReviewFactory {

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
}
