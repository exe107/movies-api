package mk.ukim.finki.moviesapi.service.impl;

import mk.ukim.finki.moviesapi.mapper.UsersMapper;
import mk.ukim.finki.moviesapi.model.dto.InitializationDataDto;
import mk.ukim.finki.moviesapi.model.dto.UserDto;
import mk.ukim.finki.moviesapi.model.jpa.UserEntity;
import mk.ukim.finki.moviesapi.service.InitializationDataService;
import mk.ukim.finki.moviesapi.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class InitializationDataServiceImpl implements InitializationDataService {

  private UsersService usersService;
  private UsersMapper usersMapper;

  public InitializationDataServiceImpl(UsersService usersService, UsersMapper usersMapper) {
    this.usersService = usersService;
    this.usersMapper = usersMapper;
  }

  @Override
  public InitializationDataDto createInitializationData(String username) {
    UserEntity userEntity = usersService.getUser(username);
    UserDto user = usersMapper.mapToUser(userEntity);

    return new InitializationDataDto(user);
  }
}
