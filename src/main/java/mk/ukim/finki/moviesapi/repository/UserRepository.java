package mk.ukim.finki.moviesapi.repository;

import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {

  UserEntity findByUsername(String username);
}
