package io.opelt.restaurantsearch.adapter.input.mapper;

import io.opelt.restaurantsearch.adapter.input.dto.ErrorDTO;
import io.opelt.restaurantsearch.usecase.exception.InvalidFilterException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionMapper extends ResponseEntityExceptionHandler {

  @ExceptionHandler(InvalidFilterException.class)
  public ResponseEntity<Object> handleInvalidFilterException(InvalidFilterException e) {
    return ResponseEntity.badRequest().body(ErrorDTO.builder()
        .status(HttpStatus.BAD_REQUEST.value())
        .code(e.getClass().getSimpleName())
        .description(e.getMessage())
        .build());
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return ResponseEntity.internalServerError().body(ErrorDTO.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .code(ex.getClass().getSimpleName())
        .description(ex.getMessage())
        .build());
  }
}
