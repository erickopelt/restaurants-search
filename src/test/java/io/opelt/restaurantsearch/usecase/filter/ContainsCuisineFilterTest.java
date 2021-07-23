package io.opelt.restaurantsearch.usecase.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.opelt.restaurantsearch.domain.Cuisine;
import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import org.junit.jupiter.api.Test;

class ContainsCuisineFilterTest {

  @Test
  void givenACuisineValueAndAMatchingRestaurantWhenFilterThenReturnTrue() {
    final var cuisine = "Brazil";
    final var restaurant = Restaurant.builder()
        .cuisine(Cuisine.builder()
            .name("South of Brazil")
            .build())
        .build();
    final var filter = new ContainsCuisineFilter(cuisine);

    assertThat(filter.test(restaurant)).isTrue();
  }

  @Test
  void givenACuisineValueAndANonMatchingRestaurantWhenFilterThenReturnFalse() {
    final var cuisine = "Brazil";
    final var restaurant = Restaurant.builder()
        .cuisine(Cuisine.builder()
            .name("South of America")
            .build())
        .build();
    final var filter = new ContainsCuisineFilter(cuisine);

    assertThat(filter.test(restaurant)).isFalse();
  }

  @Test
  void givenANullValueWhenCreateFilterThenThrowException() {
    assertThatThrownBy(() -> new ContainsCuisineFilter(null))
        .isExactlyInstanceOf(InvalidFilterException.class)
        .hasMessage("Invalid filter on field %s due to constraint: %s", "cuisine",
            "value must not be null");
  }
}