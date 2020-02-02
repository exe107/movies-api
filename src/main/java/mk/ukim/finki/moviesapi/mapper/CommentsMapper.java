package mk.ukim.finki.moviesapi.mapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import mk.ukim.finki.moviesapi.model.dto.CommentOutDto;
import mk.ukim.finki.moviesapi.model.jpa.CommentEntity;
import mk.ukim.finki.moviesapi.model.jpa.MovieEntity;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentsMapper {

  /**
   * Maps a list of {@link CommentEntity} to a sorted list of {@link CommentOutDto}.
   *
   * @param movieEntity the DB entity
   * @return mapped list of {@link CommentOutDto}
   */
  public List<CommentOutDto> mapToMovieComments(MovieEntity movieEntity) {
    return movieEntity.getComments().stream()
        .map(this::mapToMovieComment)
        .sorted(Comparator.comparing(CommentOutDto::getDate).reversed())
        .collect(Collectors.toList());
  }

  /**
   * Maps from {@link CommentEntity} to {@link CommentOutDto}.
   *
   * @param commentEntity the DB entity
   * @return the mapped {@link CommentOutDto}
   */
  public CommentOutDto mapToMovieComment(CommentEntity commentEntity) {
    UserEntity userEntity = commentEntity.getUser();

    return new CommentOutDto(
        commentEntity.getId(),
        userEntity.getUsername(),
        commentEntity.getComment(),
        commentEntity.getDate());
  }
}
