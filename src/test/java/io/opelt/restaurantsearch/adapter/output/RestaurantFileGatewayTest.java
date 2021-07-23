package io.opelt.restaurantsearch.adapter.output;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.opelt.restaurantsearch.domain.Restaurant;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;

class RestaurantFileGatewayTest {

  private final RestaurantFileReader restaurantFileReader = mock(RestaurantFileReader.class);
  private final RestaurantFileGateway restaurantFileGateway = new RestaurantFileGateway(
      restaurantFileReader);

  @Test
  void givenAPredicateAndComparatorAndLimitWhenSearchThenApplyOnList() {
    final var limit = 2;
    final var restaurants = List.of(
        Restaurant.builder().name("thai").distance(15).build(),
        Restaurant.builder().name("thailand").distance(10).build(),
        Restaurant.builder().name("chinese").distance(5).build());

    when(restaurantFileReader.findAll()).thenReturn(restaurants);

    final var response = restaurantFileGateway.find(
        restaurant -> restaurant.getName().contains("thai"),
        Comparator.comparing(Restaurant::getDistance),
        limit);

    assertThat(response).containsExactly(
        Restaurant.builder().name("thailand").distance(10).build(),
        Restaurant.builder().name("thai").distance(15).build());
  }

}