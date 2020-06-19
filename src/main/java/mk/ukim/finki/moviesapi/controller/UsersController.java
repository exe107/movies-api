package mk.ukim.finki.moviesapi.controller;

import mk.ukim.finki.moviesapi.model.rest.PasswordChangeDto;
import mk.ukim.finki.moviesapi.model.rest.UserDto;
import mk.ukim.finki.moviesapi.model.rest.UserPersonalDetailsDto;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

  private UsersService usersService;

  /**
   * Constructor.
   *
   * @param usersService the service for user related operations
   */
  public UsersController(UsersService usersService) {
    this.usersService = usersService;
  }

  /**
   * Saves the new details for user.
   *
   * @param authentication the authentication object
   * @param personalDetailsDto dto containing the new details
   */
  @PostMapping("users/edit/profile")
  public UserDto editProfile(
      @AuthenticationPrincipal Authentication authentication,
      @RequestBody UserPersonalDetailsDto personalDetailsDto) {

    String username = (String) authentication.getPrincipal();

    return usersService.editProfile(username, personalDetailsDto);
  }

  /**
   * Changes the user's password if the old one matches.
   *
   * @param authentication the authentication object
   * @param passwordDetails dto containing the old and the new password
   */
  @PostMapping("users/edit/password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void editPassword(
      @AuthenticationPrincipal Authentication authentication,
      @RequestBody PasswordChangeDto passwordDetails) {

    String username = (String) authentication.getPrincipal();
    usersService.changePassword(username, passwordDetails);
  }
}
