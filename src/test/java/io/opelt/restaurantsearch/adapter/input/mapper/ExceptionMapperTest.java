package io.opelt.restaurantsearch.adapter.input.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.opelt.restaurantsearch.adapter.input.dto.ErrorDTO;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ExceptionMapperTest {

  private final ExceptionMapper exceptionMapper = new ExceptionMapper();

  @Test
  void givenAnInvalidFilterExceptionWhenMapThenReturnResponseEntity() {
    final var exception = new InvalidFilterException("field", "must not be null");

    final var response = exceptionMapper.handleInvalidFilterException(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isExactlyInstanceOf(ErrorDTO.class).satisfies(body -> {
      final var errorDto = (ErrorDTO) body;
      assertThat(errorDto.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
      assertThat(errorDto.getCode()).isEqualTo(exception.getClass().getSimpleName());
      assertThat(errorDto.getDescription()).isEqualTo(exception.getMessage());
    });
  }

  @Test
  void givenAnUnmappedExceptionWhenMapThenReturnResponseEntity() {
    final var exception = new RuntimeException("error message");

    final var response = exceptionMapper.handleExceptionInternal(exception, null, null, null, null);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isExactlyInstanceOf(ErrorDTO.class).satisfies(body -> {
      final var errorDto = (ErrorDTO) body;
      assertThat(errorDto.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
      assertThat(errorDto.getCode()).isEqualTo(exception.getClass().getSimpleName());
      assertThat(errorDto.getDescription()).isEqualTo(exception.getMessage());
    });
  }
}