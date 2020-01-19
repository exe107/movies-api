package mk.ukim.finki.moviesapi.repository;

import java.util.List;
import mk.ukim.finki.moviesapi.model.jpa.MovieRatingEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieRatingKey;
import org.springframework.data.repository.CrudRepository;

public interface MovieRatingRepository extends CrudRepository<MovieRatingEntity, MovieRatingKey> {

  List<MovieRatingEntity> findAllByUserUsername(String username);
}
