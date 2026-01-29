package com.geekapps.geeklibrary.domain.model.edition;

public record Language(String isoCode) {

  public Language {
    if (isoCode == null || isoCode.isBlank()) {
      throw new IllegalArgumentException("ISO code cannot be null or empty");
    }
    if (!isoCode.matches("^[a-z]{2}(-[A-Z]{2})?$")) {
      throw new IllegalArgumentException(
          "ISO code must follow ISO 639-1 format (e.g., 'en', 'es', 'ja')");
    }
  }

  @Override
  public String toString() {
    return this.isoCode;
  }
}
