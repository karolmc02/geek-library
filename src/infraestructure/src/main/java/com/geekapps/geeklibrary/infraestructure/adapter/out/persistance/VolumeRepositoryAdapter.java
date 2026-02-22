package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper.VolumeEntityMapper;

@Repository
public class VolumeRepositoryAdapter implements VolumeRepository {

  private final VolumeJpaRepository volumeJpaRepository;
  private final EditionJpaRepository editionJpaRepository;
  private final VolumeEntityMapper volumeEntityMapper;

  public VolumeRepositoryAdapter(final VolumeJpaRepository volumeJpaRepository,
      final EditionJpaRepository editionJpaRepository,
      final VolumeEntityMapper volumeEntityMapper) {
    this.volumeJpaRepository = volumeJpaRepository;
    this.editionJpaRepository = editionJpaRepository;
    this.volumeEntityMapper = volumeEntityMapper;
  }

  @Override
  public Volume save(final Volume volume, final UUID editionId) {
    final var editionEntity = this.editionJpaRepository.findById(editionId).orElseThrow();
    final var volumeEntity = this.volumeEntityMapper.toEntity(volume);
    volumeEntity.setEdition(editionEntity);
    final var savedEntity = this.volumeJpaRepository.save(volumeEntity);
    return this.volumeEntityMapper.toDomain(savedEntity);
  }

  @Override
  public List<Volume> findByEditionId(final UUID editionId) {
    final var entities = this.volumeJpaRepository.findByEditionId(editionId);
    return entities.stream().map(this.volumeEntityMapper::toDomain).toList();
  }

  @Override
  public Volume findById(final UUID volumeId, final UUID editionId) {
    return this.volumeJpaRepository.findByIdAndEditionId(volumeId, editionId)
        .map(this.volumeEntityMapper::toDomain).orElse(null);
  }

  @Override
  public void deleteById(final UUID volumeId, final UUID editionId) {
    this.volumeJpaRepository.deleteByIdAndEditionId(volumeId, editionId);
  }

}
