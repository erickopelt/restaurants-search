package io.opelt.restaurantsearch.adapter.input.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.opelt.restaurantsearch.domain.Cuisine;
import io.opelt.restaurantsearch.domain.Restaurant;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class RestaurantDTOTest {

  @Test
  void givenARestaurantWhenMapThenReturnDto() {
    final var restaurant = Restaurant.builder()
        .distance(1)
        .price(BigInteger.ONE)
        .name("name")
        .customerRating(3)
        .cuisine(Cuisine.builder()
            .name("cuisine")
            .build())
        .build();

    final var dto = RestaurantDTO.of(restaurant);

    assertThat(dto.getName()).isEqualTo(restaurant.getName());
    assertThat(dto.getCuisine()).isEqualTo(restaurant.getCuisine().getName());
    assertThat(dto.getDistance()).isEqualTo(restaurant.getDistance());
    assertThat(dto.getPrice()).isEqualTo(restaurant.getPrice());
    assertThat(dto.getCustomerRating()).isEqualTo(restaurant.getCustomerRating());
  }

}