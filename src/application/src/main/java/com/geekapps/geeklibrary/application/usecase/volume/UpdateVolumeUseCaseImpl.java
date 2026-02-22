package com.geekapps.geeklibrary.application.usecase.volume;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.model.volume.UpdateVolumeCommand;
import com.geekapps.geeklibrary.application.port.in.volume.UpdateVolumeUseCase;
import com.geekapps.geeklibrary.domain.model.common.Money;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;
import com.geekapps.geeklibrary.domain.service.EditionValidatorService;
import com.geekapps.geeklibrary.domain.service.VolumeValidatorService;
import com.geekapps.geeklibrary.domain.service.WorkValidatorService;

@Service
class UpdateVolumeUseCaseImpl implements UpdateVolumeUseCase {

  private final VolumeRepository volumeRepository;
  private final WorkValidatorService workValidator;
  private final EditionValidatorService editionValidator;
  private final VolumeValidatorService volumeValidator;

  public UpdateVolumeUseCaseImpl(final VolumeRepository volumeRepository,
      final WorkValidatorService workValidator, final EditionValidatorService editionValidator,
      final VolumeValidatorService volumeValidator) {
    this.volumeRepository = volumeRepository;
    this.workValidator = workValidator;
    this.editionValidator = editionValidator;
    this.volumeValidator = volumeValidator;
  }

  @Override
  public Volume execute(final UpdateVolumeCommand input) {
    this.workValidator.validateWorkExists(input.workId());
    this.editionValidator.validateEditionExists(input.editionId(), input.workId());
    this.volumeValidator.validateVolumeExists(input.volumeId(), input.editionId());

    final var price = new Money(input.price().currency(), input.price().amount());

    final var volume = new Volume(input.volumeId(), input.title(), input.number(), price,
        input.publicationDate(), input.isbn(), input.pages());

    return this.volumeRepository.save(volume, input.editionId());
  }

}
