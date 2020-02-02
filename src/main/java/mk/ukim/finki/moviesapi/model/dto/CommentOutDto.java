package mk.ukim.finki.moviesapi.model.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentOutDto {

  private Long id;
  private String username;
  private String comment;
  private Date date;
}
