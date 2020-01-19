package mk.ukim.finki.moviesapi.config;

import mk.ukim.finki.moviesapi.aop.MoviesAdvice;
import mk.ukim.finki.moviesapi.service.MoviesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

  @Bean
  public MoviesAdvice moviesAdvice(MoviesService moviesService) {
    return new MoviesAdvice(moviesService);
  }
}
