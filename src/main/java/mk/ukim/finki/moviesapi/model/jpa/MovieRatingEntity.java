package mk.ukim.finki.moviesapi.model.jpa;

import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movie_rating")
@NoArgsConstructor
@Getter
@Setter
public class MovieRatingEntity {

  @EmbeddedId
  private MovieRatingKey id;

  private int rating;
  private Date date;

  @ManyToOne
  @MapsId("username")
  private UserEntity user;

  @ManyToOne
  @MapsId("movieId")
  private MovieEntity movie;
}
