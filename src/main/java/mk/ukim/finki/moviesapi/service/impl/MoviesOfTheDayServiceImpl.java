package mk.ukim.finki.moviesapi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.moviesapi.exception.MoviesOfTheDayNotFoundException;
import mk.ukim.finki.moviesapi.model.jpa.MovieOfTheDayEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieOfTheDayKey;
import mk.ukim.finki.moviesapi.repository.MovieOfTheDayRepository;
import mk.ukim.finki.moviesapi.service.MoviesOfTheDayService;
import org.springframework.stereotype.Service;

@Service
public class MoviesOfTheDayServiceImpl implements MoviesOfTheDayService {

  private MovieOfTheDayRepository movieOfTheDayRepository;

  /**
   * Constructor.
   *
   * @param movieOfTheDayRepository {@link MovieOfTheDayRepository}
   */
  public MoviesOfTheDayServiceImpl(MovieOfTheDayRepository movieOfTheDayRepository) {
    this.movieOfTheDayRepository = movieOfTheDayRepository;
  }

  @Override
  public List<String> getMovieIdsForToday() {
    String currentDate = new SimpleDateFormat("dd-MM-YYYY").format(new Date());
    List<MovieOfTheDayEntity> moviesOfTheDay = movieOfTheDayRepository.findAllByDate(currentDate);

    if (moviesOfTheDay.isEmpty()) {
      throw new MoviesOfTheDayNotFoundException();
    }

    return moviesOfTheDay.stream()
        .map(MovieOfTheDayEntity::getId)
        .map(MovieOfTheDayKey::getMovieId)
        .collect(Collectors.toList());
  }
}
