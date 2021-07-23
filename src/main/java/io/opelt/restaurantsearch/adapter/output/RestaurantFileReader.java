package io.opelt.restaurantsearch.adapter.output;

import io.opelt.restaurantsearch.adapter.output.exception.CuisineNotFoundException;
import io.opelt.restaurantsearch.adapter.output.exception.ErrorReadingFileException;
import io.opelt.restaurantsearch.domain.Cuisine;
import io.opelt.restaurantsearch.domain.Restaurant;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class RestaurantFileReader {

  private final Resource restaurantFileResource;
  private final CuisineRepository cuisineRepository;

  public RestaurantFileReader(@Value("${restaurants.file}") Resource restaurantFileResource,
      CuisineRepository cuisineRepository) {
    this.restaurantFileResource = restaurantFileResource;
    this.cuisineRepository = cuisineRepository;
  }

  @Cacheable("restaurants")
  public List<Restaurant> findAll() {
    try {
      final var lines = new BufferedReader(
          new InputStreamReader(restaurantFileResource.getInputStream())).lines()
          .collect(Collectors.toList());
      final var headers = Header.fromLine(lines.get(0));
      return lines.stream()
          .skip(1)
          .map(line -> map(line, headers))
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new ErrorReadingFileException(e);
    }
  }

  private Restaurant map(String line, Header header) {
    String[] values = line.split(",");
    return Restaurant.builder()
        .name(values[header.getFieldPosition("name")])
        .customerRating(Integer.parseInt(values[header.getFieldPosition("customer_rating")]))
        .distance(Double.parseDouble(values[header.getFieldPosition("distance")]))
        .price(new BigInteger(values[header.getFieldPosition("price")]))
        .cuisine(getCuisine(Long.parseLong(values[header.getFieldPosition("cuisine_id")])))
        .build();
  }

  private Cuisine getCuisine(long id) {
    return cuisineRepository.getCuisine(id)
        .orElseThrow(() -> new CuisineNotFoundException(id));
  }
}
