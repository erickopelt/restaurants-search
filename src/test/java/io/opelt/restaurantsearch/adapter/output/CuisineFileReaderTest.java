package io.opelt.restaurantsearch.adapter.output;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.opelt.restaurantsearch.adapter.output.exception.ErrorReadingFileException;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

class CuisineFileReaderTest {

  @Test
  void givenACsvFileWhenReadAllThenGetCuisineList() {
    final var cuisineFileReader = new CuisineFileReader(
        new ClassPathResource("cuisines.csv"));
    final var response = cuisineFileReader.getAll();
    assertThat(response).hasSize(19).first().satisfies(cuisine -> {
      assertThat(cuisine.getId()).isEqualTo(1L);
      assertThat(cuisine.getName()).isEqualTo("American");
    });
  }

  @Test
  void givenAnInvalidCsvFileWhenReadAllThenThrowException() {
    final var cuisineFileReader = new CuisineFileReader(
        new ClassPathResource("not-found.csv"));
    assertThatThrownBy(cuisineFileReader::getAll)
        .isExactlyInstanceOf(ErrorReadingFileException.class)
        .hasCauseInstanceOf(IOException.class);
  }
}