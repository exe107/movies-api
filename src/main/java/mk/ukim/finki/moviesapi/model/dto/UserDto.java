package mk.ukim.finki.moviesapi.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {

  private String username;
  private UserPersonalDetailsDto personalDetails;
  private List<UserMovieRatingOutDto> movieRatings;
  private List<MovieDto> watchlist;
}
