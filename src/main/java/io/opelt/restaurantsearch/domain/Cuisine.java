package io.opelt.restaurantsearch.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class Cuisine {

  private final long id;
  private final String name;
}
