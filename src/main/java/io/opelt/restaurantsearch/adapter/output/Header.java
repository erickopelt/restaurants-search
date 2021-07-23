package io.opelt.restaurantsearch.adapter.output;

import io.opelt.restaurantsearch.adapter.output.exception.FieldNotFoundOnFileHeaderException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Header {

  private final Map<String, Integer> fields = new HashMap<>();

  private Header() {
  }

  public int getFieldPosition(String field) {
    return Optional.ofNullable(fields.get(field))
        .orElseThrow(() -> new FieldNotFoundOnFileHeaderException(field));
  }

  public static Header fromLine(String line) {
    final var headers = new Header();
    final var headerValues = line.split(",");
    for (int i = 0; i < headerValues.length; i++) {
      headers.fields.put(headerValues[i], i);
    }
    return headers;
  }

}
