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
import com.geekapps.geeklibrary.application.port.in.model.volume.UpdateVolumeCommand;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.model.common.Money;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;
import com.geekapps.geeklibrary.domain.service.EditionValidatorService;
import com.geekapps.geeklibrary.domain.service.VolumeValidatorService;
import com.geekapps.geeklibrary.domain.service.WorkValidatorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateVolumeUseCase Tests")
class UpdateVolumeUseCaseImplTest {

    @Mock
    private VolumeRepository volumeRepository;

    @Mock
    private WorkValidatorService workValidator;

    @Mock
    private EditionValidatorService editionValidator;

    @Mock
    private VolumeValidatorService volumeValidator;

    @InjectMocks
    private UpdateVolumeUseCaseImpl updateVolumeUseCase;

    private UUID workId;
    private UUID editionId;
    private UUID volumeId;
    private UpdateVolumeCommand command;
    private Volume expectedVolume;

    @BeforeEach
    void setUp() {
        this.workId = UUID.randomUUID();
        this.editionId = UUID.randomUUID();
        this.volumeId = UUID.randomUUID();

        final var priceTO = new MoneyTO("EUR", new BigDecimal("14.99"));

        this.command = new UpdateVolumeCommand(this.workId, this.editionId, this.volumeId,
                "Volume 1 Updated", 1, priceTO, LocalDate.of(2024, 6, 15), "978-1-5555-6666-7",
                250);

        final var price = new Money("EUR", new BigDecimal("14.99"));
        this.expectedVolume = new Volume(this.volumeId, "Volume 1 Updated", 1, price,
                LocalDate.of(2024, 6, 15), "978-1-5555-6666-7", 250);
    }

    @Test
    @DisplayName("Should update volume successfully")
    void shouldUpdateVolumeSuccessfully() {
        // Given
        Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
        Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
                this.workId);
        Mockito.doNothing().when(this.volumeValidator).validateVolumeExists(this.volumeId,
                this.editionId);
        Mockito.when(this.volumeRepository.save(ArgumentMatchers.any(Volume.class),
                ArgumentMatchers.eq(this.editionId))).thenReturn(this.expectedVolume);

        // When
        final var result = this.updateVolumeUseCase.execute(this.command);

        // Then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(this.volumeId);
        Assertions.assertThat(result.getTitle()).isEqualTo("Volume 1 Updated");
        Assertions.assertThat(result.getNumber()).isEqualTo(1);
        Assertions.assertThat(result.getPrice().currency()).isEqualTo("EUR");
        Assertions.assertThat(result.getPrice().amount())
                .isEqualByComparingTo(new BigDecimal("14.99"));
        Assertions.assertThat(result.getPublicationDate()).isEqualTo(LocalDate.of(2024, 6, 15));
        Assertions.assertThat(result.getIsbn()).isEqualTo("978-1-5555-6666-7");
        Assertions.assertThat(result.getPages()).isEqualTo(250);

        Mockito.verify(this.workValidator).validateWorkExists(this.workId);
        Mockito.verify(this.editionValidator).validateEditionExists(this.editionId, this.workId);
        Mockito.verify(this.volumeValidator).validateVolumeExists(this.volumeId, this.editionId);
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
        Assertions.assertThatThrownBy(() -> this.updateVolumeUseCase.execute(this.command))
                .isInstanceOf(EntityNotFoundException.class).hasMessageContaining("Work");

        Mockito.verify(this.workValidator).validateWorkExists(this.workId);
        Mockito.verifyNoInteractions(this.editionValidator);
        Mockito.verifyNoInteractions(this.volumeValidator);
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
        Assertions.assertThatThrownBy(() -> this.updateVolumeUseCase.execute(this.command))
                .isInstanceOf(EntityNotFoundException.class).hasMessageContaining("Edition");

        Mockito.verify(this.workValidator).validateWorkExists(this.workId);
        Mockito.verify(this.editionValidator).validateEditionExists(this.editionId, this.workId);
        Mockito.verifyNoInteractions(this.volumeValidator);
        Mockito.verifyNoInteractions(this.volumeRepository);
    }

    @Test
    @DisplayName("Should fail when volume does not exist")
    void shouldFailWhenVolumeDoesNotExist() {
        // Given
        Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
        Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
                this.workId);
        Mockito.doThrow(new EntityNotFoundException("Volume", this.volumeId))
                .when(this.volumeValidator).validateVolumeExists(this.volumeId, this.editionId);

        // When & Then
        Assertions.assertThatThrownBy(() -> this.updateVolumeUseCase.execute(this.command))
                .isInstanceOf(EntityNotFoundException.class).hasMessageContaining("Volume");

        Mockito.verify(this.workValidator).validateWorkExists(this.workId);
        Mockito.verify(this.editionValidator).validateEditionExists(this.editionId, this.workId);
        Mockito.verify(this.volumeValidator).validateVolumeExists(this.volumeId, this.editionId);
        Mockito.verifyNoInteractions(this.volumeRepository);
    }

    @Test
    @DisplayName("Should call all validators in correct order")
    void shouldCallAllValidatorsInCorrectOrder() {
        // Given
        final var inOrder = Mockito.inOrder(this.workValidator, this.editionValidator,
                this.volumeValidator, this.volumeRepository);

        Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
        Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
                this.workId);
        Mockito.doNothing().when(this.volumeValidator).validateVolumeExists(this.volumeId,
                this.editionId);
        Mockito.when(this.volumeRepository.save(ArgumentMatchers.any(Volume.class),
                ArgumentMatchers.eq(this.editionId))).thenReturn(this.expectedVolume);

        // When
        this.updateVolumeUseCase.execute(this.command);

        // Then
        inOrder.verify(this.workValidator).validateWorkExists(this.workId);
        inOrder.verify(this.editionValidator).validateEditionExists(this.editionId, this.workId);
        inOrder.verify(this.volumeValidator).validateVolumeExists(this.volumeId, this.editionId);
        inOrder.verify(this.volumeRepository).save(ArgumentMatchers.any(Volume.class),
                ArgumentMatchers.eq(this.editionId));
    }

    @Test
    @DisplayName("Should create Money from UpdateVolumeCommand")
    void shouldCreateMoneyFromUpdateVolumeCommand() {
        // Given
        Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
        Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
                this.workId);
        Mockito.doNothing().when(this.volumeValidator).validateVolumeExists(this.volumeId,
                this.editionId);

        final var volumeCaptor = ArgumentCaptor.forClass(Volume.class);
        Mockito.when(this.volumeRepository.save(volumeCaptor.capture(),
                ArgumentMatchers.eq(this.editionId))).thenReturn(this.expectedVolume);

        // When
        this.updateVolumeUseCase.execute(this.command);

        // Then
        final var capturedVolume = volumeCaptor.getValue();
        Assertions.assertThat(capturedVolume.getPrice()).isNotNull();
        Assertions.assertThat(capturedVolume.getPrice().currency()).isEqualTo("EUR");
        Assertions.assertThat(capturedVolume.getPrice().amount())
                .isEqualByComparingTo(new BigDecimal("14.99"));
    }

    @Test
    @DisplayName("Should preserve volumeId from command")
    void shouldPreserveVolumeIdFromCommand() {
        // Given
        Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
        Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
                this.workId);
        Mockito.doNothing().when(this.volumeValidator).validateVolumeExists(this.volumeId,
                this.editionId);

        final var volumeCaptor = ArgumentCaptor.forClass(Volume.class);
        Mockito.when(this.volumeRepository.save(volumeCaptor.capture(),
                ArgumentMatchers.eq(this.editionId))).thenReturn(this.expectedVolume);

        // When
        this.updateVolumeUseCase.execute(this.command);

        // Then
        final var capturedVolume = volumeCaptor.getValue();
        Assertions.assertThat(capturedVolume.getId()).isEqualTo(this.volumeId);
    }

    @Test
    @DisplayName("Should create Volume with correct constructor parameters")
    void shouldCreateVolumeWithCorrectConstructorParameters() {
        // Given
        Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
        Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
                this.workId);
        Mockito.doNothing().when(this.volumeValidator).validateVolumeExists(this.volumeId,
                this.editionId);

        final var volumeCaptor = ArgumentCaptor.forClass(Volume.class);
        Mockito.when(this.volumeRepository.save(volumeCaptor.capture(),
                ArgumentMatchers.eq(this.editionId))).thenReturn(this.expectedVolume);

        // When
        this.updateVolumeUseCase.execute(this.command);

        // Then
        final var capturedVolume = volumeCaptor.getValue();
        Assertions.assertThat(capturedVolume.getId()).isEqualTo(this.volumeId);
        Assertions.assertThat(capturedVolume.getTitle()).isEqualTo("Volume 1 Updated");
        Assertions.assertThat(capturedVolume.getNumber()).isEqualTo(1);
        Assertions.assertThat(capturedVolume.getPublicationDate())
                .isEqualTo(LocalDate.of(2024, 6, 15));
        Assertions.assertThat(capturedVolume.getIsbn()).isEqualTo("978-1-5555-6666-7");
        Assertions.assertThat(capturedVolume.getPages()).isEqualTo(250);
    }

    @Test
    @DisplayName("Should pass editionId to repository save")
    void shouldPassEditionIdToRepositorySave() {
        // Given
        Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
        Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
                this.workId);
        Mockito.doNothing().when(this.volumeValidator).validateVolumeExists(this.volumeId,
                this.editionId);
        Mockito.when(this.volumeRepository.save(ArgumentMatchers.any(Volume.class),
                ArgumentMatchers.eq(this.editionId))).thenReturn(this.expectedVolume);

        // When
        this.updateVolumeUseCase.execute(this.command);

        // Then
        Mockito.verify(this.volumeRepository).save(ArgumentMatchers.any(Volume.class),
                ArgumentMatchers.eq(this.editionId));
    }

    @Test
    @DisplayName("Should return updated volume from repository")
    void shouldReturnUpdatedVolumeFromRepository() {
        // Given
        Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
        Mockito.doNothing().when(this.editionValidator).validateEditionExists(this.editionId,
                this.workId);
        Mockito.doNothing().when(this.volumeValidator).validateVolumeExists(this.volumeId,
                this.editionId);
        Mockito.when(this.volumeRepository.save(ArgumentMatchers.any(Volume.class),
                ArgumentMatchers.eq(this.editionId))).thenReturn(this.expectedVolume);

        // When
        final var result = this.updateVolumeUseCase.execute(this.command);

        // Then
        Assertions.assertThat(result).isEqualTo(this.expectedVolume);
    }
}
