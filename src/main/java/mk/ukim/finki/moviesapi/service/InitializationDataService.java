package mk.ukim.finki.moviesapi.service;

import mk.ukim.finki.moviesapi.model.dto.InitializationData;

public interface InitializationDataService {

  InitializationData createInitializationData(String username);
}
