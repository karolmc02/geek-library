package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.geekapps.geeklibrary.domain.model.common.Money;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.EditionEntity;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.MoneyEmbeddable;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.VolumeEntity;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper.VolumeEntityMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("VolumeRepositoryAdapter Tests")
class VolumeRepositoryAdapterTest {

  @Mock
  private VolumeJpaRepository volumeJpaRepository;

  @Mock
  private EditionJpaRepository editionJpaRepository;

  @Mock
  private VolumeEntityMapper volumeEntityMapper;

  @InjectMocks
  private VolumeRepositoryAdapter volumeRepositoryAdapter;

  private Volume volume;
  private VolumeEntity volumeEntity;
  private EditionEntity editionEntity;
  private UUID volumeId;
  private UUID editionId;

  @BeforeEach
  void setUp() {
    this.volumeId = UUID.randomUUID();
    this.editionId = UUID.randomUUID();

    final var price = new Money("USD", new BigDecimal("9.99"));
    this.volume = new Volume(this.volumeId, "Volume 1", 1, price, LocalDate.of(2024, 1, 15),
        "978-1-2345-6789-0", 200);

    this.volumeEntity = new VolumeEntity();
    this.volumeEntity.setId(this.volumeId);
    this.volumeEntity.setTitle("Volume 1");
    this.volumeEntity.setNumber(1);
    final var priceEmbeddable = new MoneyEmbeddable();
    priceEmbeddable.setCurrency("USD");
    priceEmbeddable.setAmount(new BigDecimal("9.99"));
    this.volumeEntity.setPrice(priceEmbeddable);
    this.volumeEntity.setPublicationDate(LocalDate.of(2024, 1, 15));
    this.volumeEntity.setIsbn("978-1-2345-6789-0");
    this.volumeEntity.setPages(200);

    this.editionEntity = new EditionEntity();
    this.editionEntity.setId(this.editionId);
    this.volumeEntity.setEdition(this.editionEntity);
  }

  @Test
  @DisplayName("Should save volume successfully")
  void shouldSaveVolumeSuccessfully() {
    // Given
    Mockito.when(this.editionJpaRepository.findById(this.editionId))
        .thenReturn(Optional.of(this.editionEntity));
    Mockito.when(this.volumeEntityMapper.toEntity(this.volume)).thenReturn(this.volumeEntity);
    Mockito.when(this.volumeJpaRepository.save(this.volumeEntity)).thenReturn(this.volumeEntity);
    Mockito.when(this.volumeEntityMapper.toDomain(this.volumeEntity)).thenReturn(this.volume);

    // When
    final var result = this.volumeRepositoryAdapter.save(this.volume, this.editionId);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(this.volumeId);
    Assertions.assertThat(result.getTitle()).isEqualTo("Volume 1");
    Assertions.assertThat(result.getNumber()).isEqualTo(1);

    Mockito.verify(this.editionJpaRepository).findById(this.editionId);
    Mockito.verify(this.volumeEntityMapper).toEntity(this.volume);
    Mockito.verify(this.volumeJpaRepository).save(this.volumeEntity);
    Mockito.verify(this.volumeEntityMapper).toDomain(this.volumeEntity);
  }

  @Test
  @DisplayName("Should associate volume with edition when saving")
  void shouldAssociateVolumeWithEditionWhenSaving() {
    // Given
    Mockito.when(this.editionJpaRepository.findById(this.editionId))
        .thenReturn(Optional.of(this.editionEntity));
    Mockito.when(this.volumeEntityMapper.toEntity(this.volume)).thenReturn(this.volumeEntity);
    Mockito.when(this.volumeJpaRepository.save(ArgumentMatchers.any(VolumeEntity.class)))
        .thenReturn(this.volumeEntity);
    Mockito.when(this.volumeEntityMapper.toDomain(this.volumeEntity)).thenReturn(this.volume);

    // When
    final var result = this.volumeRepositoryAdapter.save(this.volume, this.editionId);

    // Then
    Assertions.assertThat(result).isNotNull();
    Mockito.verify(this.editionJpaRepository).findById(this.editionId);
    Mockito.verify(this.volumeJpaRepository).save(ArgumentMatchers.any(VolumeEntity.class));
  }

  @Test
  @DisplayName("Should map Volume to VolumeEntity")
  void shouldMapVolumeToVolumeEntity() {
    // Given
    Mockito.when(this.editionJpaRepository.findById(this.editionId))
        .thenReturn(Optional.of(this.editionEntity));
    Mockito.when(this.volumeEntityMapper.toEntity(this.volume)).thenReturn(this.volumeEntity);
    Mockito.when(this.volumeJpaRepository.save(this.volumeEntity)).thenReturn(this.volumeEntity);
    Mockito.when(this.volumeEntityMapper.toDomain(this.volumeEntity)).thenReturn(this.volume);

    // When
    this.volumeRepositoryAdapter.save(this.volume, this.editionId);

    // Then
    Mockito.verify(this.volumeEntityMapper).toEntity(this.volume);
  }

  @Test
  @DisplayName("Should map saved VolumeEntity to Volume domain")
  void shouldMapSavedVolumeEntityToVolumeDomain() {
    // Given
    Mockito.when(this.editionJpaRepository.findById(this.editionId))
        .thenReturn(Optional.of(this.editionEntity));
    Mockito.when(this.volumeEntityMapper.toEntity(this.volume)).thenReturn(this.volumeEntity);
    Mockito.when(this.volumeJpaRepository.save(this.volumeEntity)).thenReturn(this.volumeEntity);
    Mockito.when(this.volumeEntityMapper.toDomain(this.volumeEntity)).thenReturn(this.volume);

    // When
    this.volumeRepositoryAdapter.save(this.volume, this.editionId);

    // Then
    Mockito.verify(this.volumeEntityMapper).toDomain(this.volumeEntity);
  }

  @Test
  @DisplayName("Should find volumes by edition ID")
  void shouldFindVolumesByEditionId() {
    // Given
    final var volume2 =
        new Volume(UUID.randomUUID(), "Volume 2", 2, new Money("USD", new BigDecimal("9.99")),
            LocalDate.of(2024, 3, 20), "978-1-1111-2222-3", 180);

    final var volumeEntity2 = new VolumeEntity();
    volumeEntity2.setId(UUID.randomUUID());
    volumeEntity2.setTitle("Volume 2");

    final var entities = List.of(this.volumeEntity, volumeEntity2);

    Mockito.when(this.volumeJpaRepository.findByEditionId(this.editionId)).thenReturn(entities);
    Mockito.when(this.volumeEntityMapper.toDomain(this.volumeEntity)).thenReturn(this.volume);
    Mockito.when(this.volumeEntityMapper.toDomain(volumeEntity2)).thenReturn(volume2);

    // When
    final var result = this.volumeRepositoryAdapter.findByEditionId(this.editionId);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).hasSize(2);
    Assertions.assertThat(result.get(0).getTitle()).isEqualTo("Volume 1");
    Assertions.assertThat(result.get(1).getTitle()).isEqualTo("Volume 2");

    Mockito.verify(this.volumeJpaRepository).findByEditionId(this.editionId);
    Mockito.verify(this.volumeEntityMapper).toDomain(this.volumeEntity);
    Mockito.verify(this.volumeEntityMapper).toDomain(volumeEntity2);
  }

  @Test
  @DisplayName("Should return empty list when edition has no volumes")
  void shouldReturnEmptyListWhenEditionHasNoVolumes() {
    // Given
    Mockito.when(this.volumeJpaRepository.findByEditionId(this.editionId))
        .thenReturn(new ArrayList<>());

    // When
    final var result = this.volumeRepositoryAdapter.findByEditionId(this.editionId);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isEmpty();

    Mockito.verify(this.volumeJpaRepository).findByEditionId(this.editionId);
  }

  @Test
  @DisplayName("Should find volume by ID and edition ID when it exists")
  void shouldFindVolumeByIdAndEditionIdWhenItExists() {
    // Given
    Mockito.when(this.volumeJpaRepository.findByIdAndEditionId(this.volumeId, this.editionId))
        .thenReturn(Optional.of(this.volumeEntity));
    Mockito.when(this.volumeEntityMapper.toDomain(this.volumeEntity)).thenReturn(this.volume);

    // When
    final var result = this.volumeRepositoryAdapter.findById(this.volumeId, this.editionId);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(this.volumeId);
    Assertions.assertThat(result.getTitle()).isEqualTo("Volume 1");

    Mockito.verify(this.volumeJpaRepository).findByIdAndEditionId(this.volumeId, this.editionId);
    Mockito.verify(this.volumeEntityMapper).toDomain(this.volumeEntity);
  }

  @Test
  @DisplayName("Should return null when volume not found by ID")
  void shouldReturnNullWhenVolumeNotFoundById() {
    // Given
    Mockito.when(this.volumeJpaRepository.findByIdAndEditionId(this.volumeId, this.editionId))
        .thenReturn(Optional.empty());

    // When
    final var result = this.volumeRepositoryAdapter.findById(this.volumeId, this.editionId);

    // Then
    Assertions.assertThat(result).isNull();

    Mockito.verify(this.volumeJpaRepository).findByIdAndEditionId(this.volumeId, this.editionId);
  }

  @Test
  @DisplayName("Should delete volume by ID and edition ID")
  void shouldDeleteVolumeByIdAndEditionId() {
    // When
    this.volumeRepositoryAdapter.deleteById(this.volumeId, this.editionId);

    // Then
    Mockito.verify(this.volumeJpaRepository).deleteByIdAndEditionId(this.volumeId, this.editionId);
  }

  @Test
  @DisplayName("Should call JPA repository with correct parameters for delete")
  void shouldCallJpaRepositoryWithCorrectParametersForDelete() {
    // When
    this.volumeRepositoryAdapter.deleteById(this.volumeId, this.editionId);

    // Then
    Mockito.verify(this.volumeJpaRepository).deleteByIdAndEditionId(this.volumeId, this.editionId);
    Mockito.verifyNoMoreInteractions(this.volumeJpaRepository);
  }
}
