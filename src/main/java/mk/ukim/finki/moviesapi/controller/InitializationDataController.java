package mk.ukim.finki.moviesapi.controller;

import mk.ukim.finki.moviesapi.model.InitializationData;
import mk.ukim.finki.moviesapi.model.UserPersonalDetails;
import mk.ukim.finki.moviesapi.model.jpa.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitializationDataController {

  /**
   * Returns the application initialization data needed on UI.
   *
   * @param authentication the {@link Authentication} object containing the user logged in the
   *     application (if any).
   * @return the {@link InitializationData}
   */
  @GetMapping("initialization")
  public InitializationData initialization(@AuthenticationPrincipal Authentication authentication) {
    InitializationData initializationData = new InitializationData();

    if (authentication != null) {
      User user = (User) authentication.getPrincipal();

      UserPersonalDetails userPersonalDetails =
          new UserPersonalDetails(user.getName(), user.getSurname());

      initializationData.setUser(userPersonalDetails);
    }

    return initializationData;
  }
}
