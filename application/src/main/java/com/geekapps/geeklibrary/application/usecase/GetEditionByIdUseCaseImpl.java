package com.geekapps.geeklibrary.application.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.edition.GetEditionByIdUseCase;
import com.geekapps.geeklibrary.application.port.in.model.edition.GetEditionByIdCommand;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.model.edition.Edition;
import com.geekapps.geeklibrary.domain.port.out.edition.EditionRepository;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;

@Service
class GetEditionByIdUseCaseImpl implements GetEditionByIdUseCase {

  private final EditionRepository editionRepository;
  private final WorkRepository workRepository;

  public GetEditionByIdUseCaseImpl(final EditionRepository editionRepository,
      final WorkRepository workRepository) {
    this.editionRepository = editionRepository;
    this.workRepository = workRepository;
  }

  @Override
  public Edition execute(final GetEditionByIdCommand input) {
    final var work = this.workRepository.findById(input.workId());
    if (work == null) {
      throw new EntityNotFoundException("Work", input.workId());
    }

    final var edition = this.editionRepository.findById(input.editionId(), input.workId());
    if (edition == null) {
      throw new EntityNotFoundException("Edition", input.editionId());
    }

    return edition;
  }

}
