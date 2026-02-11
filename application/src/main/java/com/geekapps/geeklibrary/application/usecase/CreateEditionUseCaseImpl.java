package com.geekapps.geeklibrary.application.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.edition.CreateEditionUseCase;
import com.geekapps.geeklibrary.application.port.in.model.edition.CreateEditionCommand;
import com.geekapps.geeklibrary.domain.model.edition.Country;
import com.geekapps.geeklibrary.domain.model.edition.Edition;
import com.geekapps.geeklibrary.domain.model.edition.Format;
import com.geekapps.geeklibrary.domain.model.edition.Language;
import com.geekapps.geeklibrary.domain.port.out.edition.EditionRepository;
import com.geekapps.geeklibrary.domain.service.WorkValidatorService;

@Service
class CreateEditionUseCaseImpl implements CreateEditionUseCase {

  private final EditionRepository editionRepository;
  private final WorkValidatorService workValidator;

  public CreateEditionUseCaseImpl(final EditionRepository editionRepository,
      final WorkValidatorService workValidator) {
    this.editionRepository = editionRepository;
    this.workValidator = workValidator;
  }

  @Override
  public Edition execute(final CreateEditionCommand input) {

    this.workValidator.validateWorkExists(input.workId());

    final var language = new Language(input.language().isoCode());
    final var country = new Country(input.country().isoCode());
    final var format = new Format(input.format().id(), input.format().name(),
        input.format().description(), input.format().widthCm(), input.format().heightCm());

    final var edition = new Edition(input.publisher(), language, country, input.isOriginal(),
        format, input.colorMode());

    return this.editionRepository.save(edition, input.workId());
  }

}
