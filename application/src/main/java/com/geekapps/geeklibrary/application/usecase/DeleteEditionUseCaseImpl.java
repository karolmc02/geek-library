package com.geekapps.geeklibrary.application.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.edition.DeleteEditionUseCase;
import com.geekapps.geeklibrary.application.port.in.model.edition.DeleteEditionCommand;
import com.geekapps.geeklibrary.domain.port.out.edition.EditionRepository;

@Service
class DeleteEditionUseCaseImpl implements DeleteEditionUseCase {

  private final EditionRepository editionRepository;

  public DeleteEditionUseCaseImpl(final EditionRepository editionRepository) {
    this.editionRepository = editionRepository;
  }

  @Override
  public Void execute(final DeleteEditionCommand input) {
    this.editionRepository.deleteById(input.editionId(), input.workId());
    return null;
  }

}
