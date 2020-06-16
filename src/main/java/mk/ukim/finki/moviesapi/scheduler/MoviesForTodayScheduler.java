package mk.ukim.finki.moviesapi.scheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import mk.ukim.finki.moviesapi.model.jpa.CandidateMovieOfTheDayEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieOfTheDayEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieOfTheDayKey;
import mk.ukim.finki.moviesapi.repository.CandidateMovieOfTheDayRepository;
import mk.ukim.finki.moviesapi.repository.MovieOfTheDayRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;

public class MoviesForTodayScheduler {

  private CandidateMovieOfTheDayRepository candidateMovieOfTheDayRepository;
  private MovieOfTheDayRepository movieOfTheDayRepository;

  /**
   * Constructor.
   *
   * @param candidateMovieOfTheDayRepository {@link CandidateMovieOfTheDayRepository}
   * @param movieOfTheDayRepository {@link MovieOfTheDayRepository}
   */
  public MoviesForTodayScheduler(
      CandidateMovieOfTheDayRepository candidateMovieOfTheDayRepository,
      MovieOfTheDayRepository movieOfTheDayRepository) {

    this.candidateMovieOfTheDayRepository = candidateMovieOfTheDayRepository;
    this.movieOfTheDayRepository = movieOfTheDayRepository;
  }

  /**
   * Fills the {@link CandidateMovieOfTheDayEntity} table if needed and runs the generation of
   * movies for today if that isn't already done.
   *
   * @throws IOException exception while reading all the movie ids from the .csv file
   */
  @PostConstruct
  public void runGenerationOfMoviesIfNeeded() throws IOException {
    if (candidateMovieOfTheDayRepository.count() == 0) {
      initializeMoviesOfTheDayTable();
    }

    String currentDate = new SimpleDateFormat("dd-MM-YYYY").format(new Date());
    List<MovieOfTheDayEntity> moviesOfTheDay = movieOfTheDayRepository.findAllByDate(currentDate);

    if (moviesOfTheDay.isEmpty()) {
      generateMoviesForTodayTask();
    }
  }

  @Scheduled(cron = "0 0 0 * * *")
  private void generateMoviesForTodayTask() {
    List<CandidateMovieOfTheDayEntity> moviesForToday = chooseRandomMoviesForToday();
    moviesForToday.forEach(movie -> movie.setChosen(true));
    candidateMovieOfTheDayRepository.saveAll(moviesForToday);

    String currentDate = new SimpleDateFormat("dd-MM-YYYY").format(new Date());

    List<MovieOfTheDayEntity> moviesOfTheDay =
        moviesForToday.stream()
            .map(CandidateMovieOfTheDayEntity::getImdbId)
            .map(imdbId -> new MovieOfTheDayKey(currentDate, imdbId))
            .map(MovieOfTheDayEntity::new)
            .collect(Collectors.toList());

    movieOfTheDayRepository.saveAll(moviesOfTheDay);
  }

  private void initializeMoviesOfTheDayTable() throws IOException {

    List<CandidateMovieOfTheDayEntity> moviesOfTheDay = new ArrayList<>();
    String imdbId;

    Resource resource = new ClassPathResource("movie_ids.csv");
    FileReader fileReader = new FileReader(resource.getFile());
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    while ((imdbId = bufferedReader.readLine()) != null) {
      if (imdbId.startsWith("tt")) {
        CandidateMovieOfTheDayEntity candidateMovieOfTheDayEntity =
            new CandidateMovieOfTheDayEntity(imdbId, false);

        moviesOfTheDay.add(candidateMovieOfTheDayEntity);
      }
    }

    bufferedReader.close();
    candidateMovieOfTheDayRepository.saveAll(moviesOfTheDay);
  }

  private List<CandidateMovieOfTheDayEntity> chooseRandomMoviesForToday() {
    List<CandidateMovieOfTheDayEntity> moviesLeft =
        candidateMovieOfTheDayRepository.findAllByChosenFalse();

    int randomMoviesPerDay = 3;

    if (moviesLeft.size() < randomMoviesPerDay) {
      candidateMovieOfTheDayRepository.reset();
      moviesLeft = candidateMovieOfTheDayRepository.findAllByChosenFalse();
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
