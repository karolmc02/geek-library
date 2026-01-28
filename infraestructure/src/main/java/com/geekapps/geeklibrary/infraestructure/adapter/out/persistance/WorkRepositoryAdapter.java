package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper.WorkEntityMapper;

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

}
