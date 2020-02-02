package mk.ukim.finki.moviesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.UNAUTHORIZED,
    reason = "The provided password does not match the old one")
public class PasswordMismatchException extends RuntimeException {

}
