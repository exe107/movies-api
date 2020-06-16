package mk.ukim.finki.moviesapi.model.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "candidate_movie_of_the_day")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CandidateMovieOfTheDayEntity {

  @Id private String imdbId;
  private boolean chosen;
}
