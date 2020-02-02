package mk.ukim.finki.moviesapi.controller;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.CommentInDto;
import mk.ukim.finki.moviesapi.model.dto.CommentOutDto;
import mk.ukim.finki.moviesapi.model.dto.MovieDto;
import mk.ukim.finki.moviesapi.service.CommentsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentsController {

  private CommentsService commentsService;

  public CommentsController(CommentsService commentsService) {
    this.commentsService = commentsService;
  }

  @GetMapping("comments/{movieId}")
  public List<CommentOutDto> getComments(@PathVariable String movieId) {
    return commentsService.getMovieComments(movieId);
  }

  /**
   * Saves the user's comment.
   *
   * @param comment the comment's details
   * @param authentication the authentication object
   */
  @PostMapping("comments")
  public CommentOutDto saveComment(
      @RequestBody CommentInDto comment, @AuthenticationPrincipal Authentication authentication) {

    String username = (String) authentication.getPrincipal();
    MovieDto movie = comment.getMovie();

    return commentsService.saveComment(movie.getId(), username, comment.getComment());
  }

  @DeleteMapping("comments/{commentId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteComment(@PathVariable Long commentId) {
    commentsService.deleteComment(commentId);
  }
}
