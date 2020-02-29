package mk.ukim.finki.moviesapi.repository;

import java.util.List;
import javax.transaction.Transactional;
import mk.ukim.finki.moviesapi.model.jpa.MovieOfTheDayEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovieOfTheDayRepository extends CrudRepository<MovieOfTheDayEntity, String> {

  List<MovieOfTheDayEntity> findAllByChosenFalse();

  @Transactional
  @Modifying
  @Query("update MovieOfTheDayEntity set chosen = false")
  void reset();
}
