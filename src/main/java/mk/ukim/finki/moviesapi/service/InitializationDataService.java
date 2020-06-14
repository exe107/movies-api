package mk.ukim.finki.moviesapi.service;

import java.io.IOException;
import mk.ukim.finki.moviesapi.model.rest.InitializationDataDto;
import org.springframework.security.core.Authentication;

public interface InitializationDataService {

  InitializationDataDto createInitializationData(Authentication authentication) throws IOException;
}
