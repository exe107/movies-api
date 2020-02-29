package mk.ukim.finki.moviesapi.service;

import java.util.List;

public interface MoviesOfTheDayService {

  List<String> getMovieIdsForToday();
}
