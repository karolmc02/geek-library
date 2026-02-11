package com.geekapps.geeklibrary.application.usecase.edition;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.edition.GetEditionsByWorkIdUseCase;
import com.geekapps.geeklibrary.domain.model.edition.Edition;
import com.geekapps.geeklibrary.domain.port.out.edition.EditionRepository;
import com.geekapps.geeklibrary.domain.service.WorkValidatorService;

@Service
class GetEditionsByWorkIdUseCaseImpl implements GetEditionsByWorkIdUseCase {

  private final EditionRepository editionRepository;
  private final WorkValidatorService workValidator;

  public GetEditionsByWorkIdUseCaseImpl(final EditionRepository editionRepository,
      final WorkValidatorService workValidator) {
    this.editionRepository = editionRepository;
    this.workValidator = workValidator;
  }

  @Override
  public List<Edition> execute(final UUID workId) {
    this.workValidator.validateWorkExists(workId);

    return this.editionRepository.findByWorkId(workId);
  }

}
