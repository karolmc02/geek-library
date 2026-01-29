package com.geekapps.geeklibrary.domain.model.common;

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
    Assertions.assertThat(person.firstName()).isEqualTo(firstName);
    Assertions.assertThat(person.lastName()).isEqualTo(lastName);
  }

  @Test
  @DisplayName("Should be equal when firstName and lastName are the same")
  void shouldBeEqualWhenSame() {
    // Given
    final var person1 = new Person("John", "Doe");
    final var person2 = new Person("John", "Doe");

    // When & Then
    Assertions.assertThat(person1).isEqualTo(person2);
    Assertions.assertThat(person1.hashCode()).isEqualTo(person2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal when different")
  void shouldNotBeEqualWhenDifferent() {
    // Given
    final var person1 = new Person("John", "Doe");
    final var person2 = new Person("Jane", "Smith");

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
    Assertions.assertThat(result).contains("John", "Doe");
  }
}
