package io.opelt.restaurantsearch.usecase.filter;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import java.util.function.Predicate;

public class ContainsCuisineFilter implements Predicate<Restaurant> {

  private final String cuisine;

  public ContainsCuisineFilter(String cuisine) {
    if (cuisine == null) {
      throw new InvalidFilterException("cuisine", "value must not be null");
    }
    this.cuisine = cuisine;
  }

  @Override
  public boolean test(Restaurant restaurant) {
    return restaurant.getCuisine().getName().contains(cuisine);
  }
}
