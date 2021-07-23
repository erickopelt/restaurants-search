package io.opelt.restaurantsearch.usecase.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import org.junit.jupiter.api.Test;

class MinimalCustomerRatingFilterTest {

  @Test
  void givenAMinRatingAndAMatchingRestaurantWhenFilterThenReturnTrue() {
    final var rating = 4;
    final var restaurant = Restaurant.builder()
        .customerRating(4)
        .build();
    final var filter = new MinimalCustomerRatingFilter(rating);

    assertThat(filter.test(restaurant)).isTrue();
  }

  @Test
  void givenAManRatingAndANonMatchingRestaurantWhenFilterThenReturnFalse() {
    final var rating = 4;
    final var restaurant = Restaurant.builder()
        .customerRating(3)
        .build();
    final var filter = new MinimalCustomerRatingFilter(rating);

    assertThat(filter.test(restaurant)).isFalse();
  }

  @Test
  void givenARatingGreaterThanMaxBoundaryWhenCreateFilterThenThrowException() {
    assertThatThrownBy(() -> new MinimalCustomerRatingFilter(6))
        .isExactlyInstanceOf(InvalidFilterException.class)
        .hasMessage("Invalid filter on field %s due to constraint: %s", "customerRating",
            "value must be between 0 and 5");
  }

  @Test
  void givenARatingLesserThanMinBoundaryWhenCreateFilterThenThrowException() {
    assertThatThrownBy(() -> new MinimalCustomerRatingFilter(-1))
        .isExactlyInstanceOf(InvalidFilterException.class)
        .hasMessage("Invalid filter on field %s due to constraint: %s", "customerRating",
            "value must be between 0 and 5");
  }

}