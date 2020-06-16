package mk.ukim.finki.moviesapi.scheduler.config;

import mk.ukim.finki.moviesapi.repository.CandidateMovieOfTheDayRepository;
import mk.ukim.finki.moviesapi.repository.MovieOfTheDayRepository;
import mk.ukim.finki.moviesapi.scheduler.MoviesForTodayScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {

  @Bean
  public MoviesForTodayScheduler moviesForTodayScheduler(
      CandidateMovieOfTheDayRepository candidateMovieOfTheDayRepository,
      MovieOfTheDayRepository movieOfTheDayRepository) {

    return new MoviesForTodayScheduler(candidateMovieOfTheDayRepository, movieOfTheDayRepository);
  }
}
