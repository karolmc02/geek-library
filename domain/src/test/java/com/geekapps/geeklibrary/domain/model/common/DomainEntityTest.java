package com.geekapps.geeklibrary.domain.model.common;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DomainEntity Tests")
class DomainEntityTest {

  @Test
  @DisplayName("Should generate UUID when created without ID")
  void shouldGenerateUuidWhenCreatedWithoutId() {
    // When
    final var entity = new DomainEntity();

    // Then
    Assertions.assertThat(entity.getId()).isNotNull();
  }

  @Test
  @DisplayName("Should use provided UUID when created with ID")
  void shouldUseProvidedUuidWhenCreatedWithId() {
    // Given
    final var id = UUID.randomUUID();

    // When
    final var entity = new DomainEntity(id);

    // Then
    Assertions.assertThat(entity.getId()).isEqualTo(id);
  }

  @Test
  @DisplayName("Should generate UUID when created with null ID")
  void shouldGenerateUuidWhenCreatedWithNullId() {
    // When
    final var entity = new DomainEntity(null);

    // Then
    Assertions.assertThat(entity.getId()).isNotNull();
  }

  @Test
  @DisplayName("Should allow setting ID")
  void shouldAllowSettingId() {
    // Given
    final var entity = new DomainEntity();
    final var newId = UUID.randomUUID();

    // When
    entity.setId(newId);

    // Then
    Assertions.assertThat(entity.getId()).isEqualTo(newId);
  }

  @Test
  @DisplayName("Should be equal when IDs are the same")
  void shouldBeEqualWhenIdsAreSame() {
    // Given
    final var id = UUID.randomUUID();
    final var entity1 = new DomainEntity(id);
    final var entity2 = new DomainEntity(id);

    // When & Then
    Assertions.assertThat(entity1).isEqualTo(entity2);
    Assertions.assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal when IDs are different")
  void shouldNotBeEqualWhenIdsAreDifferent() {
    // Given
    final var entity1 = new DomainEntity(UUID.randomUUID());
    final var entity2 = new DomainEntity(UUID.randomUUID());

    // When & Then
    Assertions.assertThat(entity1).isNotEqualTo(entity2);
  }

  @Test
  @DisplayName("Should not be equal to null")
  void shouldNotBeEqualToNull() {
    // Given
    final var entity = new DomainEntity();

    // When & Then
    Assertions.assertThat(entity).isNotEqualTo(null);
  }

  @Test
  @DisplayName("Should be equal to itself")
  void shouldBeEqualToItself() {
    // Given
    final var entity = new DomainEntity();

    // When & Then
    Assertions.assertThat(entity).isEqualTo(entity);
  }

  @Test
  @DisplayName("Should have proper toString")
  void shouldHaveProperToString() {
    // Given
    final var id = UUID.randomUUID();
    final var entity = new DomainEntity(id);

    // When
    final var result = entity.toString();

    // Then
    Assertions.assertThat(result).contains("DomainEntity", id.toString());
  }
}
