package io.opelt.restaurantsearch.usecase.exception;

public class InvalidFilterException extends RuntimeException {

  public InvalidFilterException(String field, String constraint) {
    super(String.format("Invalid filter on field %s due to constraint: %s", field, constraint));
  }
}
