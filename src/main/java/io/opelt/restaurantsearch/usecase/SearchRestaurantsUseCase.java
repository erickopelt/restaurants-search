package io.opelt.restaurantsearch.usecase;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.port.RestaurantRepository;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SearchRestaurantsUseCase {

  static final Comparator<Restaurant> DEFAULT_COMPARATOR =
      Comparator.comparing(Restaurant::getDistance)
          .thenComparing(Restaurant::getCustomerRating, Comparator.reverseOrder())
          .thenComparing(Restaurant::getPrice)
          .thenComparing(Restaurant::getName);

  private final int limit;
  private final RestaurantRepository repository;

  public SearchRestaurantsUseCase(RestaurantRepository repository,
      @Value("${restaurants.search.limit}") int limit) {
    this.repository = repository;
    this.limit = limit;
  }

  public List<Restaurant> search(List<Predicate<Restaurant>> predicates) {
    return repository.find(
        predicates.stream().reduce(Predicate::and).orElse(restaurant -> true),
        DEFAULT_COMPARATOR,
        limit);
  }
}
