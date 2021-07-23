package io.opelt.restaurantsearch.usecase;

import static io.opelt.restaurantsearch.usecase.SearchRestaurantsUseCase.DEFAULT_COMPARATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.opelt.restaurantsearch.domain.Restaurant;
import io.opelt.restaurantsearch.usecase.filter.ContainsNameFilter;
import io.opelt.restaurantsearch.usecase.filter.MaxDistanceFilter;
import io.opelt.restaurantsearch.usecase.port.RestaurantRepository;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class SearchRestaurantsUseCaseTest {

  private final int limit = 1;
  private final RestaurantRepository repository = mock(RestaurantRepository.class);
  private final SearchRestaurantsUseCase searchRestaurantsUseCase =
      new SearchRestaurantsUseCase(repository, limit);

  @Test
  void givenARestaurantWhenCompareThenOrderByDistance() {
    final var firstRestaurant = Restaurant.builder()
        .distance(10)
        .build();
    final var secondRestaurant = Restaurant.builder()
        .distance(20)
        .build();

    assertThat(DEFAULT_COMPARATOR.compare(firstRestaurant, secondRestaurant)).isLessThan(0);
  }

  @Test
  void givenARestaurantWhenCompareThenOrderByCustomerRating() {
    final var firstRestaurant = Restaurant.builder()
        .distance(10)
        .customerRating(5)
        .build();
    final var secondRestaurant = Restaurant.builder()
        .distance(10)
        .customerRating(4)
        .build();

    assertThat(DEFAULT_COMPARATOR.compare(firstRestaurant, secondRestaurant)).isLessThan(0);
  }

  @Test
  void givenARestaurantWhenCompareThenOrderByPrice() {
    final var firstRestaurant = Restaurant.builder()
        .distance(10)
        .customerRating(5)
        .price(BigInteger.ONE)
        .build();
    final var secondRestaurant = Restaurant.builder()
        .distance(10)
        .customerRating(5)
        .price(BigInteger.TWO)
        .build();

    assertThat(DEFAULT_COMPARATOR.compare(firstRestaurant, secondRestaurant)).isLessThan(0);
  }
  @Test
  void givenARestaurantWhenCompareThenOrderByName() {
    final var firstRestaurant = Restaurant.builder()
        .distance(10)
        .customerRating(5)
        .price(BigInteger.ONE)
        .name("burguer king")
        .build();
    final var secondRestaurant = Restaurant.builder()
        .distance(10)
        .customerRating(5)
        .price(BigInteger.ONE)
        .name("mcdonald's")
        .build();

    assertThat(DEFAULT_COMPARATOR.compare(firstRestaurant, secondRestaurant)).isLessThan(0);
  }

  @Test
  void givenAListOfPredicatesWhenSearchThenCombineIntoAnAndPredicateAndReturnList() {
    final var restaurant = Restaurant.builder()
        .name("name")
        .distance(10)
        .build();
    final var list = List.of(restaurant);
    when(repository.find(any(Predicate.class), eq(DEFAULT_COMPARATOR), eq(limit)))
        .thenReturn(list);

    final var response = searchRestaurantsUseCase
        .search(List.of(new ContainsNameFilter("name"), new MaxDistanceFilter(10)));

    assertThat(response).isSameAs(list);

    final var predicateCaptor = ArgumentCaptor.forClass(Predicate.class);
    verify(repository).find(predicateCaptor.capture(), eq(DEFAULT_COMPARATOR), eq(limit));

    assertThat(predicateCaptor.getValue().test(restaurant)).isTrue();
  }

  @Test
  void givenAListOfPredicatesAndANotMatchingWhenSearchThenCombineIntoAnAndPredicateAndReturnList() {
    final var restaurant = Restaurant.builder()
        .name("name")
        .distance(20)
        .build();
    final var list = List.of(restaurant);
    when(repository.find(any(Predicate.class), eq(DEFAULT_COMPARATOR), eq(limit)))
        .thenReturn(list);

    searchRestaurantsUseCase
        .search(List.of(new ContainsNameFilter("name"), new MaxDistanceFilter(10)));

    final var predicateCaptor = ArgumentCaptor.forClass(Predicate.class);
    verify(repository).find(predicateCaptor.capture(), eq(DEFAULT_COMPARATOR), eq(limit));

    assertThat(predicateCaptor.getValue().test(restaurant)).isFalse();
  }
}