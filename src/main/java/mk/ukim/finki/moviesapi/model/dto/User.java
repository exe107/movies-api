package mk.ukim.finki.moviesapi.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {

  private UserPersonalDetails personalDetails;
  private List<UserMovieRating> movieRatings;
}
