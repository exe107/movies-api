package mk.ukim.finki.moviesapi.repository;

import mk.ukim.finki.moviesapi.model.jpa.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
}
