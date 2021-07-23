package io.opelt.restaurantsearch.adapter.output.exception;

public class CuisineNotFoundException extends RuntimeException {

  public CuisineNotFoundException(long id) {
    super(String.format("Cuisine with id %s not found", id));
  }
}
