package com.geekapps.geeklibrary.domain.model.edition;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ColorMode Tests")
class ColorModeTest {

  @Test
  @DisplayName("Should have three color modes")
  void shouldHaveThreeColorModes() {
    // When
    final ColorMode[] colorModes = ColorMode.values();

    // Then
    Assertions.assertThat(colorModes).hasSize(3);
    Assertions.assertThat(colorModes).containsExactlyInAnyOrder(ColorMode.BLACK_AND_WHITE,
        ColorMode.FULL_COLOR, ColorMode.PARTIAL_COLOR);
  }

  @Test
  @DisplayName("Should get color mode by name")
  void shouldGetColorModeByName() {
    // When
    final ColorMode blackAndWhite = ColorMode.valueOf("BLACK_AND_WHITE");
    final ColorMode fullColor = ColorMode.valueOf("FULL_COLOR");
    final ColorMode partialColor = ColorMode.valueOf("PARTIAL_COLOR");

    // Then
    Assertions.assertThat(blackAndWhite).isEqualTo(ColorMode.BLACK_AND_WHITE);
    Assertions.assertThat(fullColor).isEqualTo(ColorMode.FULL_COLOR);
    Assertions.assertThat(partialColor).isEqualTo(ColorMode.PARTIAL_COLOR);
  }

  @Test
  @DisplayName("Should have consistent enum ordering")
  void shouldHaveConsistentEnumOrdering() {
    // Given
    final ColorMode[] colorModes = ColorMode.values();

    // Then
    Assertions.assertThat(colorModes[0]).isEqualTo(ColorMode.BLACK_AND_WHITE);
    Assertions.assertThat(colorModes[1]).isEqualTo(ColorMode.FULL_COLOR);
    Assertions.assertThat(colorModes[2]).isEqualTo(ColorMode.PARTIAL_COLOR);
  }
}
