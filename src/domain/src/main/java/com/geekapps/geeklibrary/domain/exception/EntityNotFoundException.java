package com.geekapps.geeklibrary.domain.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public EntityNotFoundException(final String entityName, final UUID id) {
    super(String.format("%s with id %s not found", entityName, id));
  }

  public EntityNotFoundException(final String message) {
    super(message);
  }

}
