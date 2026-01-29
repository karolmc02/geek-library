package com.geekapps.geeklibrary.domain.model.edition;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Language Tests")
class LanguageTest {

  @Test
  @DisplayName("Should create language with valid ISO code")
  void shouldCreateLanguageWithValidIsoCode() {
    // Given
    final String isoCode = "es";

    // When
    final Language language = new Language(isoCode);

    // Then
    Assertions.assertThat(language.isoCode()).isEqualTo("es");
    Assertions.assertThat(language.toString()).isEqualTo("es");
  }

  @Test
  @DisplayName("Should create language with valid ISO code and region")
  void shouldCreateLanguageWithValidIsoCodeAndRegion() {
    // Given
    final String isoCode = "en-US";

    // When
    final Language language = new Language(isoCode);

    // Then
    Assertions.assertThat(language.isoCode()).isEqualTo("en-US");
  }

  @Test
  @DisplayName("Should throw exception when ISO code is null")
  void shouldThrowExceptionWhenIsoCodeIsNull() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Language(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ISO code cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when ISO code is empty")
  void shouldThrowExceptionWhenIsoCodeIsEmpty() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Language(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ISO code cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when ISO code is blank")
  void shouldThrowExceptionWhenIsoCodeIsBlank() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Language("   "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ISO code cannot be null or empty");
  }

  @Test
  @DisplayName("Should throw exception when ISO code format is invalid")
  void shouldThrowExceptionWhenIsoCodeFormatIsInvalid() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Language("ESP"))
        .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("ISO 639-1 format");

    Assertions.assertThatThrownBy(() -> new Language("e"))
        .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("ISO 639-1 format");

    Assertions.assertThatThrownBy(() -> new Language("EN"))
        .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("ISO 639-1 format");
  }

  @Test
  @DisplayName("Should be equal when ISO codes match")
  void shouldBeEqualWhenIsoCodesMatch() {
    // Given
    final Language language1 = new Language("ja");
    final Language language2 = new Language("ja");

    // Then
    Assertions.assertThat(language1).isEqualTo(language2);
    Assertions.assertThat(language1.hashCode()).isEqualTo(language2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal when ISO codes differ")
  void shouldNotBeEqualWhenIsoCodesDiffer() {
    // Given
    final Language language1 = new Language("ja");
    final Language language2 = new Language("en");

    // Then
    Assertions.assertThat(language1).isNotEqualTo(language2);
  }

  @Test
  @DisplayName("Should convert to string correctly")
  void shouldConvertToStringCorrectly() {
    // Given
    final Language language = new Language("fr");

    // When
    final String result = language.toString();

    // Then
    Assertions.assertThat(result).isEqualTo("fr");
  }
}
