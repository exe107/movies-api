package mk.ukim.finki.moviesapi.controller;

import mk.ukim.finki.moviesapi.model.dto.InitializationData;
import mk.ukim.finki.moviesapi.service.InitializationDataService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitializationDataController {

  private InitializationDataService initializationDataService;

  public InitializationDataController(InitializationDataService initializationDataService) {
    this.initializationDataService = initializationDataService;
  }

  /**
   * Returns the application initialization data needed on UI.
   *
   * @param authentication the {@link Authentication} object containing the user logged in the
   *     application (if any).
   * @return the {@link InitializationData}
   */
  @GetMapping("initialization")
  public InitializationData initialization(@AuthenticationPrincipal Authentication authentication) {
    if (authentication == null) {
      return new InitializationData();
    }

    String username = (String) authentication.getPrincipal();

    return initializationDataService.createInitializationData(username);
  }
}
