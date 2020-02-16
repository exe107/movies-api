package mk.ukim.finki.moviesapi.model.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewOutDto {

  private String movieId;
  private String username;
  private String review;
  private String movie;
  private Date date;
}
