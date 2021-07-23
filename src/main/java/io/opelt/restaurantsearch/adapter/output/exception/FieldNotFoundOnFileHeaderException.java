package io.opelt.restaurantsearch.adapter.output.exception;

public class FieldNotFoundOnFileHeaderException extends RuntimeException {

  public FieldNotFoundOnFileHeaderException(String field) {
    super(String.format("Field %s not found on file's header", field));
  }
}
