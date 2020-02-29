package mk.ukim.finki.moviesapi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import mk.ukim.finki.moviesapi.model.jpa.DayEntity;
import mk.ukim.finki.moviesapi.repository.DayRepository;
import mk.ukim.finki.moviesapi.service.MoviesOfTheDayService;
import org.springframework.stereotype.Service;

@Service
public class MoviesOfTheDayServiceImpl implements MoviesOfTheDayService {

  private DayRepository dayRepository;

  /**
   * Constructor.
   *
   * @param dayRepository {@link DayRepository}
   */
  public MoviesOfTheDayServiceImpl(DayRepository dayRepository) {
    this.dayRepository = dayRepository;
  }

  @Override
  public List<String> getMovieIdsForToday() {
    String currentDate = new SimpleDateFormat("dd-MM-YYYY").format(new Date());
    Optional<DayEntity> today = dayRepository.findById(currentDate);

    if (!today.isPresent()) {
      throw new EntityNotFoundException();
    }

    return today.get().getMovieIds();
  }
}
