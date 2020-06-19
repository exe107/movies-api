package mk.ukim.finki.moviesapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    value = HttpStatus.UNAUTHORIZED,
    reason = "Incorrect old password")
public class PasswordMismatchException extends RuntimeException {

}
