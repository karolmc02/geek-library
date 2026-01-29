package com.geekapps.geeklibrary.domain.model.edition;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Format Tests")
class FormatTest {

  @Test
  @DisplayName("Should create format with UUID")
  void shouldCreateFormatWithUuid() {
    // Given
    final UUID id = UUID.randomUUID();
    final String name = "Tankobon";
    final String description = "Standard manga format";
    final Double width = 11.5;
    final Double height = 17.5;

    // When
    final Format format = new Format(id, name, description, width, height);

    // Then
    Assertions.assertThat(format.getId()).isEqualTo(id);
    Assertions.assertThat(format.getName()).isEqualTo(name);
    Assertions.assertThat(format.getDescription()).isEqualTo(description);
    Assertions.assertThat(format.getWidthCm()).isEqualTo(width);
    Assertions.assertThat(format.getHeightCm()).isEqualTo(height);
  }

  @Test
  @DisplayName("Should create format without UUID")
  void shouldCreateFormatWithoutUuid() {
    // Given
    final String name = "A4";
    final String description = "Standard paper size";
    final Double width = 21.0;
    final Double height = 29.7;

    // When
    final Format format = new Format(name, description, width, height);

    // Then
    Assertions.assertThat(format.getId()).isNotNull();
    Assertions.assertThat(format.getName()).isEqualTo(name);
    Assertions.assertThat(format.getDescription()).isEqualTo(description);
    Assertions.assertThat(format.getWidthCm()).isEqualTo(width);
    Assertions.assertThat(format.getHeightCm()).isEqualTo(height);
  }

  @Test
  @DisplayName("Should create format with null dimensions")
  void shouldCreateFormatWithNullDimensions() {
    // Given
    final String name = "Custom";
    final String description = "Custom format";

    // When
    final Format format = new Format(name, description, null, null);

    // Then
    Assertions.assertThat(format.getName()).isEqualTo(name);
    Assertions.assertThat(format.getWidthCm()).isNull();
    Assertions.assertThat(format.getHeightCm()).isNull();
  }

  @Test
  @DisplayName("Should throw exception when width is zero")
  void shouldThrowExceptionWhenWidthIsZero() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Format("Test", "Test", 0.0, 10.0))
        .isInstanceOf(IllegalArgumentException.class).hasMessage("Width must be positive");
  }

  @Test
  @DisplayName("Should throw exception when width is negative")
  void shouldThrowExceptionWhenWidthIsNegative() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Format("Test", "Test", -5.0, 10.0))
        .isInstanceOf(IllegalArgumentException.class).hasMessage("Width must be positive");
  }

  @Test
  @DisplayName("Should throw exception when height is zero")
  void shouldThrowExceptionWhenHeightIsZero() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Format("Test", "Test", 10.0, 0.0))
        .isInstanceOf(IllegalArgumentException.class).hasMessage("Height must be positive");
  }

  @Test
  @DisplayName("Should throw exception when height is negative")
  void shouldThrowExceptionWhenHeightIsNegative() {
    // When & Then
    Assertions.assertThatThrownBy(() -> new Format("Test", "Test", 10.0, -5.0))
        .isInstanceOf(IllegalArgumentException.class).hasMessage("Height must be positive");
  }

  @Test
  @DisplayName("Should update name")
  void shouldUpdateName() {
    // Given
    final Format format = new Format("Old Name", "Description", 10.0, 15.0);

    // When
    format.setName("New Name");

    // Then
    Assertions.assertThat(format.getName()).isEqualTo("New Name");
  }

  @Test
  @DisplayName("Should update description")
  void shouldUpdateDescription() {
    // Given
    final Format format = new Format("Name", "Old Description", 10.0, 15.0);

    // When
    format.setDescription("New Description");

    // Then
    Assertions.assertThat(format.getDescription()).isEqualTo("New Description");
  }

  @Test
  @DisplayName("Should update width with valid value")
  void shouldUpdateWidthWithValidValue() {
    // Given
    final Format format = new Format("Name", "Description", 10.0, 15.0);

    // When
    format.setWidthCm(20.0);

    // Then
    Assertions.assertThat(format.getWidthCm()).isEqualTo(20.0);
  }

  @Test
  @DisplayName("Should throw exception when updating width to zero")
  void shouldThrowExceptionWhenUpdatingWidthToZero() {
    // Given
    final Format format = new Format("Name", "Description", 10.0, 15.0);

    // When & Then
    Assertions.assertThatThrownBy(() -> format.setWidthCm(0.0))
        .isInstanceOf(IllegalArgumentException.class).hasMessage("Width must be positive");
  }

  @Test
  @DisplayName("Should throw exception when updating width to negative")
  void shouldThrowExceptionWhenUpdatingWidthToNegative() {
    // Given
    final Format format = new Format("Name", "Description", 10.0, 15.0);

    // When & Then
    Assertions.assertThatThrownBy(() -> format.setWidthCm(-5.0))
        .isInstanceOf(IllegalArgumentException.class).hasMessage("Width must be positive");
  }

  @Test
  @DisplayName("Should update height with valid value")
  void shouldUpdateHeightWithValidValue() {
    // Given
    final Format format = new Format("Name", "Description", 10.0, 15.0);

    // When
    format.setHeightCm(25.0);

    // Then
    Assertions.assertThat(format.getHeightCm()).isEqualTo(25.0);
  }

  @Test
  @DisplayName("Should throw exception when updating height to zero")
  void shouldThrowExceptionWhenUpdatingHeightToZero() {
    // Given
    final Format format = new Format("Name", "Description", 10.0, 15.0);

    // When & Then
    Assertions.assertThatThrownBy(() -> format.setHeightCm(0.0))
        .isInstanceOf(IllegalArgumentException.class).hasMessage("Height must be positive");
  }

  @Test
  @DisplayName("Should throw exception when updating height to negative")
  void shouldThrowExceptionWhenUpdatingHeightToNegative() {
    // Given
    final Format format = new Format("Name", "Description", 10.0, 15.0);

    // When & Then
    Assertions.assertThatThrownBy(() -> format.setHeightCm(-5.0))
        .isInstanceOf(IllegalArgumentException.class).hasMessage("Height must be positive");
  }

  @Test
  @DisplayName("Should allow updating dimensions to null")
  void shouldAllowUpdatingDimensionsToNull() {
    // Given
    final Format format = new Format("Name", "Description", 10.0, 15.0);

    // When
    format.setWidthCm(null);
    format.setHeightCm(null);

    // Then
    Assertions.assertThat(format.getWidthCm()).isNull();
    Assertions.assertThat(format.getHeightCm()).isNull();
  }

  @Test
  @DisplayName("Should be equal when ID and properties match")
  void shouldBeEqualWhenIdAndPropertiesMatch() {
    // Given
    final UUID id = UUID.randomUUID();
    final Format format1 = new Format(id, "Tankobon", "Manga", 11.5, 17.5);
    final Format format2 = new Format(id, "Tankobon", "Manga", 11.5, 17.5);

    // Then
    Assertions.assertThat(format1).isEqualTo(format2);
    Assertions.assertThat(format1.hashCode()).isEqualTo(format2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal when properties differ")
  void shouldNotBeEqualWhenPropertiesDiffer() {
    // Given
    final UUID id = UUID.randomUUID();
    final Format format1 = new Format(id, "Tankobon", "Manga", 11.5, 17.5);
    final Format format2 = new Format(id, "A4", "Paper", 21.0, 29.7);

    // Then
    Assertions.assertThat(format1).isNotEqualTo(format2);
  }

  @Test
  @DisplayName("Should not be equal to null")
  void shouldNotBeEqualToNull() {
    // Given
    final Format format = new Format("Test", "Test", 10.0, 15.0);

    // Then
    Assertions.assertThat(format).isNotEqualTo(null);
  }

  @Test
  @DisplayName("Should convert to string with name and dimensions")
  void shouldConvertToStringWithNameAndDimensions() {
    // Given
    final Format format = new Format("Tankobon", "Standard manga", 11.5, 17.5);

    // When
    final String result = format.toString();

    // Then
    Assertions.assertThat(result).isEqualTo("Tankobon (11.5x17.5 cm)");
  }
}
