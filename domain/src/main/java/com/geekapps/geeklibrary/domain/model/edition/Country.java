package com.geekapps.geeklibrary.domain.model.edition;

public record Country(String name, String isoCode) {

  public Country {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Country name cannot be null or empty");
    }
    if (isoCode == null || isoCode.isBlank()) {
      throw new IllegalArgumentException("ISO code cannot be null or empty");
    }
    if (!isoCode.matches("^[A-Z]{2}$")) {
      throw new IllegalArgumentException(
          "ISO code must follow ISO 3166-1 alpha-2 format (e.g., 'US', 'ES', 'JP')");
    }
  }

  @Override
  public String toString() {
    return this.name + " (" + this.isoCode + ")";
  }
}
