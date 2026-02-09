package com.geekapps.geeklibrary.domain.model.edition;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Country Tests")
class CountryTest {

  @Test
  @DisplayName("Should create country with valid ISO code")
  void shouldCreateCountryWithValidIsoCode() {
    // Given
    final String isoCode = "ES";

    // When
    final Country country = new Country(isoCode);

    // Then
    Assertions.assertThat(country.isoCode()).isEqualTo("ES");
  }

  @Test
  @DisplayName("Should throw exception when ISO code is null")
  void shouldThrowExceptionWhenIsoCodeIsNull() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Country(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ISO code cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when ISO code is empty")
  void shouldThrowExceptionWhenIsoCodeIsEmpty() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Country(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ISO code cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when ISO code format is invalid")
  void shouldThrowExceptionWhenIsoCodeFormatIsInvalid() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Country("ESP"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("ISO 3166-1 alpha-2 format");

    Assertions.assertThatThrownBy(() -> new Country("E"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("ISO 3166-1 alpha-2 format");

    Assertions.assertThatThrownBy(() -> new Country("es"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("ISO 3166-1 alpha-2 format");
  }

  @Test
  @DisplayName("Should be equal when ISO code matches")
  void shouldBeEqualWhenIsoCodeMatches() {
    // Given
    final Country country1 = new Country("JP");
    final Country country2 = new Country("JP");

    // Then
    Assertions.assertThat(country1).isEqualTo(country2);
    Assertions.assertThat(country1.hashCode()).isEqualTo(country2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal when countries differ")
  void shouldNotBeEqualWhenCountriesDiffer() {
    // Given
    final Country country1 = new Country("JP");
    final Country country2 = new Country("US");

    // Then
    Assertions.assertThat(country1).isNotEqualTo(country2);
  }

  @Test
  @DisplayName("Should convert to string with ISO code")
  void shouldConvertToStringWithIsoCode() {
    // Given
    final Country country = new Country("FR");

    // When
    final String result = country.toString();

    // Then
    Assertions.assertThat(result).isEqualTo("FR");
  }
}
