package mk.ukim.finki.moviesapi.model.jpa;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieEntity {

  @Id private String id;

  private String name;
  private Integer year;

  @ElementCollection private List<String> genres;

  private String imageUrl;

  @Column(precision = 2, scale = 1)
  private BigDecimal rating;

  private Integer runtime;

  @OneToMany(mappedBy = "movie")
  private List<ReviewEntity> reviews;
}
