package com.geekapps.geeklibrary.domain.exception;

public class InvalidDimensionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvalidDimensionException(final String dimensionName) {
    super(String.format("%s must be positive", dimensionName));
  }

  public InvalidDimensionException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
