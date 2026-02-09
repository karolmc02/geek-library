package com.geekapps.geeklibrary.application.usecase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.geekapps.geeklibrary.application.port.in.model.QueryWorksCommand;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.model.work.WorkType;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("QueryWorksUseCase Tests")
class QueryWorksUseCaseImplTest {

  @Mock
  private WorkRepository workRepository;

  @InjectMocks
  private QueryWorksUseCaseImpl queryWorksUseCase;

  @Test
  @DisplayName("Should query works by title")
  void shouldQueryWorksByTitle() {
    // Given
    final var title = "One Piece";
    final var command = new QueryWorksCommand(title, null);

    final var work = new Work(UUID.randomUUID(), WorkType.MANGA, "One Piece", "A pirate adventure",
        new Person("Eiichiro", "Oda"), new Person("Eiichiro", "Oda"));

    Mockito.when(this.workRepository.query(title, null)).thenReturn(List.of(work));

    // When
    final var result = this.queryWorksUseCase.execute(command);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).hasSize(1);
    Assertions.assertThat(result.get(0).getTitle()).isEqualTo("One Piece");

    Mockito.verify(this.workRepository).query(title, null);
  }

  @Test
  @DisplayName("Should query works by author")
  void shouldQueryWorksByAuthor() {
    // Given
    final var author = "Oda";
    final var command = new QueryWorksCommand(null, author);

    final var work = new Work(UUID.randomUUID(), WorkType.MANGA, "One Piece", "A pirate adventure",
        new Person("Eiichiro", "Oda"), new Person("Eiichiro", "Oda"));

    Mockito.when(this.workRepository.query(null, author)).thenReturn(List.of(work));

    // When
    final var result = this.queryWorksUseCase.execute(command);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).hasSize(1);
    Assertions.assertThat(result.get(0).getAuthor().getLastName()).contains("Oda");

    Mockito.verify(this.workRepository).query(null, author);
  }

  @Test
  @DisplayName("Should query works by title and author")
  void shouldQueryWorksByTitleAndAuthor() {
    // Given
    final var title = "Naruto";
    final var author = "Kishimoto";
    final var command = new QueryWorksCommand(title, author);

    final var work = new Work(UUID.randomUUID(), WorkType.MANGA, "Naruto", "Ninja story",
        new Person("Masashi", "Kishimoto"), new Person("Masashi", "Kishimoto"));

    Mockito.when(this.workRepository.query(title, author)).thenReturn(List.of(work));

    // When
    final var result = this.queryWorksUseCase.execute(command);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).hasSize(1);
    Assertions.assertThat(result.get(0).getTitle()).isEqualTo("Naruto");
    Assertions.assertThat(result.get(0).getAuthor().getLastName()).contains("Kishimoto");

    Mockito.verify(this.workRepository).query(title, author);
  }

  @Test
  @DisplayName("Should query works without filters")
  void shouldQueryWorksWithoutFilters() {
    // Given
    final var command = new QueryWorksCommand(null, null);

    final var work1 = new Work(UUID.randomUUID(), WorkType.MANGA, "One Piece", "Desc",
        new Person("Eiichiro", "Oda"), new Person("Eiichiro", "Oda"));
    final var work2 = new Work(UUID.randomUUID(), WorkType.MANGA, "Naruto", "Desc",
        new Person("Masashi", "Kishimoto"), new Person("Masashi", "Kishimoto"));

    Mockito.when(this.workRepository.query(null, null)).thenReturn(Arrays.asList(work1, work2));

    // When
    final var result = this.queryWorksUseCase.execute(command);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).hasSize(2);

    Mockito.verify(this.workRepository).query(null, null);
  }

  @Test
  @DisplayName("Should return empty list when no works found")
  void shouldReturnEmptyListWhenNoWorksFound() {
    // Given
    final var command = new QueryWorksCommand("NonExistent", "Unknown");
    Mockito.when(this.workRepository.query("NonExistent", "Unknown"))
        .thenReturn(Collections.emptyList());

    // When
    final var result = this.queryWorksUseCase.execute(command);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isEmpty();

    Mockito.verify(this.workRepository).query("NonExistent", "Unknown");
  }

  @Test
  @DisplayName("Should return multiple works")
  void shouldReturnMultipleWorks() {
    // Given
    final var command = new QueryWorksCommand("Manga", null);

    final var work1 = new Work(UUID.randomUUID(), WorkType.MANGA, "One Piece", "Desc",
        new Person("Eiichiro", "Oda"), new Person("Eiichiro", "Oda"));
    final var work2 = new Work(UUID.randomUUID(), WorkType.MANGA, "Naruto", "Desc",
        new Person("Masashi", "Kishimoto"), new Person("Masashi", "Kishimoto"));
    final var work3 = new Work(UUID.randomUUID(), WorkType.MANGA, "Bleach", "Desc",
        new Person("Tite", "Kubo"), new Person("Tite", "Kubo"));

    Mockito.when(this.workRepository.query("Manga", null))
        .thenReturn(Arrays.asList(work1, work2, work3));

    // When
    final var result = this.queryWorksUseCase.execute(command);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).hasSize(3);
    Assertions.assertThat(result).containsExactly(work1, work2, work3);

    Mockito.verify(this.workRepository).query("Manga", null);
  }
}
