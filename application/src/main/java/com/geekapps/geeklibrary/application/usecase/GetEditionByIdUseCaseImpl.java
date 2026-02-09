package com.geekapps.geeklibrary.application.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.edition.GetEditionByIdUseCase;
import com.geekapps.geeklibrary.application.port.in.model.edition.GetEditionByIdCommand;
import com.geekapps.geeklibrary.domain.model.edition.Edition;
import com.geekapps.geeklibrary.domain.port.out.edition.EditionRepository;
import com.geekapps.geeklibrary.domain.service.EditionValidator;
import com.geekapps.geeklibrary.domain.service.WorkValidator;

@Service
class GetEditionByIdUseCaseImpl implements GetEditionByIdUseCase {

  private final EditionRepository editionRepository;
  private final WorkValidator workValidator;
  private final EditionValidator editionValidator;

  public GetEditionByIdUseCaseImpl(final EditionRepository editionRepository,
      final WorkValidator workValidator, final EditionValidator editionValidator) {
    this.editionRepository = editionRepository;
    this.workValidator = workValidator;
    this.editionValidator = editionValidator;
  }

  @Override
  public Edition execute(final GetEditionByIdCommand input) {
    this.workValidator.validateWorkExists(input.workId());
    this.editionValidator.validateEditionExists(input.editionId(), input.workId());

    return this.editionRepository.findById(input.editionId(), input.workId());
  }

}
