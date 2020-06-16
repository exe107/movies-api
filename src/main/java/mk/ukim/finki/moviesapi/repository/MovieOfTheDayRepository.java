package mk.ukim.finki.moviesapi.repository;

import java.util.List;
import mk.ukim.finki.moviesapi.model.jpa.MovieOfTheDayEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieOfTheDayKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MovieOfTheDayRepository
    extends CrudRepository<MovieOfTheDayEntity, MovieOfTheDayKey> {

  @Query("select movie from MovieOfTheDayEntity movie where movie.id.date = :date")
  List<MovieOfTheDayEntity> findAllByDate(@Param("date") String date);
}
