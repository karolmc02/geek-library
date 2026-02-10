package com.geekapps.geeklibrary.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.geekapps.geeklibrary.application.port.in.model.to.MoneyTO;
import com.geekapps.geeklibrary.application.port.in.model.volume.CreateVolumeCommand;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.model.common.Money;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;
import com.geekapps.geeklibrary.domain.service.EditionValidator;
import com.geekapps.geeklibrary.domain.service.WorkValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateVolumeUseCase Tests")
class CreateVolumeUseCaseImplTest {

  @Mock
  private VolumeRepository volumeRepository;

  @Mock
  private WorkValidator workValidator;

  @Mock
  private EditionValidator editionValidator;

  @InjectMocks
  private CreateVolumeUseCaseImpl createVolumeUseCase;

  private UUID workId;
  private UUID editionId;
  private CreateVolumeCommand command;
  private Volume expectedVolume;

  @BeforeEach
  void setUp() {
    this.workId = UUID.randomUUID();
    this.editionId = UUID.randomUUID();

    final var priceTO = new MoneyTO("USD", new BigDecimal("9.99"));

    this.command = new CreateVolumeCommand(this.workId, this.editionId, "Volume 1", 1, priceTO,
        LocalDate.of(2024, 1, 15), "978-1-2345-6789-0", 200);

    final var price = new Money("USD", new BigDecimal("9.99"));
    this.expectedVolume = new Volume(UUID.randomUUID(), "Volume 1", 1, price,
        LocalDate.of(2024, 1, 15), "978-1-2345-6789-0", 200);
  }

  @Test
  @DisplayName("Should create volume successfully with valid data")
  void shouldCreateVolumeSuccessfullyWithValidData() {
    // Given
    Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
    Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
        this.workId);
    Mockito.when(this.volumeRepository.save(ArgumentMatchers.any(Volume.class),
        ArgumentMatchers.eq(this.editionId))).thenReturn(this.expectedVolume);

    // When
    final var result = this.createVolumeUseCase.execute(this.command);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getTitle()).isEqualTo("Volume 1");
    Assertions.assertThat(result.getNumber()).isEqualTo(1);
    Assertions.assertThat(result.getPrice().currency()).isEqualTo("USD");
    Assertions.assertThat(result.getPrice().amount()).isEqualByComparingTo(new BigDecimal("9.99"));
    Assertions.assertThat(result.getPublicationDate()).isEqualTo(LocalDate.of(2024, 1, 15));
    Assertions.assertThat(result.getIsbn()).isEqualTo("978-1-2345-6789-0");
    Assertions.assertThat(result.getPages()).isEqualTo(200);

    Mockito.verify(this.workValidator).validateWorkExists(this.workId);
    Mockito.verify(this.editionValidator).validateEditionExists(this.editionId, this.workId);
    Mockito.verify(this.volumeRepository).save(ArgumentMatchers.any(Volume.class),
        ArgumentMatchers.eq(this.editionId));
  }

  @Test
  @DisplayName("Should fail when work does not exist")
  void shouldFailWhenWorkDoesNotExist() {
    // Given
    Mockito.doThrow(new EntityNotFoundException("Work", this.workId)).when(this.workValidator)
        .validateWorkExists(this.workId);

    // When & Then
    Assertions.assertThatThrownBy(() -> this.createVolumeUseCase.execute(this.command))
        .isInstanceOf(EntityNotFoundException.class).hasMessageContaining("Work")
        .hasMessageContaining(this.workId.toString());

    Mockito.verify(this.workValidator).validateWorkExists(this.workId);
    Mockito.verifyNoInteractions(this.editionValidator);
    Mockito.verifyNoInteractions(this.volumeRepository);
  }

  @Test
  @DisplayName("Should fail when edition does not exist")
  void shouldFailWhenEditionDoesNotExist() {
    // Given
    Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
    Mockito.doThrow(new EntityNotFoundException("Edition", this.editionId))
        .when(this.editionValidator).validateEditionExists(this.editionId, this.workId);

    // When & Then
    Assertions.assertThatThrownBy(() -> this.createVolumeUseCase.execute(this.command))
        .isInstanceOf(EntityNotFoundException.class).hasMessageContaining("Edition")
        .hasMessageContaining(this.editionId.toString());

    Mockito.verify(this.workValidator).validateWorkExists(this.workId);
    Mockito.verify(this.editionValidator).validateEditionExists(this.editionId, this.workId);
    Mockito.verifyNoInteractions(this.volumeRepository);
  }

  @Test
  @DisplayName("Should call validators in correct order")
  void shouldCallValidatorsInCorrectOrder() {
    // Given
    final var inOrder =
        Mockito.inOrder(this.workValidator, this.editionValidator, this.volumeRepository);

    Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
    Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
        this.workId);
    Mockito.when(this.volumeRepository.save(ArgumentMatchers.any(Volume.class),
        ArgumentMatchers.eq(this.editionId))).thenReturn(this.expectedVolume);

    // When
    this.createVolumeUseCase.execute(this.command);

    // Then
    inOrder.verify(this.workValidator).validateWorkExists(this.workId);
    inOrder.verify(this.editionValidator).validateEditionExists(this.editionId, this.workId);
    inOrder.verify(this.volumeRepository).save(ArgumentMatchers.any(Volume.class),
        ArgumentMatchers.eq(this.editionId));
  }

  @Test
  @DisplayName("Should create Money from MoneyTO correctly")
  void shouldCreateMoneyFromMoneyToCorrectly() {
    // Given
    Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
    Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
        this.workId);

    final var volumeCaptor = ArgumentCaptor.forClass(Volume.class);
    Mockito
        .when(
            this.volumeRepository.save(volumeCaptor.capture(), ArgumentMatchers.eq(this.editionId)))
        .thenReturn(this.expectedVolume);

    // When
    this.createVolumeUseCase.execute(this.command);

    // Then
    final var capturedVolume = volumeCaptor.getValue();
    Assertions.assertThat(capturedVolume.getPrice()).isNotNull();
    Assertions.assertThat(capturedVolume.getPrice().currency()).isEqualTo("USD");
    Assertions.assertThat(capturedVolume.getPrice().amount())
        .isEqualByComparingTo(new BigDecimal("9.99"));
  }

  @Test
  @DisplayName("Should create Volume without UUID")
  void shouldCreateVolumeWithoutUuid() {
    // Given
    Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
    Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
        this.workId);

    final var volumeCaptor = ArgumentCaptor.forClass(Volume.class);
    Mockito
        .when(
            this.volumeRepository.save(volumeCaptor.capture(), ArgumentMatchers.eq(this.editionId)))
        .thenReturn(this.expectedVolume);

    // When
    this.createVolumeUseCase.execute(this.command);

    // Then
    final var capturedVolume = volumeCaptor.getValue();
    Assertions.assertThat(capturedVolume.getId()).isNotNull();
  }

  @Test
  @DisplayName("Should pass editionId to repository save method")
  void shouldPassEditionIdToRepositorySaveMethod() {
    // Given
    Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
    Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
        this.workId);
    Mockito.when(this.volumeRepository.save(ArgumentMatchers.any(Volume.class),
        ArgumentMatchers.eq(this.editionId))).thenReturn(this.expectedVolume);

    // When
    this.createVolumeUseCase.execute(this.command);

    // Then
    Mockito.verify(this.volumeRepository).save(ArgumentMatchers.any(Volume.class),
        ArgumentMatchers.eq(this.editionId));
  }

  @Test
  @DisplayName("Should return volume from repository")
  void shouldReturnVolumeFromRepository() {
    // Given
    Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
    Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
        this.workId);
    Mockito.when(this.volumeRepository.save(ArgumentMatchers.any(Volume.class),
        ArgumentMatchers.eq(this.editionId))).thenReturn(this.expectedVolume);

    // When
    final var result = this.createVolumeUseCase.execute(this.command);

    // Then
    Assertions.assertThat(result).isEqualTo(this.expectedVolume);
  }
}
