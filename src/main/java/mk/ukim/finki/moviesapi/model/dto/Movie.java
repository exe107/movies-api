package mk.ukim.finki.moviesapi.model.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Movie {

  private String id;
  private String name;
  private int year;
  private String imageUrl;
  private BigDecimal rating;
}
