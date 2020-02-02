package mk.ukim.finki.moviesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username already taken")
public class UsernameAlreadyExistsException extends RuntimeException {

}
