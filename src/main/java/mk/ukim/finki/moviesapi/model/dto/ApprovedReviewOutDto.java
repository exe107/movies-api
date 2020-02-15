package mk.ukim.finki.moviesapi.model.dto;

import java.util.Date;
import lombok.Getter;

@Getter
public class ApprovedReviewOutDto extends BaseReviewOutDto {

  public ApprovedReviewOutDto(Long id, String username, String review, Date date) {
    super(id, username, review, date);
  }
}
