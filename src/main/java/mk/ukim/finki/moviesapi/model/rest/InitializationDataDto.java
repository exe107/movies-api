package mk.ukim.finki.moviesapi.model.rest;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InitializationDataDto {

  private UserDto user;
  private List<String> movieIdsForToday;
}
