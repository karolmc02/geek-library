package com.geekapps.geeklibrary.application.usecase;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteWorkUseCase Tests")
class DeleteWorkUseCaseImplTest {

  @Mock
  private WorkRepository workRepository;

  @InjectMocks
  private DeleteWorkUseCaseImpl deleteWorkUseCase;

  @Test
  @DisplayName("Should delete work by ID")
  void shouldDeleteWorkById() {
    // Given
    final var workId = UUID.randomUUID();

    // When
    this.deleteWorkUseCase.execute(workId);

    // Then
    Mockito.verify(this.workRepository).deleteById(workId);
  }

  @Test
  @DisplayName("Should call repository with correct ID")
  void shouldCallRepositoryWithCorrectId() {
    // Given
    final var workId = UUID.randomUUID();

    // When
    this.deleteWorkUseCase.execute(workId);

    // Then
    Mockito.verify(this.workRepository).deleteById(workId);
  }

  @Test
  @DisplayName("Should handle deletion of different work IDs")
  void shouldHandleDeletionOfDifferentWorkIds() {
    // Given
    final var workId1 = UUID.randomUUID();
    final var workId2 = UUID.randomUUID();

    // When
    this.deleteWorkUseCase.execute(workId1);
    this.deleteWorkUseCase.execute(workId2);

    // Then
    Mockito.verify(this.workRepository).deleteById(workId1);
    Mockito.verify(this.workRepository).deleteById(workId2);
  }
}
