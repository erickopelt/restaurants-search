package io.opelt.restaurantsearch.adapter.output;

import io.opelt.restaurantsearch.domain.Cuisine;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CuisineRepository {

  private final CuisineFileReader cuisineFileReader;

  public CuisineRepository(
      CuisineFileReader cuisineFileReader) {
    this.cuisineFileReader = cuisineFileReader;
  }

  public Optional<Cuisine> getCuisine(long id) {
    return cuisineFileReader.getAll().stream()
        .filter(cuisine -> cuisine.getId() == id)
        .findAny();
  }
}
