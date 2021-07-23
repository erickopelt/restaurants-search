package io.opelt.restaurantsearch.adapter.output;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.opelt.restaurantsearch.adapter.output.exception.CuisineNotFoundException;
import io.opelt.restaurantsearch.adapter.output.exception.ErrorReadingFileException;
import io.opelt.restaurantsearch.domain.Cuisine;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

class RestaurantFileReaderTest {

  private final CuisineRepository cuisineRepository = mock(CuisineRepository.class);

  @Test
  void givenACsvFileWhenGetAllThenMapAllRestaurants() {
    final var fileReader = new RestaurantFileReader(new ClassPathResource("restaurants.csv"),
        cuisineRepository);
    final var cuisine = Cuisine.builder()
        .id(1L)
        .name("cuisine")
        .build();
    when(cuisineRepository.getCuisine(anyLong())).thenReturn(Optional.of(cuisine));

    final var response = fileReader.findAll();

    assertThat(response).hasSize(200).first().satisfies(restaurant -> {
      assertThat(restaurant.getName()).isEqualTo("Deliciousgenix");
      assertThat(restaurant.getCustomerRating()).isEqualTo(4);
      assertThat(restaurant.getDistance()).isEqualTo(1.0);
      assertThat(restaurant.getPrice()).isEqualTo(BigInteger.valueOf(10));
      assertThat(restaurant.getCuisine()).isEqualTo(cuisine);
    });
  }

  @Test
  void givenAUnknownCsvFileWhenGetAllThenMapThrowException() {
    final var fileReader = new RestaurantFileReader(new ClassPathResource("not-found.csv"),
        cuisineRepository);

    assertThatThrownBy(fileReader::findAll)
        .isExactlyInstanceOf(ErrorReadingFileException.class)
        .hasCauseInstanceOf(IOException.class);
  }

  @Test
  void givenARestaurantWithUnknownCuisineWhenFindAllThenThrowException() {
    final var fileReader = new RestaurantFileReader(new ClassPathResource("restaurants.csv"),
        cuisineRepository);

    assertThatThrownBy(fileReader::findAll)
        .isExactlyInstanceOf(CuisineNotFoundException.class);
  }

}