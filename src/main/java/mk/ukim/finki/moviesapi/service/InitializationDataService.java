package mk.ukim.finki.moviesapi.service;

import mk.ukim.finki.moviesapi.model.dto.InitializationDataDto;

public interface InitializationDataService {

  InitializationDataDto createInitializationData(String username);
}
