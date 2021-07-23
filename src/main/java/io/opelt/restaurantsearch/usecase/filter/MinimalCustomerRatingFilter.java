package io.opelt.restaurantsearch.usecase.filter;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import java.util.function.Predicate;

public class MinimalCustomerRatingFilter implements Predicate<Restaurant> {

  private final int minimalRating;

  public MinimalCustomerRatingFilter(int minimalRating) {
    if (minimalRating < 0 || minimalRating > 5) {
      throw new InvalidFilterException("customerRating", "value must be between 0 and 5");
    }
    this.minimalRating = minimalRating;
  }

  @Override
  public boolean test(Restaurant restaurant) {
    return restaurant.getCustomerRating() >= minimalRating;
  }
}
