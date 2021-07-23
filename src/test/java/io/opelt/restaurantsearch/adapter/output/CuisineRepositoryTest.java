package io.opelt.restaurantsearch.adapter.output;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.opelt.restaurantsearch.domain.Cuisine;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CuisineRepositoryTest {

  private final CuisineFileReader fileReader = mock(CuisineFileReader.class);
  private final CuisineRepository repository = new CuisineRepository(fileReader);

  @Test
  void givenAnIdWhenSearchCuisineThenReturnCuisine() {
    final var cuisine = Cuisine.builder()
        .id(1L)
        .build();
    when(fileReader.getAll()).thenReturn(List.of(cuisine));

    assertThat(repository.getCuisine(1L)).isNotEmpty().get().isEqualTo(cuisine);
  }


  @Test
  void givenAnUnknownIdWhenSearchCuisineThenReturnEmpty() {
    final var cuisine = Cuisine.builder()
        .id(2L)
        .build();
    when(fileReader.getAll()).thenReturn(List.of(cuisine));

    assertThat(repository.getCuisine(1L)).isEmpty();
  }
}