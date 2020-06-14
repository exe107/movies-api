package mk.ukim.finki.moviesapi.model.rest;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {

  private String username;
  private boolean admin;
  private UserPersonalDetailsDto personalDetails;
  private List<UserMovieRatingOutDto> movieRatings;
  private List<MovieDto> watchlist;
  private List<ReviewOutDto> pendingReviews;
}
