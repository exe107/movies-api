package mk.ukim.finki.moviesapi.model.rest;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MovieDto {

  private String id;
  private String name;
  private Integer year;
  private List<String> genres;
  private String imageUrl;
  private BigDecimal rating;
  private Integer runtime;
}
