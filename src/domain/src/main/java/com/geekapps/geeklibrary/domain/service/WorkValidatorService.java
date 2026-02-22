package com.geekapps.geeklibrary.domain.service;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;

public class WorkValidatorService {

  private final WorkRepository workRepository;

  public WorkValidatorService(final WorkRepository workRepository) {
    this.workRepository = workRepository;
  }

  public void validateWorkExists(final UUID workId) {
    final var work = this.workRepository.findById(workId);
    if (work == null) {
      throw new EntityNotFoundException("Work", workId);
    }
  }

}
