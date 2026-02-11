package com.geekapps.geeklibrary.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import com.geekapps.geeklibrary.application.usecase.volume.GetVolumesByEditionIdUseCaseImpl;
import com.geekapps.geeklibrary.domain.model.common.Money;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetVolumesByEditionIdUseCase Tests")
class GetVolumesByEditionIdUseCaseImplTest {

  @Mock
  private VolumeRepository volumeRepository;

  @InjectMocks
  private GetVolumesByEditionIdUseCaseImpl getVolumesByEditionIdUseCase;

  private UUID editionId;
  private List<Volume> expectedVolumes;

  @BeforeEach
  void setUp() {
    this.editionId = UUID.randomUUID();

    final var price1 = new Money("USD", new BigDecimal("9.99"));
    final var volume1 = new Volume(UUID.randomUUID(), "Volume 1", 1, price1,
        LocalDate.of(2024, 1, 15), "978-1-2345-6789-0", 200);

    final var price2 = new Money("USD", new BigDecimal("9.99"));
    final var volume2 = new Volume(UUID.randomUUID(), "Volume 2", 2, price2,
        LocalDate.of(2024, 3, 20), "978-1-1111-2222-3", 180);

    final var price3 = new Money("USD", new BigDecimal("9.99"));
    final var volume3 = new Volume(UUID.randomUUID(), "Volume 3", 3, price3,
        LocalDate.of(2024, 5, 10), "978-1-9999-8888-7", 220);

    this.expectedVolumes = new ArrayList<>();
    this.expectedVolumes.add(volume1);
    this.expectedVolumes.add(volume2);
    this.expectedVolumes.add(volume3);
  }

  @Test
  @DisplayName("Should return list of volumes when edition has volumes")
  void shouldReturnListOfVolumesWhenEditionHasVolumes() {
    // Given
    Mockito.when(this.volumeRepository.findByEditionId(this.editionId))
        .thenReturn(this.expectedVolumes);

    // When
    final var result = this.getVolumesByEditionIdUseCase.execute(this.editionId);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).hasSize(3);
    Assertions.assertThat(result.get(0).getTitle()).isEqualTo("Volume 1");
    Assertions.assertThat(result.get(1).getTitle()).isEqualTo("Volume 2");
    Assertions.assertThat(result.get(2).getTitle()).isEqualTo("Volume 3");

    Mockito.verify(this.volumeRepository).findByEditionId(this.editionId);
  }

  @Test
  @DisplayName("Should return empty list when edition has no volumes")
  void shouldReturnEmptyListWhenEditionHasNoVolumes() {
    // Given
    Mockito.when(this.volumeRepository.findByEditionId(this.editionId))
        .thenReturn(new ArrayList<>());

    // When
    final var result = this.getVolumesByEditionIdUseCase.execute(this.editionId);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isEmpty();

    Mockito.verify(this.volumeRepository).findByEditionId(this.editionId);
  }

  @Test
  @DisplayName("Should call repository with correct editionId")
  void shouldCallRepositoryWithCorrectEditionId() {
    // Given
    Mockito.when(this.volumeRepository.findByEditionId(this.editionId))
        .thenReturn(this.expectedVolumes);

    // When
    this.getVolumesByEditionIdUseCase.execute(this.editionId);

    // Then
    Mockito.verify(this.volumeRepository).findByEditionId(this.editionId);
    Mockito.verifyNoMoreInteractions(this.volumeRepository);
  }

  @Test
  @DisplayName("Should return volumes in correct order")
  void shouldReturnVolumesInCorrectOrder() {
    // Given
    Mockito.when(this.volumeRepository.findByEditionId(this.editionId))
        .thenReturn(this.expectedVolumes);

    // When
    final var result = this.getVolumesByEditionIdUseCase.execute(this.editionId);

    // Then
    Assertions.assertThat(result).hasSize(3);
    Assertions.assertThat(result.get(0).getNumber()).isEqualTo(1);
    Assertions.assertThat(result.get(1).getNumber()).isEqualTo(2);
    Assertions.assertThat(result.get(2).getNumber()).isEqualTo(3);
  }

  @Test
  @DisplayName("Should return volumes with all properties")
  void shouldReturnVolumesWithAllProperties() {
    // Given
    Mockito.when(this.volumeRepository.findByEditionId(this.editionId))
        .thenReturn(this.expectedVolumes);

    // When
    final var result = this.getVolumesByEditionIdUseCase.execute(this.editionId);

    // Then
    final var firstVolume = result.get(0);
    Assertions.assertThat(firstVolume.getId()).isNotNull();
    Assertions.assertThat(firstVolume.getTitle()).isEqualTo("Volume 1");
    Assertions.assertThat(firstVolume.getNumber()).isEqualTo(1);
    Assertions.assertThat(firstVolume.getPrice()).isNotNull();
    Assertions.assertThat(firstVolume.getPublicationDate()).isEqualTo(LocalDate.of(2024, 1, 15));
    Assertions.assertThat(firstVolume.getIsbn()).isEqualTo("978-1-2345-6789-0");
    Assertions.assertThat(firstVolume.getPages()).isEqualTo(200);
  }
}
