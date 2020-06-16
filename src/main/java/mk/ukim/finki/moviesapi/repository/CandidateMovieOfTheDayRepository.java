package mk.ukim.finki.moviesapi.repository;

import java.util.List;
import javax.transaction.Transactional;
import mk.ukim.finki.moviesapi.model.jpa.CandidateMovieOfTheDayEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CandidateMovieOfTheDayRepository
    extends CrudRepository<CandidateMovieOfTheDayEntity, String> {

  List<CandidateMovieOfTheDayEntity> findAllByChosenFalse();

  @Transactional
  @Modifying
  @Query("update CandidateMovieOfTheDayEntity set chosen = false")
  void reset();
}
