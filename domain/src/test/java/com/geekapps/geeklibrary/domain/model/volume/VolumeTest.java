package com.geekapps.geeklibrary.domain.model.volume;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.geekapps.geeklibrary.domain.model.common.Money;

@DisplayName("Volume Tests")
class VolumeTest {

  @Test
  @DisplayName("Should create volume with UUID")
  void shouldCreateVolumeWithUuid() {
    // Given
    final UUID id = UUID.randomUUID();
    final String title = "Volume 1";
    final Integer number = 1;
    final Money price = new Money("USD", new BigDecimal("9.99"));
    final LocalDate publicationDate = LocalDate.of(2024, 1, 15);
    final String isbn = "978-1-4215-1234-5";
    final Integer pages = 192;

    // When
    final Volume volume = new Volume(id, title, number, price, publicationDate, isbn, pages);

    // Then
    Assertions.assertThat(volume.getId()).isEqualTo(id);
    Assertions.assertThat(volume.getTitle()).isEqualTo(title);
    Assertions.assertThat(volume.getNumber()).isEqualTo(number);
    Assertions.assertThat(volume.getPrice()).isEqualTo(price);
    Assertions.assertThat(volume.getPublicationDate()).isEqualTo(publicationDate);
    Assertions.assertThat(volume.getIsbn()).isEqualTo(isbn);
    Assertions.assertThat(volume.getPages()).isEqualTo(pages);
  }

  @Test
  @DisplayName("Should create volume without UUID")
  void shouldCreateVolumeWithoutUuid() {
    // Given
    final String title = "Volume 2";
    final Integer number = 2;
    final Money price = new Money("EUR", new BigDecimal("12.50"));
    final LocalDate publicationDate = LocalDate.of(2024, 3, 20);
    final String isbn = "978-1-5678-9012-3";
    final Integer pages = 224;

    // When
    final Volume volume = new Volume(title, number, price, publicationDate, isbn, pages);

    // Then
    Assertions.assertThat(volume.getId()).isNotNull();
    Assertions.assertThat(volume.getTitle()).isEqualTo(title);
    Assertions.assertThat(volume.getNumber()).isEqualTo(number);
    Assertions.assertThat(volume.getPrice()).isEqualTo(price);
    Assertions.assertThat(volume.getPublicationDate()).isEqualTo(publicationDate);
    Assertions.assertThat(volume.getIsbn()).isEqualTo(isbn);
    Assertions.assertThat(volume.getPages()).isEqualTo(pages);
  }

  @Test
  @DisplayName("Should update title")
  void shouldUpdateTitle() {
    // Given
    final Volume volume = new Volume("Old Title", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-2345-6789-0", 200);

    // When
    volume.setTitle("New Title");

    // Then
    Assertions.assertThat(volume.getTitle()).isEqualTo("New Title");
  }

  @Test
  @DisplayName("Should update number")
  void shouldUpdateNumber() {
    // Given
    final Volume volume = new Volume("Volume", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-2345-6789-0", 200);

    // When
    volume.setNumber(5);

    // Then
    Assertions.assertThat(volume.getNumber()).isEqualTo(5);
  }

  @Test
  @DisplayName("Should update price")
  void shouldUpdatePrice() {
    // Given
    final Money oldPrice = new Money("USD", new BigDecimal("9.99"));
    final Money newPrice = new Money("USD", new BigDecimal("14.99"));
    final Volume volume =
        new Volume("Volume", 1, oldPrice, LocalDate.now(), "978-1-2345-6789-0", 200);

    // When
    volume.setPrice(newPrice);

    // Then
    Assertions.assertThat(volume.getPrice()).isEqualTo(newPrice);
  }

  @Test
  @DisplayName("Should update publication date")
  void shouldUpdatePublicationDate() {
    // Given
    final LocalDate oldDate = LocalDate.of(2024, 1, 1);
    final LocalDate newDate = LocalDate.of(2024, 12, 31);
    final Volume volume = new Volume("Volume", 1, new Money("USD", new BigDecimal("9.99")), oldDate,
        "978-1-2345-6789-0", 200);

    // When
    volume.setPublicationDate(newDate);

    // Then
    Assertions.assertThat(volume.getPublicationDate()).isEqualTo(newDate);
  }

  @Test
  @DisplayName("Should update isbn")
  void shouldUpdateIsbn() {
    // Given
    final Volume volume = new Volume("Volume", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-2345-6789-0", 200);

    // When
    volume.setIsbn("978-9-8765-4321-0");

    // Then
    Assertions.assertThat(volume.getIsbn()).isEqualTo("978-9-8765-4321-0");
  }

  @Test
  @DisplayName("Should update pages")
  void shouldUpdatePages() {
    // Given
    final Volume volume = new Volume("Volume", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-2345-6789-0", 200);

    // When
    volume.setPages(350);

    // Then
    Assertions.assertThat(volume.getPages()).isEqualTo(350);
  }

  @Test
  @DisplayName("Should be equal when ID and all properties match")
  void shouldBeEqualWhenIdAndAllPropertiesMatch() {
    // Given
    final UUID id = UUID.randomUUID();
    final String title = "Volume 1";
    final Integer number = 1;
    final Money price = new Money("JPY", new BigDecimal("1200"));
    final LocalDate publicationDate = LocalDate.of(2024, 6, 15);
    final String isbn = "978-1-1111-2222-3";
    final Integer pages = 180;

    final Volume volume1 = new Volume(id, title, number, price, publicationDate, isbn, pages);
    final Volume volume2 = new Volume(id, title, number, price, publicationDate, isbn, pages);

    // Then
    Assertions.assertThat(volume1).isEqualTo(volume2);
    Assertions.assertThat(volume1.hashCode()).isEqualTo(volume2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal when title differs")
  void shouldNotBeEqualWhenTitleDiffers() {
    // Given
    final UUID id = UUID.randomUUID();
    final Money price = new Money("USD", new BigDecimal("9.99"));
    final LocalDate date = LocalDate.now();

    final Volume volume1 = new Volume(id, "Volume 1", 1, price, date, "978-1-1111-2222-3", 200);
    final Volume volume2 = new Volume(id, "Volume 2", 1, price, date, "978-1-1111-2222-3", 200);

    // Then
    Assertions.assertThat(volume1).isNotEqualTo(volume2);
  }

  @Test
  @DisplayName("Should not be equal when number differs")
  void shouldNotBeEqualWhenNumberDiffers() {
    // Given
    final UUID id = UUID.randomUUID();
    final Money price = new Money("USD", new BigDecimal("9.99"));
    final LocalDate date = LocalDate.now();

    final Volume volume1 = new Volume(id, "Volume", 1, price, date, "978-1-1111-2222-3", 200);
    final Volume volume2 = new Volume(id, "Volume", 2, price, date, "978-1-1111-2222-3", 200);

    // Then
    Assertions.assertThat(volume1).isNotEqualTo(volume2);
  }

  @Test
  @DisplayName("Should not be equal when price differs")
  void shouldNotBeEqualWhenPriceDiffers() {
    // Given
    final UUID id = UUID.randomUUID();
    final Money price1 = new Money("USD", new BigDecimal("9.99"));
    final Money price2 = new Money("USD", new BigDecimal("14.99"));
    final LocalDate date = LocalDate.now();

    final Volume volume1 = new Volume(id, "Volume", 1, price1, date, "978-1-1111-2222-3", 200);
    final Volume volume2 = new Volume(id, "Volume", 1, price2, date, "978-1-1111-2222-3", 200);

    // Then
    Assertions.assertThat(volume1).isNotEqualTo(volume2);
  }

  @Test
  @DisplayName("Should not be equal when publication date differs")
  void shouldNotBeEqualWhenPublicationDateDiffers() {
    // Given
    final UUID id = UUID.randomUUID();
    final Money price = new Money("USD", new BigDecimal("9.99"));
    final LocalDate date1 = LocalDate.of(2024, 1, 1);
    final LocalDate date2 = LocalDate.of(2024, 12, 31);

    final Volume volume1 = new Volume(id, "Volume", 1, price, date1, "978-1-1111-2222-3", 200);
    final Volume volume2 = new Volume(id, "Volume", 1, price, date2, "978-1-1111-2222-3", 200);

    // Then
    Assertions.assertThat(volume1).isNotEqualTo(volume2);
  }

  @Test
  @DisplayName("Should not be equal when isbn differs")
  void shouldNotBeEqualWhenIsbnDiffers() {
    // Given
    final UUID id = UUID.randomUUID();
    final Money price = new Money("USD", new BigDecimal("9.99"));
    final LocalDate date = LocalDate.now();

    final Volume volume1 = new Volume(id, "Volume", 1, price, date, "978-1-1111-2222-3", 200);
    final Volume volume2 = new Volume(id, "Volume", 1, price, date, "978-9-9999-8888-7", 200);

    // Then
    Assertions.assertThat(volume1).isNotEqualTo(volume2);
  }

  @Test
  @DisplayName("Should not be equal when pages differ")
  void shouldNotBeEqualWhenPagesDiffer() {
    // Given
    final UUID id = UUID.randomUUID();
    final Money price = new Money("USD", new BigDecimal("9.99"));
    final LocalDate date = LocalDate.now();

    final Volume volume1 = new Volume(id, "Volume", 1, price, date, "978-1-1111-2222-3", 200);
    final Volume volume2 = new Volume(id, "Volume", 1, price, date, "978-1-1111-2222-3", 350);

    // Then
    Assertions.assertThat(volume1).isNotEqualTo(volume2);
  }

  @Test
  @DisplayName("Should not be equal to null")
  void shouldNotBeEqualToNull() {
    // Given
    final Volume volume = new Volume("Volume", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-2345-6789-0", 200);

    // Then
    Assertions.assertThat(volume).isNotEqualTo(null);
  }

  @Test
  @DisplayName("Should be equal to itself")
  void shouldBeEqualToItself() {
    // Given
    final Volume volume = new Volume("Volume", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-2345-6789-0", 200);

    // Then
    Assertions.assertThat(volume).isEqualTo(volume);
  }

  @Test
  @DisplayName("Should not be equal to different class")
  void shouldNotBeEqualToDifferentClass() {
    // Given
    final Volume volume = new Volume("Volume", 1, new Money("USD", new BigDecimal("9.99")),
        LocalDate.now(), "978-1-2345-6789-0", 200);
    final String notAVolume = "Not a Volume";

    // Then
    Assertions.assertThat(volume).isNotEqualTo(notAVolume);
  }

  @Test
  @DisplayName("Should generate same hashCode for equal objects")
  void shouldGenerateSameHashCodeForEqualObjects() {
    // Given
    final UUID id = UUID.randomUUID();
    final Money price = new Money("GBP", new BigDecimal("15.99"));
    final LocalDate date = LocalDate.of(2024, 5, 10);

    final Volume volume1 = new Volume(id, "Volume", 3, price, date, "978-1-5555-6666-7", 250);
    final Volume volume2 = new Volume(id, "Volume", 3, price, date, "978-1-5555-6666-7", 250);

    // Then
    Assertions.assertThat(volume1.hashCode()).isEqualTo(volume2.hashCode());
  }

  @Test
  @DisplayName("Should include all properties in toString")
  void shouldIncludeAllPropertiesToString() {
    // Given
    final UUID id = UUID.randomUUID();
    final Money price = new Money("CAD", new BigDecimal("19.99"));
    final LocalDate date = LocalDate.of(2024, 7, 4);
    final Volume volume = new Volume(id, "Volume Title", 7, price, date, "978-1-7777-8888-9", 300);

    // When
    final String result = volume.toString();

    // Then
    Assertions.assertThat(result).contains(id.toString());
    Assertions.assertThat(result).contains("Volume Title");
    Assertions.assertThat(result).contains("7");
    Assertions.assertThat(result).contains("19.99");
    Assertions.assertThat(result).contains("CAD");
    Assertions.assertThat(result).contains("2024-07-04");
    Assertions.assertThat(result).contains("978-1-7777-8888-9");
    Assertions.assertThat(result).contains("300");
  }
}
