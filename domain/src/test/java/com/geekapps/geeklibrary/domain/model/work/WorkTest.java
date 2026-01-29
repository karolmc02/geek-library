package com.geekapps.geeklibrary.domain.model.work;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.geekapps.geeklibrary.domain.model.common.Person;

@DisplayName("Work Tests")
class WorkTest {

  @Test
  @DisplayName("Should create Work without ID")
  void shouldCreateWorkWithoutId() {
    // Given
    final var type = WorkType.MANGA;
    final var title = "One Piece";
    final var description = "A pirate adventure";
    final var author = new Person("Eiichiro", "Oda");
    final var illustrator = new Person("Eiichiro", "Oda");

    // When
    final var work = new Work(type, title, description, author, illustrator);

    // Then
    Assertions.assertThat(work.getId()).isNotNull();
    Assertions.assertThat(work.getType()).isEqualTo(type);
    Assertions.assertThat(work.getTitle()).isEqualTo(title);
    Assertions.assertThat(work.getDescription()).isEqualTo(description);
    Assertions.assertThat(work.getAuthor()).isEqualTo(author);
    Assertions.assertThat(work.getIllustrator()).isEqualTo(illustrator);
  }

  @Test
  @DisplayName("Should create Work with ID")
  void shouldCreateWorkWithId() {
    // Given
    final var id = UUID.randomUUID();
    final var type = WorkType.LIGHT_NOVEL;
    final var title = "Sword Art Online";
    final var description = "A VR adventure";
    final var author = new Person("Reki", "Kawahara");
    final var illustrator = new Person("abec", "");

    // When
    final var work = new Work(id, type, title, description, author, illustrator);

    // Then
    Assertions.assertThat(work.getId()).isEqualTo(id);
    Assertions.assertThat(work.getType()).isEqualTo(type);
    Assertions.assertThat(work.getTitle()).isEqualTo(title);
    Assertions.assertThat(work.getDescription()).isEqualTo(description);
    Assertions.assertThat(work.getAuthor()).isEqualTo(author);
    Assertions.assertThat(work.getIllustrator()).isEqualTo(illustrator);
  }

  @Test
  @DisplayName("Should allow updating Work properties")
  void shouldAllowUpdatingWorkProperties() {
    // Given
    final var work = new Work(WorkType.MANGA, "Title", "Desc", new Person("John", "Doe"),
        new Person("Jane", "Smith"));
    final var newType = WorkType.ARTBOOK;
    final var newTitle = "New Title";
    final var newDescription = "New Description";
    final var newAuthor = new Person("Alice", "Wonder");
    final var newIllustrator = new Person("Bob", "Builder");

    // When
    work.setType(newType);
    work.setTitle(newTitle);
    work.setDescription(newDescription);
    work.setAuthor(newAuthor);
    work.setIllustrator(newIllustrator);

    // Then
    Assertions.assertThat(work.getType()).isEqualTo(newType);
    Assertions.assertThat(work.getTitle()).isEqualTo(newTitle);
    Assertions.assertThat(work.getDescription()).isEqualTo(newDescription);
    Assertions.assertThat(work.getAuthor()).isEqualTo(newAuthor);
    Assertions.assertThat(work.getIllustrator()).isEqualTo(newIllustrator);
  }

  @Test
  @DisplayName("Should be equal when all properties are the same")
  void shouldBeEqualWhenAllPropertiesAreSame() {
    // Given
    final var id = UUID.randomUUID();
    final var type = WorkType.MANGA;
    final var title = "Naruto";
    final var description = "Ninja story";
    final var author = new Person("Masashi", "Kishimoto");
    final var illustrator = new Person("Masashi", "Kishimoto");

    final var work1 = new Work(id, type, title, description, author, illustrator);
    final var work2 = new Work(id, type, title, description, author, illustrator);

    // When & Then
    Assertions.assertThat(work1).isEqualTo(work2);
    Assertions.assertThat(work1.hashCode()).isEqualTo(work2.hashCode());
  }

  @Test
  @DisplayName("Should not be equal when IDs are different")
  void shouldNotBeEqualWhenIdsDifferent() {
    // Given
    final var type = WorkType.MANGA;
    final var title = "Naruto";
    final var description = "Ninja story";
    final var author = new Person("Masashi", "Kishimoto");
    final var illustrator = new Person("Masashi", "Kishimoto");

    final var work1 = new Work(UUID.randomUUID(), type, title, description, author, illustrator);
    final var work2 = new Work(UUID.randomUUID(), type, title, description, author, illustrator);

    // When & Then
    Assertions.assertThat(work1).isNotEqualTo(work2);
  }

  @Test
  @DisplayName("Should not be equal when types are different")
  void shouldNotBeEqualWhenTypesDifferent() {
    // Given
    final var id = UUID.randomUUID();
    final var title = "Title";
    final var description = "Desc";
    final var author = new Person("John", "Doe");
    final var illustrator = new Person("Jane", "Smith");

    final var work1 = new Work(id, WorkType.MANGA, title, description, author, illustrator);
    final var work2 = new Work(id, WorkType.LIGHT_NOVEL, title, description, author, illustrator);

    // When & Then
    Assertions.assertThat(work1).isNotEqualTo(work2);
  }

  @Test
  @DisplayName("Should not be equal when titles are different")
  void shouldNotBeEqualWhenTitlesDifferent() {
    // Given
    final var id = UUID.randomUUID();
    final var type = WorkType.MANGA;
    final var description = "Desc";
    final var author = new Person("John", "Doe");
    final var illustrator = new Person("Jane", "Smith");

    final var work1 = new Work(id, type, "Title 1", description, author, illustrator);
    final var work2 = new Work(id, type, "Title 2", description, author, illustrator);

    // When & Then
    Assertions.assertThat(work1).isNotEqualTo(work2);
  }

  @Test
  @DisplayName("Should handle null author and illustrator")
  void shouldHandleNullAuthorAndIllustrator() {
    // Given
    final var type = WorkType.MANGA;
    final var title = "Title";
    final var description = "Description";

    // When
    final var work = new Work(type, title, description, null, null);

    // Then
    Assertions.assertThat(work.getAuthor()).isNull();
    Assertions.assertThat(work.getIllustrator()).isNull();
  }

  @Test
  @DisplayName("Should have proper toString")
  void shouldHaveProperToString() {
    // Given
    final var work = new Work(WorkType.MANGA, "Naruto", "Ninja story",
        new Person("Masashi", "Kishimoto"), new Person("Masashi", "Kishimoto"));

    // When
    final var result = work.toString();

    // Then
    Assertions.assertThat(result).contains("Work", "MANGA", "Naruto", "Ninja story");
  }

  @Test
  @DisplayName("Should not be equal to null")
  void shouldNotBeEqualToNull() {
    // Given
    final var work = new Work(WorkType.MANGA, "Title", "Desc", new Person("John", "Doe"),
        new Person("Jane", "Smith"));

    // When & Then
    Assertions.assertThat(work).isNotEqualTo(null);
  }

  @Test
  @DisplayName("Should be equal to itself")
  void shouldBeEqualToItself() {
    // Given
    final var work = new Work(WorkType.MANGA, "Title", "Desc", new Person("John", "Doe"),
        new Person("Jane", "Smith"));

    // When & Then
    Assertions.assertThat(work).isEqualTo(work);
  }
}
