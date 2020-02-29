package mk.ukim.finki.moviesapi.repository;

import mk.ukim.finki.moviesapi.model.jpa.DayEntity;
import org.springframework.data.repository.CrudRepository;

public interface DayRepository extends CrudRepository<DayEntity, String> {}
