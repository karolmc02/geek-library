package com.geekapps.geeklibrary.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.geekapps.geeklibrary.application.port.in.model.volume.GetVolumeByIdCommand;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.model.common.Money;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;
import com.geekapps.geeklibrary.domain.service.EditionValidatorService;
import com.geekapps.geeklibrary.domain.service.VolumeValidatorService;
import com.geekapps.geeklibrary.domain.service.WorkValidatorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetVolumeByIdUseCase Tests")
class GetVolumeByIdUseCaseImplTest {

        @Mock
        private VolumeRepository volumeRepository;

        @Mock
        private WorkValidatorService workValidator;

        @Mock
        private EditionValidatorService editionValidator;

        @Mock
        private VolumeValidatorService volumeValidator;

        @InjectMocks
        private GetVolumeByIdUseCaseImpl getVolumeByIdUseCase;

        private UUID workId;
        private UUID editionId;
        private UUID volumeId;
        private GetVolumeByIdCommand command;
        private Volume expectedVolume;

        @BeforeEach
        void setUp() {
                this.workId = UUID.randomUUID();
                this.editionId = UUID.randomUUID();
                this.volumeId = UUID.randomUUID();

                this.command = new GetVolumeByIdCommand(this.workId, this.editionId, this.volumeId);

                final var price = new Money("USD", new BigDecimal("9.99"));
                this.expectedVolume = new Volume(this.volumeId, "Volume 1", 1, price,
                                LocalDate.of(2024, 1, 15), "978-1-2345-6789-0", 200);
        }

        @Test
        @DisplayName("Should return volume when it exists")
        void shouldReturnVolumeWhenItExists() {
                // Given
                Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
                Mockito.doNothing().when(this.editionValidator)
                                .validateEditionExists(this.editionId, this.workId);
                Mockito.doNothing().when(this.volumeValidator).validateVolumeExists(this.volumeId,
                                this.editionId);
                Mockito.when(this.volumeRepository.findById(this.volumeId, this.editionId))
                                .thenReturn(this.expectedVolume);

                // When
                final var result = this.getVolumeByIdUseCase.execute(this.command);

                // Then
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.getId()).isEqualTo(this.volumeId);
                Assertions.assertThat(result.getTitle()).isEqualTo("Volume 1");
                Assertions.assertThat(result.getNumber()).isEqualTo(1);

                Mockito.verify(this.workValidator).validateWorkExists(this.workId);
                Mockito.verify(this.editionValidator).validateEditionExists(this.editionId,
                                this.workId);
                Mockito.verify(this.volumeValidator).validateVolumeExists(this.volumeId,
                                this.editionId);
                Mockito.verify(this.volumeRepository).findById(this.volumeId, this.editionId);
        }

        @Test
        @DisplayName("Should fail when work does not exist")
        void shouldFailWhenWorkDoesNotExist() {
                // Given
                Mockito.doThrow(new EntityNotFoundException("Work", this.workId))
                                .when(this.workValidator).validateWorkExists(this.workId);

                // When & Then
                Assertions.assertThatThrownBy(() -> this.getVolumeByIdUseCase.execute(this.command))
                                .isInstanceOf(EntityNotFoundException.class)
                                .hasMessageContaining("Work");

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
                                .when(this.editionValidator)
                                .validateEditionExists(this.editionId, this.workId);

                // When & Then
                Assertions.assertThatThrownBy(() -> this.getVolumeByIdUseCase.execute(this.command))
                                .isInstanceOf(EntityNotFoundException.class)
                                .hasMessageContaining("Edition");

                Mockito.verify(this.workValidator).validateWorkExists(this.workId);
                Mockito.verify(this.editionValidator).validateEditionExists(this.editionId,
                                this.workId);
                Mockito.verifyNoInteractions(this.volumeValidator);
                Mockito.verifyNoInteractions(this.volumeRepository);
        }

        @Test
        @DisplayName("Should fail when volume does not exist")
        void shouldFailWhenVolumeDoesNotExist() {
                // Given
                Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
                Mockito.doNothing().when(this.editionValidator)
                                .validateEditionExists(this.editionId, this.workId);
                Mockito.doThrow(new EntityNotFoundException("Volume", this.volumeId))
                                .when(this.volumeValidator)
                                .validateVolumeExists(this.volumeId, this.editionId);

                // When & Then
                Assertions.assertThatThrownBy(() -> this.getVolumeByIdUseCase.execute(this.command))
                                .isInstanceOf(EntityNotFoundException.class)
                                .hasMessageContaining("Volume");

                Mockito.verify(this.workValidator).validateWorkExists(this.workId);
                Mockito.verify(this.editionValidator).validateEditionExists(this.editionId,
                                this.workId);
                Mockito.verify(this.volumeValidator).validateVolumeExists(this.volumeId,
                                this.editionId);
                Mockito.verifyNoInteractions(this.volumeRepository);
        }

        @Test
        @DisplayName("Should call all validators in correct order")
        void shouldCallAllValidatorsInCorrectOrder() {
                // Given
                final var inOrder = Mockito.inOrder(this.workValidator, this.editionValidator,
                                this.volumeValidator, this.volumeRepository);

                Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
                Mockito.doNothing().when(this.editionValidator)
                                .validateEditionExists(this.editionId, this.workId);
                Mockito.doNothing().when(this.volumeValidator).validateVolumeExists(this.volumeId,
                                this.editionId);
                Mockito.when(this.volumeRepository.findById(this.volumeId, this.editionId))
                                .thenReturn(this.expectedVolume);

                // When
                this.getVolumeByIdUseCase.execute(this.command);

                // Then
                inOrder.verify(this.workValidator).validateWorkExists(this.workId);
                inOrder.verify(this.editionValidator).validateEditionExists(this.editionId,
                                this.workId);
                inOrder.verify(this.volumeValidator).validateVolumeExists(this.volumeId,
                                this.editionId);
                inOrder.verify(this.volumeRepository).findById(this.volumeId, this.editionId);
        }

        @Test
        @DisplayName("Should call repository with correct parameters")
        void shouldCallRepositoryWithCorrectParameters() {
                // Given
                Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
                Mockito.doNothing().when(this.editionValidator)
                                .validateEditionExists(this.editionId, this.workId);
                Mockito.doNothing().when(this.volumeValidator).validateVolumeExists(this.volumeId,
                                this.editionId);
                Mockito.when(this.volumeRepository.findById(this.volumeId, this.editionId))
                                .thenReturn(this.expectedVolume);

                // When
                this.getVolumeByIdUseCase.execute(this.command);

                // Then
                Mockito.verify(this.volumeRepository).findById(this.volumeId, this.editionId);
                Mockito.verifyNoMoreInteractions(this.volumeRepository);
        }

        @Test
        @DisplayName("Should return volume from repository")
        void shouldReturnVolumeFromRepository() {
                // Given
                Mockito.doNothing().when(this.workValidator).validateWorkExists(this.workId);
                Mockito.doNothing().when(this.editionValidator)
                                .validateEditionExists(this.editionId, this.workId);
                Mockito.doNothing().when(this.volumeValidator).validateVolumeExists(this.volumeId,
                                this.editionId);
                Mockito.when(this.volumeRepository.findById(this.volumeId, this.editionId))
                                .thenReturn(this.expectedVolume);

                // When
                final var result = this.getVolumeByIdUseCase.execute(this.command);

                // Then
                Assertions.assertThat(result).isEqualTo(this.expectedVolume);
        }
}
