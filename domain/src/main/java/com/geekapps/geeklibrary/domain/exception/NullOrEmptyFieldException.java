package com.geekapps.geeklibrary.domain.exception;

public class NullOrEmptyFieldException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NullOrEmptyFieldException(final String fieldName) {
    super(String.format("%s cannot be null or empty", fieldName));
  }

  public NullOrEmptyFieldException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
