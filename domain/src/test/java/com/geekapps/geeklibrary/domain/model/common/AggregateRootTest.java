package com.geekapps.geeklibrary.domain.model.common;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AggregateRoot Tests")
class AggregateRootTest {

  // Test implementation of AggregateRoot
  private static class TestAggregateRoot extends AggregateRoot {
    public TestAggregateRoot() {
    }

    public TestAggregateRoot(final UUID id) {
      super(id);
    }
  }

  @Test
  @DisplayName("Should create AggregateRoot without ID")
  void shouldCreateAggregateRootWithoutId() {
    // When
    final var aggregate = new TestAggregateRoot();

    // Then
    Assertions.assertThat(aggregate.getId()).isNotNull();
  }

  @Test
  @DisplayName("Should create AggregateRoot with ID")
  void shouldCreateAggregateRootWithId() {
    // Given
    final var id = UUID.randomUUID();

    // When
    final var aggregate = new TestAggregateRoot(id);

    // Then
    Assertions.assertThat(aggregate.getId()).isEqualTo(id);
  }

  @Test
  @DisplayName("Should inherit DomainEntity behavior")
  void shouldInheritDomainEntityBehavior() {
    // Given
    final var id = UUID.randomUUID();
    final var aggregate1 = new TestAggregateRoot(id);
    final var aggregate2 = new TestAggregateRoot(id);

    // When & Then
    Assertions.assertThat(aggregate1).isEqualTo(aggregate2);
    Assertions.assertThat(aggregate1.hashCode()).isEqualTo(aggregate2.hashCode());
  }
}
