package mk.ukim.finki.moviesapi.model.mail;

import lombok.Getter;

@Getter
public enum MimeType {
  HTML("text/html"),
  TEXT("text/plain");

  private String type;

  MimeType(String type) {
    this.type = type;
  }
}
