package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper.WorkEntityMapper;

@Repository
public class WorkRepositoryAdapter implements WorkRepository {

  private final WorkJpaRepository workJpaRepository;

  private final WorkEntityMapper workEntityMapper;

  public WorkRepositoryAdapter(final WorkJpaRepository workJpaRepository,
      final WorkEntityMapper workEntityMapper) {
    this.workJpaRepository = workJpaRepository;
    this.workEntityMapper = workEntityMapper;
  }

  @Override
  public Work save(final Work work) {
    final var workEntity = this.workEntityMapper.toEntity(work);
    final var savedEntity = this.workJpaRepository.save(workEntity);
    return this.workEntityMapper.toDomain(savedEntity);
  }

  @Override
  public List<Work> query(final String title, final String author) {
    final var entities = this.workJpaRepository.query(title, author);
    return entities.stream().map(this.workEntityMapper::toDomain).toList();
  }

  @Override
  public Work findById(final UUID id) {
    return this.workJpaRepository.findById(id).map(this.workEntityMapper::toDomain).orElse(null);
  }

}
