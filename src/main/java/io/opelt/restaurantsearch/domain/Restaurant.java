package io.opelt.restaurantsearch.domain;

import java.math.BigInteger;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class Restaurant {
  private final String name;
  private final int customerRating;
  private final double distance;
  private final Cuisine cuisine;
  private final BigInteger price;
}
