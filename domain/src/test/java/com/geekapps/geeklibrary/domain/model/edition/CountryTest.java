package com.geekapps.geeklibrary.domain.model.edition;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Country Tests")
class CountryTest {

  @Test
  @DisplayName("Should create country with valid name and ISO code")
  void shouldCreateCountryWithValidNameAndIsoCode() {
    // Given
    final String name = "Spain";
    final String isoCode = "ES";

    // When
    final Country country = new Country(name, isoCode);

    // Then
    Assertions.assertThat(country.name()).isEqualTo("Spain");
    Assertions.assertThat(country.isoCode()).isEqualTo("ES");
  }

  @Test
  @DisplayName("Should throw exception when name is null")
  void shouldThrowExceptionWhenNameIsNull() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Country(null, "US"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Country name cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when name is empty")
  void shouldThrowExceptionWhenNameIsEmpty() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Country("", "US"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Country name cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when name is blank")
  void shouldThrowExceptionWhenNameIsBlank() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Country("   ", "US"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Country name cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when ISO code is null")
  void shouldThrowExceptionWhenIsoCodeIsNull() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Country("United States", null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ISO code cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when ISO code is empty")
  void shouldThrowExceptionWhenIsoCodeIsEmpty() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Country("United States", ""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ISO code cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when ISO code format is invalid")
  void shouldThrowExceptionWhenIsoCodeFormatIsInvalid() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Country("Spain", "ESP"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("ISO 3166-1 alpha-2 format");

    Assertions.assertThatThrownBy(() -> new Country("Spain", "E"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("ISO 3166-1 alpha-2 format");

    Assertions.assertThatThrownBy(() -> new Country("Spain", "es"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("ISO 3166-1 alpha-2 format");
  }

  @Test
  @DisplayName("Should be equal when name and ISO code match")
  void shouldBeEqualWhenNameAndIsoCodeMatch() {
    // Given
    final Country country1 = new Country("Japan", "JP");
    final Country country2 = new Country("Japan", "JP");

    // Then
    Assertions.assertThat(country1).isEqualTo(country2);
    Assertions.assertThat(country1.hashCode()).isEqualTo(country2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal when countries differ")
  void shouldNotBeEqualWhenCountriesDiffer() {
    // Given
    final Country country1 = new Country("Japan", "JP");
    final Country country2 = new Country("United States", "US");

    // Then
    Assertions.assertThat(country1).isNotEqualTo(country2);
  }

  @Test
  @DisplayName("Should convert to string with name and ISO code")
  void shouldConvertToStringWithNameAndIsoCode() {
    // Given
    final Country country = new Country("France", "FR");

    // When
    final String result = country.toString();

    // Then
    Assertions.assertThat(result).isEqualTo("France (FR)");
  }
}
