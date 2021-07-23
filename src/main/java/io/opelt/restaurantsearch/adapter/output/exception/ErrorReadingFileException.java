package io.opelt.restaurantsearch.adapter.output.exception;

public class ErrorReadingFileException extends RuntimeException {

  public ErrorReadingFileException(Exception cause) {
    super(cause);
  }
}
