package io.opelt.restaurantsearch.usecase.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class MaxPriceFilterTest {

  @Test
  void givenAMaxPriceAndAMatchingRestaurantWhenFilterThenReturnTrue() {
    final var price = BigInteger.valueOf(20);
    final var restaurant = Restaurant.builder()
        .price(BigInteger.valueOf(15))
        .build();
    final var filter = new MaxPriceFilter(price);

    assertThat(filter.test(restaurant)).isTrue();
  }

  @Test
  void givenAMaxPriceAndANonMatchingRestaurantWhenFilterThenReturnFalse() {
    final var price = BigInteger.valueOf(20);
    final var restaurant = Restaurant.builder()
        .price(BigInteger.valueOf(25))
        .build();
    final var filter = new MaxPriceFilter(price);

    assertThat(filter.test(restaurant)).isFalse();
  }

  @Test
  void givenAPriceGreaterThanMaxBoundaryWhenCreateFilterThenThrowException() {
    assertThatThrownBy(() -> new MaxPriceFilter(BigInteger.valueOf(51)))
        .isExactlyInstanceOf(InvalidFilterException.class)
        .hasMessage("Invalid filter on field %s due to constraint: %s", "price",
            "value must be between 10 and 50");
  }

  @Test
  void givenAPriceLesserThanMinBoundaryWhenCreateFilterThenThrowException() {
    assertThatThrownBy(() -> new MaxPriceFilter(BigInteger.valueOf(9)))
        .isExactlyInstanceOf(InvalidFilterException.class)
        .hasMessage("Invalid filter on field %s due to constraint: %s", "price",
            "value must be between 10 and 50");
  }
}