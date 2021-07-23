package io.opelt.restaurantsearch.usecase.port;

import io.opelt.restaurantsearch.domain.Restaurant;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface RestaurantRepository {

  List<Restaurant> find(Predicate<Restaurant> predicate, Comparator<Restaurant> sort, int limit);
}
