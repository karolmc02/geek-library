package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.geekapps.geeklibrary.domain.model.edition.Edition;
import com.geekapps.geeklibrary.domain.port.out.edition.EditionRepository;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper.EditionEntityMapper;

@Repository
public class EditionRepositoryAdapter implements EditionRepository {

  private final EditionJpaRepository editionJpaRepository;
  private final WorkJpaRepository workJpaRepository;
  private final EditionEntityMapper editionEntityMapper;

  public EditionRepositoryAdapter(final EditionJpaRepository editionJpaRepository,
      final WorkJpaRepository workJpaRepository, final EditionEntityMapper editionEntityMapper) {
    this.editionJpaRepository = editionJpaRepository;
    this.workJpaRepository = workJpaRepository;
    this.editionEntityMapper = editionEntityMapper;
  }

  @Override
  public Edition save(final Edition edition, final UUID workId) {
    final var workEntity = this.workJpaRepository.findById(workId).orElseThrow();
    final var editionEntity = this.editionEntityMapper.toEntity(edition);
    editionEntity.setWork(workEntity);
    final var savedEntity = this.editionJpaRepository.save(editionEntity);
    return this.editionEntityMapper.toDomain(savedEntity);
  }

  @Override
  public List<Edition> findByWorkId(final UUID workId) {
    final var entities = this.editionJpaRepository.findByWorkId(workId);
    return entities.stream().map(this.editionEntityMapper::toDomain).toList();
  }

  @Override
  public Edition findById(final UUID editionId, final UUID workId) {
    return this.editionJpaRepository.findByIdAndWorkId(editionId, workId)
        .map(this.editionEntityMapper::toDomain).orElse(null);
  }

  @Override
  public void deleteById(final UUID editionId, final UUID workId) {
    this.editionJpaRepository.deleteByIdAndWorkId(editionId, workId);
  }

}
