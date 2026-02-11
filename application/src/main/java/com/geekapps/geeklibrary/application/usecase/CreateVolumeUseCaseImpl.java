package com.geekapps.geeklibrary.application.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.model.volume.CreateVolumeCommand;
import com.geekapps.geeklibrary.application.port.in.volume.CreateVolumeUseCase;
import com.geekapps.geeklibrary.domain.model.common.Money;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;
import com.geekapps.geeklibrary.domain.service.EditionValidatorService;
import com.geekapps.geeklibrary.domain.service.WorkValidatorService;

@Service
class CreateVolumeUseCaseImpl implements CreateVolumeUseCase {

  private final VolumeRepository volumeRepository;
  private final WorkValidatorService workValidator;
  private final EditionValidatorService editionValidator;

  public CreateVolumeUseCaseImpl(final VolumeRepository volumeRepository,
      final WorkValidatorService workValidator, final EditionValidatorService editionValidator) {
    this.volumeRepository = volumeRepository;
    this.workValidator = workValidator;
    this.editionValidator = editionValidator;
  }

  @Override
  public Volume execute(final CreateVolumeCommand input) {

    this.workValidator.validateWorkExists(input.workId());
    this.editionValidator.validateEditionExists(input.editionId(), input.workId());

    final var price = new Money(input.price().currency(), input.price().amount());

    final var volume = new Volume(input.title(), input.number(), price, input.publicationDate(),
        input.isbn(), input.pages());

    return this.volumeRepository.save(volume, input.editionId());
  }

}
