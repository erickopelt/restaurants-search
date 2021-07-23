package io.opelt.restaurantsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestaurantSearchApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestaurantSearchApplication.class, args);
  }

}
