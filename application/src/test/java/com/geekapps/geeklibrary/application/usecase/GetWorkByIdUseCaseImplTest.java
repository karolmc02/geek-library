package com.geekapps.geeklibrary.application.usecase;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.model.work.WorkType;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetWorkByIdUseCase Tests")
class GetWorkByIdUseCaseImplTest {

  @Mock
  private WorkRepository workRepository;

  @InjectMocks
  private GetWorkByIdUseCaseImpl getWorkByIdUseCase;

  @Test
  @DisplayName("Should return work when it exists")
  void shouldReturnWorkWhenItExists() {
    // Given
    final var workId = UUID.randomUUID();
    final var author = new Person("Eiichiro", "Oda");
    final var work =
        new Work(workId, WorkType.MANGA, "One Piece", "A pirate adventure", author, author);

    Mockito.when(this.workRepository.findById(workId)).thenReturn(work);

    // When
    final var result = this.getWorkByIdUseCase.execute(workId);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(workId);
    Assertions.assertThat(result.getTitle()).isEqualTo("One Piece");
    Assertions.assertThat(result.getType()).isEqualTo(WorkType.MANGA);

    Mockito.verify(this.workRepository).findById(workId);
  }

  @Test
  @DisplayName("Should return null when work does not exist")
  void shouldReturnNullWhenWorkDoesNotExist() {
    // Given
    final var workId = UUID.randomUUID();
    Mockito.when(this.workRepository.findById(workId)).thenReturn(null);

    // When
    final var result = this.getWorkByIdUseCase.execute(workId);

    // Then
    Assertions.assertThat(result).isNull();
    Mockito.verify(this.workRepository).findById(workId);
  }

  @Test
  @DisplayName("Should call repository with correct ID")
  void shouldCallRepositoryWithCorrectId() {
    // Given
    final var workId = UUID.randomUUID();
    Mockito.when(this.workRepository.findById(workId)).thenReturn(null);

    // When
    this.getWorkByIdUseCase.execute(workId);

    // Then
    Mockito.verify(this.workRepository).findById(workId);
  }

  @Test
  @DisplayName("Should return work with all properties")
  void shouldReturnWorkWithAllProperties() {
    // Given
    final var workId = UUID.randomUUID();
    final var author = new Person("Masashi", "Kishimoto");
    final var illustrator = new Person("Masashi", "Kishimoto");
    final var work = new Work(workId, WorkType.MANGA, "Naruto", "Ninja story", author, illustrator);

    Mockito.when(this.workRepository.findById(workId)).thenReturn(work);

    // When
    final var result = this.getWorkByIdUseCase.execute(workId);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(workId);
    Assertions.assertThat(result.getType()).isEqualTo(WorkType.MANGA);
    Assertions.assertThat(result.getTitle()).isEqualTo("Naruto");
    Assertions.assertThat(result.getDescription()).isEqualTo("Ninja story");
    Assertions.assertThat(result.getAuthor()).isEqualTo(author);
    Assertions.assertThat(result.getIllustrator()).isEqualTo(illustrator);
  }
}
