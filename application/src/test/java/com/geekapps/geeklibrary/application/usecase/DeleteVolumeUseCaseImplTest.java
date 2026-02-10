package com.geekapps.geeklibrary.application.usecase;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.geekapps.geeklibrary.application.port.in.model.volume.DeleteVolumeCommand;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteVolumeUseCase Tests")
class DeleteVolumeUseCaseImplTest {

  @Mock
  private VolumeRepository volumeRepository;

  @InjectMocks
  private DeleteVolumeUseCaseImpl deleteVolumeUseCase;

  private UUID workId;
  private UUID editionId;
  private UUID volumeId;
  private DeleteVolumeCommand command;

  @BeforeEach
  void setUp() {
    this.workId = UUID.randomUUID();
    this.editionId = UUID.randomUUID();
    this.volumeId = UUID.randomUUID();

    this.command = new DeleteVolumeCommand(this.workId, this.editionId, this.volumeId);
  }

  @Test
  @DisplayName("Should delete volume successfully")
  void shouldDeleteVolumeSuccessfully() {
    // Given
    Mockito.doNothing().when(this.volumeRepository).deleteById(this.volumeId, this.editionId);

    // When
    final var result = this.deleteVolumeUseCase.execute(this.command);

    // Then
    Assertions.assertThat(result).isNull();
    Mockito.verify(this.volumeRepository).deleteById(this.volumeId, this.editionId);
  }

  @Test
  @DisplayName("Should call repository deleteById with correct parameters")
  void shouldCallRepositoryDeleteByIdWithCorrectParameters() {
    // Given
    Mockito.doNothing().when(this.volumeRepository).deleteById(this.volumeId, this.editionId);

    // When
    this.deleteVolumeUseCase.execute(this.command);

    // Then
    Mockito.verify(this.volumeRepository).deleteById(this.volumeId, this.editionId);
    Mockito.verifyNoMoreInteractions(this.volumeRepository);
  }

  @Test
  @DisplayName("Should return null")
  void shouldReturnNull() {
    // Given
    Mockito.doNothing().when(this.volumeRepository).deleteById(this.volumeId, this.editionId);

    // When
    final var result = this.deleteVolumeUseCase.execute(this.command);

    // Then
    Assertions.assertThat(result).isNull();
  }

  @Test
  @DisplayName("Should delete volume with specific volumeId and editionId")
  void shouldDeleteVolumeWithSpecificVolumeIdAndEditionId() {
    // Given
    final var specificWorkId = UUID.fromString("11111111-1111-1111-1111-111111111111");
    final var specificVolumeId = UUID.fromString("12345678-1234-1234-1234-123456789012");
    final var specificEditionId = UUID.fromString("87654321-4321-4321-4321-210987654321");
    final var specificCommand =
        new DeleteVolumeCommand(specificWorkId, specificEditionId, specificVolumeId);

    Mockito.doNothing().when(this.volumeRepository).deleteById(specificVolumeId, specificEditionId);

    // When
    this.deleteVolumeUseCase.execute(specificCommand);

    // Then
    Mockito.verify(this.volumeRepository).deleteById(specificVolumeId, specificEditionId);
  }

  @Test
  @DisplayName("Should complete when repository completes successfully")
  void shouldCompleteWhenRepositoryCompletesSuccessfully() {
    // Given
    Mockito.doNothing().when(this.volumeRepository).deleteById(this.volumeId, this.editionId);

    // When & Then
    Assertions.assertThatCode(() -> this.deleteVolumeUseCase.execute(this.command))
        .doesNotThrowAnyException();

    Mockito.verify(this.volumeRepository).deleteById(this.volumeId, this.editionId);
  }
}
