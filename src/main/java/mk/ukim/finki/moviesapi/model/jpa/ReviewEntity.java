package mk.ukim.finki.moviesapi.model.jpa;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "review")
@NoArgsConstructor
@Getter
@Setter
public class ReviewEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  private Date date;

  @Lob private String review;
  private boolean approved;

  @ManyToOne private UserEntity user;
  @ManyToOne private MovieEntity movie;

  public boolean isPending() {
    return !approved;
  }
}
