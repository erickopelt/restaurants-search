package io.opelt.restaurantsearch.usecase.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import org.junit.jupiter.api.Test;

class MaxDistanceFilterTest {

  @Test
  void givenAMaxDistanceAndAMatchingRestaurantWhenFilterThenReturnTrue() {
    final var distance = 9.0;
    final var restaurant = Restaurant.builder()
        .distance(8.0)
        .build();
    final var filter = new MaxDistanceFilter(distance);

    assertThat(filter.test(restaurant)).isTrue();
  }

  @Test
  void givenAMaxDistanceAndANonMatchingRestaurantWhenFilterThenReturnFalse() {
    final var distance = 9.0;
    final var restaurant = Restaurant.builder()
        .distance(10.0)
        .build();
    final var filter = new MaxDistanceFilter(distance);

    assertThat(filter.test(restaurant)).isFalse();
  }

  @Test
  void givenADistanceGreaterThanMaxBoundaryWhenCreateFilterThenThrowException() {
    assertThatThrownBy(() -> new MaxDistanceFilter(11))
        .isExactlyInstanceOf(InvalidFilterException.class)
        .hasMessage("Invalid filter on field %s due to constraint: %s", "distance",
            "value must be between 1 and 10");
  }

  @Test
  void givenADistanceLesserThanMinBoundaryWhenCreateFilterThenThrowException() {
    assertThatThrownBy(() -> new MaxDistanceFilter(0))
        .isExactlyInstanceOf(InvalidFilterException.class)
        .hasMessage("Invalid filter on field %s due to constraint: %s", "distance",
            "value must be between 1 and 10");
  }
}