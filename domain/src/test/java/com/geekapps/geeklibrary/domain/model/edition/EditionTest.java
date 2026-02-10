package com.geekapps.geeklibrary.domain.model.edition;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.geekapps.geeklibrary.domain.model.common.Money;
import com.geekapps.geeklibrary.domain.model.volume.Volume;

@DisplayName("Edition Tests")
class EditionTest {

  @Test
  @DisplayName("Should create edition with UUID")
  void shouldCreateEditionWithUuid() {
    // Given
    final UUID id = UUID.randomUUID();
    final String publisher = "Editorial Ivrea";
    final Language language = new Language("es");
    final Country country = new Country("ES");
    final Boolean isOriginal = false;
    final Format format = new Format("Tankobon", "Standard", 11.5, 17.5);
    final ColorMode colorMode = ColorMode.FULL_COLOR;

    // When
    final Edition edition =
        new Edition(id, publisher, language, country, isOriginal, format, colorMode);

    // Then
    Assertions.assertThat(edition.getId()).isEqualTo(id);
    Assertions.assertThat(edition.getPublisher()).isEqualTo(publisher);
    Assertions.assertThat(edition.getLanguage()).isEqualTo(language);
    Assertions.assertThat(edition.getCountry()).isEqualTo(country);
    Assertions.assertThat(edition.getIsOriginal()).isEqualTo(isOriginal);
    Assertions.assertThat(edition.getFormat()).isEqualTo(format);
    Assertions.assertThat(edition.getColorMode()).isEqualTo(colorMode);
  }

  @Test
  @DisplayName("Should create edition without UUID")
  void shouldCreateEditionWithoutUuid() {
    // Given
    final String publisher = "Shueisha";
    final Language language = new Language("ja");
    final Country country = new Country("JP");
    final Boolean isOriginal = true;
    final Format format = new Format("Tankobon", "Standard", 11.5, 17.5);
    final ColorMode colorMode = ColorMode.BLACK_AND_WHITE;

    // When
    final Edition edition =
        new Edition(publisher, language, country, isOriginal, format, colorMode);

    // Then
    Assertions.assertThat(edition.getId()).isNotNull();
    Assertions.assertThat(edition.getPublisher()).isEqualTo(publisher);
    Assertions.assertThat(edition.getLanguage()).isEqualTo(language);
    Assertions.assertThat(edition.getCountry()).isEqualTo(country);
    Assertions.assertThat(edition.getIsOriginal()).isTrue();
    Assertions.assertThat(edition.getFormat()).isEqualTo(format);
    Assertions.assertThat(edition.getColorMode()).isEqualTo(colorMode);
  }

  @Test
  @DisplayName("Should create edition with null optional properties")
  void shouldCreateEditionWithNullOptionalProperties() {
    // When
    final Edition edition = new Edition(null, null, null, null, null, null);

    // Then
    Assertions.assertThat(edition.getId()).isNotNull();
    Assertions.assertThat(edition.getPublisher()).isNull();
    Assertions.assertThat(edition.getLanguage()).isNull();
    Assertions.assertThat(edition.getCountry()).isNull();
    Assertions.assertThat(edition.getIsOriginal()).isNull();
    Assertions.assertThat(edition.getFormat()).isNull();
    Assertions.assertThat(edition.getColorMode()).isNull();
  }

  @Test
  @DisplayName("Should update publisher")
  void shouldUpdatePublisher() {
    // Given
    final Edition edition = new Edition("Old Publisher", null, null, null, null, null);

    // When
    edition.setPublisher("New Publisher");

    // Then
    Assertions.assertThat(edition.getPublisher()).isEqualTo("New Publisher");
  }

  @Test
  @DisplayName("Should update language")
  void shouldUpdateLanguage() {
    // Given
    final Language oldLanguage = new Language("en");
    final Language newLanguage = new Language("fr");
    final Edition edition = new Edition("Publisher", oldLanguage, null, null, null, null);

    // When
    edition.setLanguage(newLanguage);

    // Then
    Assertions.assertThat(edition.getLanguage()).isEqualTo(newLanguage);
  }

  @Test
  @DisplayName("Should update country")
  void shouldUpdateCountry() {
    // Given
    final Country oldCountry = new Country("US");
    final Country newCountry = new Country("CA");
    final Edition edition = new Edition("Publisher", null, oldCountry, null, null, null);

    // When
    edition.setCountry(newCountry);

    // Then
    Assertions.assertThat(edition.getCountry()).isEqualTo(newCountry);
  }

  @Test
  @DisplayName("Should update isOriginal flag")
  void shouldUpdateIsOriginalFlag() {
    // Given
    final Edition edition = new Edition("Publisher", null, null, false, null, null);

    // When
    edition.setIsOriginal(true);

    // Then
    Assertions.assertThat(edition.getIsOriginal()).isTrue();
  }

  @Test
  @DisplayName("Should update format")
  void shouldUpdateFormat() {
    // Given
    final Format oldFormat = new Format("Tankobon", "Standard", 11.5, 17.5);
    final Format newFormat = new Format("A4", "Large", 21.0, 29.7);
    final Edition edition = new Edition("Publisher", null, null, null, oldFormat, null);

    // When
    edition.setFormat(newFormat);

    // Then
    Assertions.assertThat(edition.getFormat()).isEqualTo(newFormat);
  }

  @Test
  @DisplayName("Should update color mode")
  void shouldUpdateColorMode() {
    // Given
    final Edition edition =
        new Edition("Publisher", null, null, null, null, ColorMode.BLACK_AND_WHITE);

    // When
    edition.setColorMode(ColorMode.FULL_COLOR);

    // Then
    Assertions.assertThat(edition.getColorMode()).isEqualTo(ColorMode.FULL_COLOR);
  }

  @Test
  @DisplayName("Should be equal when ID and all properties match")
  void shouldBeEqualWhenIdAndAllPropertiesMatch() {
    // Given
    final UUID id = UUID.randomUUID();
    final Language language = new Language("en");
    final Country country = new Country("US");
    final Format format = new Format("Standard", "Standard format", 15.0, 20.0);

    final Edition edition1 =
        new Edition(id, "Publisher", language, country, true, format, ColorMode.FULL_COLOR);
    final Edition edition2 =
        new Edition(id, "Publisher", language, country, true, format, ColorMode.FULL_COLOR);

    // Then
    Assertions.assertThat(edition1).isEqualTo(edition2);
    Assertions.assertThat(edition1.hashCode()).isEqualTo(edition2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal when properties differ")
  void shouldNotBeEqualWhenPropertiesDiffer() {
    // Given
    final UUID id = UUID.randomUUID();
    final Language language = new Language("en");
    final Country country = new Country("US");
    final Format format = new Format("Standard", "Standard format", 15.0, 20.0);

    final Edition edition1 =
        new Edition(id, "Publisher A", language, country, true, format, ColorMode.FULL_COLOR);
    final Edition edition2 =
        new Edition(id, "Publisher B", language, country, true, format, ColorMode.FULL_COLOR);

    // Then
    Assertions.assertThat(edition1).isNotEqualTo(edition2);
  }

  @Test
  @DisplayName("Should not be equal when color modes differ")
  void shouldNotBeEqualWhenColorModesDiffer() {
    // Given
    final UUID id = UUID.randomUUID();
    final Edition edition1 =
        new Edition(id, "Publisher", null, null, null, null, ColorMode.FULL_COLOR);
    final Edition edition2 =
        new Edition(id, "Publisher", null, null, null, null, ColorMode.BLACK_AND_WHITE);

    // Then
    Assertions.assertThat(edition1).isNotEqualTo(edition2);
  }

  @Test
  @DisplayName("Should not be equal to null")
  void shouldNotBeEqualToNull() {
    // Given
    final Edition edition = new Edition("Publisher", null, null, null, null, null);

    // Then
    Assertions.assertThat(edition).isNotEqualTo(null);
  }

  @Test
  @DisplayName("Should convert to string with all properties")
  void shouldConvertToStringWithAllProperties() {
    // Given
    final Language language = new Language("es");
    final Country country = new Country("ES");
    final Format format = new Format("Tankobon", "Standard", 11.5, 17.5);
    final Edition edition =
        new Edition("Editorial Ivrea", language, country, false, format, ColorMode.FULL_COLOR);

    // When
    final String result = edition.toString();

    // Then
    Assertions.assertThat(result).contains("Editorial Ivrea");
    Assertions.assertThat(result).contains("es");
    Assertions.assertThat(result).contains("ES");
    Assertions.assertThat(result).contains("false");
    Assertions.assertThat(result).contains("Tankobon");
  }

  @Test
  @DisplayName("Should handle equals with same object")
  void shouldHandleEqualsWithSameObject() {
    // Given
    final Edition edition = new Edition("Publisher", null, null, null, null, null);

    // Then
    Assertions.assertThat(edition).isEqualTo(edition);
  }

  @Test
  @DisplayName("Should handle equals with different class")
  void shouldHandleEqualsWithDifferentClass() {
    // Given
    final Edition edition = new Edition("Publisher", null, null, null, null, null);
    final String notAnEdition = "Not an Edition";

    // Then
    Assertions.assertThat(edition).isNotEqualTo(notAnEdition);
  }

  @Test
  @DisplayName("Should initialize volumes list as empty")
  void shouldInitializeVolumesListAsEmpty() {
    // Given & When
    final Edition edition = new Edition("Publisher", null, null, null, null, null);

    // Then
    Assertions.assertThat(edition.getVolumes()).isNotNull();
    Assertions.assertThat(edition.getVolumes()).isEmpty();
  }

  @Test
  @DisplayName("Should return immutable copy of volumes")
  void shouldReturnImmutableCopyOfVolumes() {
    // Given
    final Edition edition = new Edition("Publisher", null, null, null, null, null);
    final Volume volume = new Volume("Volume 1", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-2345-6789-0", 200);
    edition.addVolume(volume);

    // When
    final List<Volume> volumes = edition.getVolumes();

    // Then
    Assertions
        .assertThatThrownBy(() -> volumes.add(new Volume("Volume 2", 2,
            new Money("USD", new BigDecimal("9.99")), LocalDate.now(), "978-1-1111-2222-3", 180)))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  @DisplayName("Should add volume successfully")
  void shouldAddVolumeSuccessfully() {
    // Given
    final Edition edition = new Edition("Publisher", null, null, null, null, null);
    final Volume volume = new Volume("Volume 1", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.of(2024, 1, 15), "978-1-2345-6789-0", 200);

    // When
    edition.addVolume(volume);

    // Then
    Assertions.assertThat(edition.getVolumes()).hasSize(1);
    Assertions.assertThat(edition.getVolumes()).contains(volume);
  }

  @Test
  @DisplayName("Should add multiple volumes")
  void shouldAddMultipleVolumes() {
    // Given
    final Edition edition = new Edition("Publisher", null, null, null, null, null);
    final Volume volume1 = new Volume("Volume 1", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.of(2024, 1, 15), "978-1-2345-6789-0", 200);
    final Volume volume2 = new Volume("Volume 2", 2, new Money("USD", new BigDecimal("9.99")),
        LocalDate.of(2024, 3, 20), "978-1-1111-2222-3", 180);
    final Volume volume3 = new Volume("Volume 3", 3, new Money("USD", new BigDecimal("9.99")),
        LocalDate.of(2024, 5, 10), "978-1-9999-8888-7", 220);

    // When
    edition.addVolume(volume1);
    edition.addVolume(volume2);
    edition.addVolume(volume3);

    // Then
    Assertions.assertThat(edition.getVolumes()).hasSize(3);
    Assertions.assertThat(edition.getVolumes()).containsExactly(volume1, volume2, volume3);
  }

  @Test
  @DisplayName("Should not add null volume")
  void shouldNotAddNullVolume() {
    // Given
    final Edition edition = new Edition("Publisher", null, null, null, null, null);

    // When
    edition.addVolume(null);

    // Then
    Assertions.assertThat(edition.getVolumes()).isEmpty();
  }

  @Test
  @DisplayName("Should remove volume successfully")
  void shouldRemoveVolumeSuccessfully() {
    // Given
    final Edition edition = new Edition("Publisher", null, null, null, null, null);
    final Volume volume1 = new Volume("Volume 1", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-2345-6789-0", 200);
    final Volume volume2 = new Volume("Volume 2", 2, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-1111-2222-3", 180);
    edition.addVolume(volume1);
    edition.addVolume(volume2);

    // When
    edition.removeVolume(volume1);

    // Then
    Assertions.assertThat(edition.getVolumes()).hasSize(1);
    Assertions.assertThat(edition.getVolumes()).contains(volume2);
    Assertions.assertThat(edition.getVolumes()).doesNotContain(volume1);
  }

  @Test
  @DisplayName("Should not fail when removing null volume")
  void shouldNotFailWhenRemovingNullVolume() {
    // Given
    final Edition edition = new Edition("Publisher", null, null, null, null, null);
    final Volume volume = new Volume("Volume 1", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-2345-6789-0", 200);
    edition.addVolume(volume);

    // When & Then
    Assertions.assertThatCode(() -> edition.removeVolume(null)).doesNotThrowAnyException();
    Assertions.assertThat(edition.getVolumes()).hasSize(1);
  }

  @Test
  @DisplayName("Should not fail when removing non-existent volume")
  void shouldNotFailWhenRemovingNonExistentVolume() {
    // Given
    final Edition edition = new Edition("Publisher", null, null, null, null, null);
    final Volume volume1 = new Volume("Volume 1", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-2345-6789-0", 200);
    final Volume volume2 = new Volume("Volume 2", 2, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-1111-2222-3", 180);
    edition.addVolume(volume1);

    // When & Then
    Assertions.assertThatCode(() -> edition.removeVolume(volume2)).doesNotThrowAnyException();
    Assertions.assertThat(edition.getVolumes()).hasSize(1);
    Assertions.assertThat(edition.getVolumes()).contains(volume1);
  }
}
