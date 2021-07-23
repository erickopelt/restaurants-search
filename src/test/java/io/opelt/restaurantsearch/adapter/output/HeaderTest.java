package io.opelt.restaurantsearch.adapter.output;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.opelt.restaurantsearch.adapter.output.exception.FieldNotFoundOnFileHeaderException;
import org.junit.jupiter.api.Test;

class HeaderTest {

  @Test
  void givenALineWithHeadersWhenCreateThenMapFieldsAndPositions() {
    final var line = "id,name";

    final var headers = Header.fromLine(line);

    assertThat(headers.getFieldPosition("id")).isEqualTo(0);
    assertThat(headers.getFieldPosition("name")).isEqualTo(1);
  }

  @Test
  void givenAnInvalidFieldWhenGetPositionThenThrowException() {
    final var line = "id,name";

    final var headers = Header.fromLine(line);

    assertThatThrownBy(() -> headers.getFieldPosition("price"))
        .isExactlyInstanceOf(FieldNotFoundOnFileHeaderException.class)
        .hasMessage("Field %s not found on file's header", "price");
  }

}