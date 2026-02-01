package com.geekapps.geeklibrary.domain.model.common;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Person Tests")
class PersonTest {

  @Test
  @DisplayName("Should create Person with firstName and lastName")
  void shouldCreatePerson() {
    // Given
    final var firstName = "John";
    final var lastName = "Doe";

    // When
    final var person = new Person(firstName, lastName);

    // Then
    Assertions.assertThat(person).isNotNull();
    Assertions.assertThat(person.getId()).isNotNull();
    Assertions.assertThat(person.getFirstName()).isEqualTo(firstName);
    Assertions.assertThat(person.getLastName()).isEqualTo(lastName);
  }

  @Test
  @DisplayName("Should create Person with id, firstName and lastName")
  void shouldCreatePersonWithId() {
    // Given
    final var id = UUID.randomUUID();
    final var firstName = "John";
    final var lastName = "Doe";

    // When
    final var person = new Person(id, firstName, lastName);

    // Then
    Assertions.assertThat(person).isNotNull();
    Assertions.assertThat(person.getId()).isEqualTo(id);
    Assertions.assertThat(person.getFirstName()).isEqualTo(firstName);
    Assertions.assertThat(person.getLastName()).isEqualTo(lastName);
  }

  @Test
  @DisplayName("Should be equal when id is the same")
  void shouldBeEqualWhenSameId() {
    // Given
    final var id = UUID.randomUUID();
    final var person1 = new Person(id, "John", "Doe");
    final var person2 = new Person(id, "John", "Doe");

    // When & Then
    Assertions.assertThat(person1).isEqualTo(person2);
    Assertions.assertThat(person1.hashCode()).isEqualTo(person2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal when different id")
  void shouldNotBeEqualWhenDifferentId() {
    // Given
    final var person1 = new Person(UUID.randomUUID(), "John", "Doe");
    final var person2 = new Person(UUID.randomUUID(), "John", "Doe");

    // When & Then
    Assertions.assertThat(person1).isNotEqualTo(person2);
  }

  @Test
  @DisplayName("Should have proper toString")
  void shouldHaveProperToString() {
    // Given
    final var person = new Person("John", "Doe");

    // When
    final var result = person.toString();

    // Then
    Assertions.assertThat(result).contains("John", "Doe", "Person");
  }
}
