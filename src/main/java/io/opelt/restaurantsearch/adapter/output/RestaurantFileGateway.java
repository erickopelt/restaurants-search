package io.opelt.restaurantsearch.adapter.output;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.port.RestaurantRepository;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RestaurantFileGateway implements RestaurantRepository {

  private final RestaurantFileReader restaurantFileReader;

  public RestaurantFileGateway(
      RestaurantFileReader restaurantFileReader) {
    this.restaurantFileReader = restaurantFileReader;
  }

  @Override
  public List<Restaurant> find(Predicate<Restaurant> predicate, Comparator<Restaurant> sort, int limit) {
    return restaurantFileReader.findAll().stream()
        .filter(predicate)
        .sorted(sort)
        .limit(limit)
        .collect(Collectors.toList());
  }
}
