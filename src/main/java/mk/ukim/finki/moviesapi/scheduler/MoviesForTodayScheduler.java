package mk.ukim.finki.moviesapi.scheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import mk.ukim.finki.moviesapi.model.jpa.DayEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieOfTheDayEntity;
import mk.ukim.finki.moviesapi.repository.DayRepository;
import mk.ukim.finki.moviesapi.repository.MovieOfTheDayRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MoviesForTodayScheduler {

  private MovieOfTheDayRepository movieOfTheDayRepository;
  private DayRepository dayRepository;

  /**
   * Constructor.
   *
   * @param movieOfTheDayRepository {@link MovieOfTheDayRepository}
   * @param dayRepository {@link DayRepository}
   */
  public MoviesForTodayScheduler(
      MovieOfTheDayRepository movieOfTheDayRepository, DayRepository dayRepository) {

    this.movieOfTheDayRepository = movieOfTheDayRepository;
    this.dayRepository = dayRepository;
  }

  /**
   * Fills the {@link MovieOfTheDayEntity} table if needed and runs the generation of movies for
   * today if that isn't already done.
   *
   * @throws IOException exception while reading all the movie ids from the .csv file
   */
  @PostConstruct
  public void runGenerationOfMoviesIfNeeded() throws IOException {
    if (movieOfTheDayRepository.count() == 0) {
      initializeMoviesOfTheDayTable();
    }

    String currentDate = new SimpleDateFormat("dd-MM-YYYY").format(new Date());
    Optional<DayEntity> existingDay = dayRepository.findById(currentDate);

    if (!existingDay.isPresent()) {
      generateMoviesForTodayTask();
    }
  }

  @Scheduled(cron = "0 0 0 * * *")
  private void generateMoviesForTodayTask() {
    List<MovieOfTheDayEntity> moviesForToday = chooseRandomMoviesForToday();
    moviesForToday.forEach(movie -> movie.setChosen(true));
    movieOfTheDayRepository.saveAll(moviesForToday);

    List<String> movieIdsForToday =
        moviesForToday.stream().map(MovieOfTheDayEntity::getImdbId).collect(Collectors.toList());

    String currentDate = new SimpleDateFormat("dd-MM-YYYY").format(new Date());
    DayEntity dayEntity = new DayEntity(currentDate, movieIdsForToday);
    dayRepository.save(dayEntity);
  }

  private void initializeMoviesOfTheDayTable() throws IOException {

    List<MovieOfTheDayEntity> moviesOfTheDay = new ArrayList<>();
    String imdbId;

    Resource resource = new ClassPathResource("movie_ids.csv");
    FileReader fileReader = new FileReader(resource.getFile());
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    while ((imdbId = bufferedReader.readLine()) != null) {
      if (imdbId.startsWith("tt")) {
        MovieOfTheDayEntity movieOfTheDayEntity = new MovieOfTheDayEntity(imdbId, false);
        moviesOfTheDay.add(movieOfTheDayEntity);
      }
    }

    bufferedReader.close();
    movieOfTheDayRepository.saveAll(moviesOfTheDay);
  }

  private List<MovieOfTheDayEntity> chooseRandomMoviesForToday() {
    List<MovieOfTheDayEntity> moviesLeft = movieOfTheDayRepository.findAllByChosenFalse();
    int randomMoviesPerDay = 3;

    if (moviesLeft.size() <= randomMoviesPerDay) {
      movieOfTheDayRepository.reset();
      moviesLeft = movieOfTheDayRepository.findAllByChosenFalse();
    }

    List<Integer> movieIndicesForToday = new ArrayList<>();

    while (movieIndicesForToday.size() < randomMoviesPerDay) {
      Random random = new Random();
      int movieIndex = random.nextInt(moviesLeft.size());

      if (!movieIndicesForToday.contains(movieIndex)) {
        movieIndicesForToday.add(movieIndex);
      }
    }

    return movieIndicesForToday.stream().map(moviesLeft::get).collect(Collectors.toList());
  }
}
