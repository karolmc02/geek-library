package com.geekapps.geeklibrary.domain.exception;

public class InvalidWorkPreviewImportException extends RuntimeException {
  public InvalidWorkPreviewImportException(final String message) {
    super(message);
  }

  public InvalidWorkPreviewImportException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
