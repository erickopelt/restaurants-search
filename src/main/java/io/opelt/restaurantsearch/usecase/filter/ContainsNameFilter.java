package io.opelt.restaurantsearch.usecase.filter;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import java.util.function.Predicate;

public class ContainsNameFilter implements Predicate<Restaurant> {

  private final String name;

  public ContainsNameFilter(String name) {
    if (name == null) {
      throw new InvalidFilterException("name", "value must not be null");
    }
    this.name = name;
  }

  @Override
  public boolean test(Restaurant restaurant) {
    return restaurant.getName().contains(name);
  }
}
