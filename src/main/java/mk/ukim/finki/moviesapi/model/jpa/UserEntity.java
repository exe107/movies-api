package mk.ukim.finki.moviesapi.model.jpa;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Setter
public class UserEntity implements UserDetails {

  @Id private String username;

  private String name;
  private String surname;
  private String password;

  @OneToMany(mappedBy = "user")
  private List<MovieRatingEntity> ratedMovies;

  @ManyToMany
  @JoinTable(
      name = "watchlist",
      joinColumns = @JoinColumn(name = "username"),
      inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private List<MovieEntity> watchlist;

  @OneToMany(mappedBy = "user")
  private List<ReviewEntity> reviews;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public List<MovieRatingEntity> getRatedMovies() {
    return ratedMovies;
  }

  public List<MovieEntity> getWatchlist() {
    return watchlist;
  }

  public List<ReviewEntity> getReviews() {
    return reviews;
  }

  public void addMovieToWatchlist(MovieEntity movieEntity) {
    watchlist.add(movieEntity);
  }

  public void removeMovieFromWatchlist(MovieEntity movieEntityToRemove) {
    watchlist.removeIf(movieEntity -> movieEntity.getId().equals(movieEntityToRemove.getId()));
  }
}
