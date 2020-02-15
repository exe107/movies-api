package mk.ukim.finki.moviesapi.model.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class BaseReviewOutDto {

  private Long id;
  private String username;
  private String review;
  private Date date;
}
