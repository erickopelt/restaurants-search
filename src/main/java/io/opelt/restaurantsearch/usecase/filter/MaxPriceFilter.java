package io.opelt.restaurantsearch.usecase.filter;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import java.math.BigInteger;
import java.util.function.Predicate;

public class MaxPriceFilter implements Predicate<Restaurant> {

  private final BigInteger maxPrice;

  public MaxPriceFilter(BigInteger maxPrice) {
    if (maxPrice.compareTo(BigInteger.valueOf(10)) < 0 || maxPrice.compareTo(BigInteger.valueOf(50)) > 0) {
      throw new InvalidFilterException("price", "value must be between 10 and 50");
    }
    this.maxPrice = maxPrice;
  }

  @Override
  public boolean test(Restaurant restaurant) {
    return restaurant.getPrice().compareTo(maxPrice) <= 0;
  }
}
