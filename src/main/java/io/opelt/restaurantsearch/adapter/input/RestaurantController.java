package io.opelt.restaurantsearch.adapter.input;

import io.opelt.restaurantsearch.adapter.input.dto.RestaurantDTO;
import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.SearchRestaurantsUseCase;
import io.opelt.restaurantsearch.usecase.filter.ContainsCuisineFilter;
import io.opelt.restaurantsearch.usecase.filter.ContainsNameFilter;
import io.opelt.restaurantsearch.usecase.filter.MaxDistanceFilter;
import io.opelt.restaurantsearch.usecase.filter.MaxPriceFilter;
import io.opelt.restaurantsearch.usecase.filter.MinimalCustomerRatingFilter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

  private final SearchRestaurantsUseCase searchRestaurants;

  public RestaurantController(
      SearchRestaurantsUseCase searchRestaurants) {
    this.searchRestaurants = searchRestaurants;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<RestaurantDTO>> findRestaurants(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Double distance,
      @RequestParam(required = false) Integer customerRating,
      @RequestParam(required = false) String cuisine,
      @RequestParam(required = false) BigInteger price
  ) {
    final var filters = new ArrayList<Predicate<Restaurant>>();
    if (name != null) {
      filters.add(new ContainsNameFilter(name));
    }
    if (distance != null) {
      filters.add(new MaxDistanceFilter(distance));
    }
    if (customerRating != null) {
      filters.add(new MinimalCustomerRatingFilter(customerRating));
    }
    if (cuisine != null) {
      filters.add(new ContainsCuisineFilter(cuisine));
    }
    if (price != null) {
      filters.add(new MaxPriceFilter(price));
    }
    final var restaurants = searchRestaurants.search(filters).stream()
        .map(RestaurantDTO::of)
        .collect(Collectors.toList());
    return ResponseEntity.ok(restaurants);
  }
}
