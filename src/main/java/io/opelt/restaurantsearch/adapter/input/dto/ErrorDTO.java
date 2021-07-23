package io.opelt.restaurantsearch.adapter.input.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class ErrorDTO {

  private final int status;
  private final String code;
  private final String description;
}
