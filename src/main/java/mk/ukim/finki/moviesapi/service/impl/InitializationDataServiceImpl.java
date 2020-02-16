package mk.ukim.finki.moviesapi.service.impl;

import mk.ukim.finki.moviesapi.factory.UserFactory;
import mk.ukim.finki.moviesapi.model.dto.InitializationDataDto;
import mk.ukim.finki.moviesapi.model.dto.UserDto;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.service.InitializationDataService;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class InitializationDataServiceImpl implements InitializationDataService {

  private UsersService usersService;
  private UserFactory userFactory;

  public InitializationDataServiceImpl(UsersService usersService, UserFactory userFactory) {
    this.usersService = usersService;
    this.userFactory = userFactory;
  }

  @Override
  public InitializationDataDto createInitializationData(String username) {
    UserEntity userEntity = usersService.getUser(username);
    UserDto user = userFactory.createUserDto(userEntity);

    return new InitializationDataDto(user);
  }
}
