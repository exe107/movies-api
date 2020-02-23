package mk.ukim.finki.moviesapi.model.jpa;

import static mk.ukim.finki.moviesapi.security.constants.SecurityConstants.ROLE_ADMIN;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

  @ElementCollection
  @CollectionTable(name = "user_authority")
  private Set<String> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
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

  public boolean isAdmin() {
    return authorities.stream().anyMatch(authority -> authority.equals(ROLE_ADMIN));
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
