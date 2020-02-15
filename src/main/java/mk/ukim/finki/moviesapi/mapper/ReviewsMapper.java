package mk.ukim.finki.moviesapi.mapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.moviesapi.model.dto.ApprovedReviewOutDto;
import mk.ukim.finki.moviesapi.model.dto.PendingReviewOutDto;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.ReviewEntity;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ReviewsMapper {

  /**
   * Maps a list of {@link ReviewEntity} to a sorted list of {@link ApprovedReviewOutDto}.
   *
   * @param reviews list of reviews
   * @return list of approved reviews
   */
  public List<ApprovedReviewOutDto> mapToApprovedReviews(List<ReviewEntity> reviews) {
    return reviews.stream()
        .filter(ReviewEntity::isApproved)
        .map(this::mapToApprovedReview)
        .sorted(Comparator.comparing(ApprovedReviewOutDto::getDate).reversed())
        .collect(Collectors.toList());
  }

  private ApprovedReviewOutDto mapToApprovedReview(ReviewEntity reviewEntity) {
    UserEntity userEntity = reviewEntity.getUser();

    return new ApprovedReviewOutDto(
        reviewEntity.getId(),
        userEntity.getUsername(),
        reviewEntity.getReview(),
        reviewEntity.getDate());
  }

  /**
   * Maps a list of {@link ReviewEntity} to a list of {@link PendingReviewOutDto}.
   *
   * @param reviews list of reviews
   * @return list of pending reviews
   */
  public List<PendingReviewOutDto> mapToPendingReviews(List<ReviewEntity> reviews) {
    return reviews.stream()
        .filter(ReviewEntity::isPending)
        .map(this::mapToPendingReview)
        .collect(Collectors.toList());
  }

  /**
   * Maps from {@link ReviewEntity} to {@link PendingReviewOutDto}.
   *
   * @param reviewEntity the DB review
   * @return the mapped pending review
   */
  public PendingReviewOutDto mapToPendingReview(ReviewEntity reviewEntity) {
    UserEntity user = reviewEntity.getUser();
    MovieEntity movie = reviewEntity.getMovie();

    return new PendingReviewOutDto(
        reviewEntity.getId(),
        user.getUsername(),
        reviewEntity.getReview(),
        reviewEntity.getDate(),
        movie.getId(),
        movie.getName());
  }
}
