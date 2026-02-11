package com.geekapps.geeklibrary.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.model.common.Money;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;

@DisplayName("VolumeValidator Tests")
@ExtendWith(MockitoExtension.class)
class VolumeValidatorTest {

  @Mock
  private VolumeRepository volumeRepository;

  private VolumeValidatorService volumeValidator;

  @BeforeEach
  void setUp() {
    this.volumeValidator = new VolumeValidatorService(this.volumeRepository);
  }

  @Test
  @DisplayName("Should validate successfully when volume exists")
  void shouldValidateSuccessfullyWhenVolumeExists() {
    // Given
    final UUID volumeId = UUID.randomUUID();
    final UUID editionId = UUID.randomUUID();
    final Volume volume = new Volume(volumeId, "Volume 1", 1,
        new Money("USD", new BigDecimal("9.99")), LocalDate.now(), "978-1-2345-6789-0", 200);

    Mockito.when(this.volumeRepository.findById(volumeId, editionId)).thenReturn(volume);

    // When & Then
    Assertions.assertThatCode(() -> this.volumeValidator.validateVolumeExists(volumeId, editionId))
        .doesNotThrowAnyException();

    Mockito.verify(this.volumeRepository).findById(volumeId, editionId);
  }

  @Test
  @DisplayName("Should throw EntityNotFoundException when volume does not exist")
  void shouldThrowEntityNotFoundExceptionWhenVolumeDoesNotExist() {
    // Given
    final UUID volumeId = UUID.randomUUID();
    final UUID editionId = UUID.randomUUID();

    Mockito.when(this.volumeRepository.findById(volumeId, editionId)).thenReturn(null);

    // When & Then
    Assertions
        .assertThatThrownBy(() -> this.volumeValidator.validateVolumeExists(volumeId, editionId))
        .isInstanceOf(EntityNotFoundException.class).hasMessageContaining("Volume")
        .hasMessageContaining(volumeId.toString());

    Mockito.verify(this.volumeRepository).findById(volumeId, editionId);
  }

  @Test
  @DisplayName("Should call volumeRepository.findById with correct parameters")
  void shouldCallVolumeRepositoryFindByIdWithCorrectParameters() {
    // Given
    final UUID volumeId = UUID.randomUUID();
    final UUID editionId = UUID.randomUUID();
    final Volume volume = new Volume(volumeId, "Volume", 1,
        new Money("EUR", new BigDecimal("12.50")), LocalDate.now(), "978-1-1111-2222-3", 180);

    Mockito.when(this.volumeRepository.findById(volumeId, editionId)).thenReturn(volume);

    // When
    this.volumeValidator.validateVolumeExists(volumeId, editionId);

    // Then
    Mockito.verify(this.volumeRepository).findById(volumeId, editionId);
    Mockito.verifyNoMoreInteractions(this.volumeRepository);
  }

  @Test
  @DisplayName("Should throw EntityNotFoundException with volumeId in message")
  void shouldThrowEntityNotFoundExceptionWithVolumeIdInMessage() {
    // Given
    final UUID volumeId = UUID.fromString("12345678-1234-1234-1234-123456789012");
    final UUID editionId = UUID.randomUUID();

    Mockito.when(this.volumeRepository.findById(volumeId, editionId)).thenReturn(null);

    // When & Then
    Assertions
        .assertThatThrownBy(() -> this.volumeValidator.validateVolumeExists(volumeId, editionId))
        .isInstanceOf(EntityNotFoundException.class).hasMessageContaining("Volume")
        .hasMessageContaining("12345678-1234-1234-1234-123456789012")
        .hasMessageContaining("not found");

    Mockito.verify(this.volumeRepository).findById(volumeId, editionId);
  }
}
