package mk.ukim.finki.moviesapi.model.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Movie {

  private String id;
  private String name;
  private int year;
  private String imageUrl;
  private BigDecimal rating;
}
