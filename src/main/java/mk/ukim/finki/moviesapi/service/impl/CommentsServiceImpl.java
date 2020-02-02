package mk.ukim.finki.moviesapi.service.impl;

import java.util.Date;
import java.util.List;
import mk.ukim.finki.moviesapi.mapper.CommentsMapper;
import mk.ukim.finki.moviesapi.model.dto.CommentOutDto;
import mk.ukim.finki.moviesapi.model.jpa.CommentEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.repository.CommentRepository;
import mk.ukim.finki.moviesapi.service.CommentsService;
import mk.ukim.finki.moviesapi.service.MoviesService;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class CommentsServiceImpl implements CommentsService {

  private UsersService usersService;
  private MoviesService moviesService;
  private CommentRepository commentRepository;
  private CommentsMapper commentsMapper;

  /**
   * Constructor.
   *
   * @param usersService {@link UsersService}
   * @param moviesService {@link MoviesService}
   * @param commentRepository {@link CommentRepository}
   * @param commentsMapper {@link CommentsMapper}
   */
  public CommentsServiceImpl(
      UsersService usersService,
      MoviesService moviesService,
      CommentRepository commentRepository,
      CommentsMapper commentsMapper) {

    this.usersService = usersService;
    this.moviesService = moviesService;
    this.commentRepository = commentRepository;
    this.commentsMapper = commentsMapper;
  }

  @Override
  public List<CommentOutDto> getMovieComments(String movieId) {
    MovieEntity movieEntity = moviesService.getMovie(movieId);

    return commentsMapper.mapToMovieComments(movieEntity);
  }

  @Override
  public CommentOutDto saveComment(String movieId, String username, String comment) {

    UserEntity user = usersService.getUser(username);
    CommentEntity commentEntity = new CommentEntity();
    commentEntity.setComment(comment);
    commentEntity.setUser(user);
    commentEntity.setDate(new Date());
    commentRepository.save(commentEntity);

    MovieEntity movieEntity = moviesService.getMovie(movieId);
    movieEntity.addComment(commentEntity);
    moviesService.saveMovie(movieEntity);

    return commentsMapper.mapToMovieComment(commentEntity);
  }

  @Override
  public void deleteComment(Long commentId) {
    commentRepository.deleteById(commentId);
  }
}
