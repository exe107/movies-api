package mk.ukim.finki.moviesapi.repository;

import mk.ukim.finki.moviesapi.model.jpa.ReviewEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {}
