package mk.ukim.finki.moviesapi.repository;

import java.util.List;
import javax.transaction.Transactional;
import mk.ukim.finki.moviesapi.model.jpa.ReviewEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {

  ReviewEntity findByUserUsernameAndMovieId(String username, String movieId);

  @Transactional
  void deleteByUserUsernameAndMovieId(String username, String movieId);

  List<ReviewEntity> findAllByApprovedFalse();
}
