package mk.ukim.finki.moviesapi.model.jpa;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "day")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DayEntity {

  @Id private String date;

  @ElementCollection
  @CollectionTable(name = "day_movie")
  private List<String> movieIds;
}
