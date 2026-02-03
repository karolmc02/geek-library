package com.geekapps.geeklibrary.domain.exception;

public class InvalidCountryCodeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvalidCountryCodeException(final String isoCode) {
    super(String.format(
        "Invalid country ISO code '%s'. Must follow ISO 3166-1 alpha-2 format (e.g., 'US', 'ES', 'JP')",
        isoCode));
  }

  public InvalidCountryCodeException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
