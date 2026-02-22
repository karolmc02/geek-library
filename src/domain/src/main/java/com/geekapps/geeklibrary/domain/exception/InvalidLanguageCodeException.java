package com.geekapps.geeklibrary.domain.exception;

public class InvalidLanguageCodeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvalidLanguageCodeException(final String isoCode) {
    super(String.format(
        "Invalid language ISO code '%s'. Must follow ISO 639-1 format (e.g., 'en', 'es', 'ja')",
        isoCode));
  }

  public InvalidLanguageCodeException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
