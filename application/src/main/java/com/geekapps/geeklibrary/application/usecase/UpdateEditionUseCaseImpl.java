package com.geekapps.geeklibrary.application.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.edition.UpdateEditionUseCase;
import com.geekapps.geeklibrary.application.port.in.model.edition.UpdateEditionCommand;
import com.geekapps.geeklibrary.domain.model.edition.Country;
import com.geekapps.geeklibrary.domain.model.edition.Edition;
import com.geekapps.geeklibrary.domain.model.edition.Format;
import com.geekapps.geeklibrary.domain.model.edition.Language;
import com.geekapps.geeklibrary.domain.port.out.edition.EditionRepository;
import com.geekapps.geeklibrary.domain.service.EditionValidator;
import com.geekapps.geeklibrary.domain.service.WorkValidator;

@Service
class UpdateEditionUseCaseImpl implements UpdateEditionUseCase {

  private final EditionRepository editionRepository;
  private final WorkValidator workValidator;
  private final EditionValidator editionValidator;

  public UpdateEditionUseCaseImpl(final EditionRepository editionRepository,
      final WorkValidator workValidator, final EditionValidator editionValidator) {
    this.editionRepository = editionRepository;
    this.workValidator = workValidator;
    this.editionValidator = editionValidator;
  }

  @Override
  public Edition execute(final UpdateEditionCommand input) {
    this.workValidator.validateWorkExists(input.workId());
    this.editionValidator.validateEditionExists(input.editionId(), input.workId());

    final var language = new Language(input.language().isoCode());
    final var country = new Country(input.country().name(), input.country().isoCode());
    final var format = new Format(input.format().id(), input.format().name(),
        input.format().description(), input.format().widthCm(), input.format().heightCm());

    final var edition = new Edition(input.editionId(), input.publisher(), language, country,
        input.isOriginal(), format, input.colorMode());

    return this.editionRepository.save(edition, input.workId());
  }

}
