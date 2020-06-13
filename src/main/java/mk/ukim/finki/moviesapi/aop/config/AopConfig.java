package mk.ukim.finki.moviesapi.aop.config;

import mk.ukim.finki.moviesapi.aop.aspect.MoviesAspect;
import mk.ukim.finki.moviesapi.service.MoviesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

  @Bean
  public MoviesAspect moviesAdvice(MoviesService moviesService) {
    return new MoviesAspect(moviesService);
  }
}
