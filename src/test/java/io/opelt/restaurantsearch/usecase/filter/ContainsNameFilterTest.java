package io.opelt.restaurantsearch.usecase.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import org.junit.jupiter.api.Test;

class ContainsNameFilterTest {

  @Test
  void givenANameValueAndAMatchingRestaurantWhenFilterThenReturnTrue() {
    final var name = "Mc";
    final var restaurant = Restaurant.builder()
        .name("McDonald's")
        .build();
    final var filter = new ContainsNameFilter(name);

    assertThat(filter.test(restaurant)).isTrue();
  }

  @Test
  void givenANameValueAndANonMatchingRestaurantWhenFilterThenReturnFalse() {
    final var name = "Mc";
    final var restaurant = Restaurant.builder()
        .name("Burguer King")
        .build();
    final var filter = new ContainsNameFilter(name);

    assertThat(filter.test(restaurant)).isFalse();
  }

  @Test
  void givenANullValueWhenCreateFilterThenThrowException() {
    assertThatThrownBy(() -> new ContainsNameFilter(null))
        .isExactlyInstanceOf(InvalidFilterException.class)
        .hasMessage("Invalid filter on field %s due to constraint: %s", "name",
            "value must not be null");
  }

}