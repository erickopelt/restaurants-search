package io.opelt.restaurantsearch.usecase.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class InvalidFilterExceptionTest {

  @Test
  void givenFieldAndMessageWhenCreateExceptionThenReturnFormattedMessage() {
    final var field = "name";
    final var message = "must not be null";

    final var exception = new InvalidFilterException(field, message);

    assertThat(exception.getMessage()).isEqualTo(
        String.format("Invalid filter on field %s due to constraint: %s", field, message));
  }

}