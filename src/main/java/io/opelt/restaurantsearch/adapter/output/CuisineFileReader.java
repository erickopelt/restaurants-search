package io.opelt.restaurantsearch.adapter.output;

import io.opelt.restaurantsearch.adapter.output.exception.ErrorReadingFileException;
import io.opelt.restaurantsearch.domain.Cuisine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CuisineFileReader {

  private final Resource cuisineResource;

  public CuisineFileReader(@Value("${cuisines.file}") Resource cuisineResource) {
    this.cuisineResource = cuisineResource;
  }

  @Cacheable("cuisines")
  public List<Cuisine> getAll() {
    try {
      final var lines = new BufferedReader(
          new InputStreamReader(cuisineResource.getInputStream())).lines()
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

  private Cuisine map(String line, Header header) {
    final var values = line.split(",");
    return Cuisine.builder()
        .id(Long.parseLong(values[header.getFieldPosition("id")]))
        .name(values[header.getFieldPosition("name")])
        .build();
  }

}
