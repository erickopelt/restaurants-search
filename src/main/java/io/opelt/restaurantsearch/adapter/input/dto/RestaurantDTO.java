package io.opelt.restaurantsearch.adapter.input.dto;

import io.opelt.restaurantsearch.domain.Restaurant;
import java.math.BigInteger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class RestaurantDTO {

  private final String name;
  private final BigInteger price;
  private final double distance;
  private final int customerRating;
  private final String cuisine;

  private RestaurantDTO(String name, BigInteger price, double distance, int customerRating,
      String cuisine) {
    this.name = name;
    this.price = price;
    this.distance = distance;
    this.customerRating = customerRating;
    this.cuisine = cuisine;
  }

  public static RestaurantDTO of(Restaurant restaurant) {
    return new RestaurantDTO(restaurant.getName(),
        restaurant.getPrice(),
        restaurant.getDistance(),
        restaurant.getCustomerRating(),
        restaurant.getCuisine().getName());
  }

}
