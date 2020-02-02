package mk.ukim.finki.moviesapi.service;

import java.util.List;
import mk.ukim.finki.moviesapi.model.dto.CommentOutDto;

public interface CommentsService {

  List<CommentOutDto> getMovieComments(String movieId);

  CommentOutDto saveComment(String movieId, String username, String comment);

  void deleteComment(Long commentId);
}
