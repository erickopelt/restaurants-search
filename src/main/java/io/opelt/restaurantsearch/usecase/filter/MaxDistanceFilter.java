package io.opelt.restaurantsearch.usecase.filter;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import java.util.function.Predicate;

public class MaxDistanceFilter implements Predicate<Restaurant> {

  private final double maxDistance;

  public MaxDistanceFilter(double maxDistance) {
    if (maxDistance < 1 || maxDistance > 10) {
      throw new InvalidFilterException("distance", "value must be between 1 and 10");
    }
    this.maxDistance = maxDistance;
  }

  @Override
  public boolean test(Restaurant restaurant) {
    return restaurant.getDistance() <= maxDistance;
  }
}
