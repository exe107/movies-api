package mk.ukim.finki.moviesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Movies of the day missing")
public class MoviesOfTheDayNotFoundException extends RuntimeException {

}
