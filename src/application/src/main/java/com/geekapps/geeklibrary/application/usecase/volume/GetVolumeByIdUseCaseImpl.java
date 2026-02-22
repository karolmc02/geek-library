package com.geekapps.geeklibrary.application.usecase.volume;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.model.volume.GetVolumeByIdCommand;
import com.geekapps.geeklibrary.application.port.in.volume.GetVolumeByIdUseCase;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;
import com.geekapps.geeklibrary.domain.service.EditionValidatorService;
import com.geekapps.geeklibrary.domain.service.VolumeValidatorService;
import com.geekapps.geeklibrary.domain.service.WorkValidatorService;

@Service
class GetVolumeByIdUseCaseImpl implements GetVolumeByIdUseCase {

  private final VolumeRepository volumeRepository;
  private final WorkValidatorService workValidator;
  private final EditionValidatorService editionValidator;
  private final VolumeValidatorService volumeValidator;

  public GetVolumeByIdUseCaseImpl(final VolumeRepository volumeRepository,
      final WorkValidatorService workValidator, final EditionValidatorService editionValidator,
      final VolumeValidatorService volumeValidator) {
    this.volumeRepository = volumeRepository;
    this.workValidator = workValidator;
    this.editionValidator = editionValidator;
    this.volumeValidator = volumeValidator;
  }

  @Override
  public Volume execute(final GetVolumeByIdCommand input) {
    this.workValidator.validateWorkExists(input.workId());
    this.editionValidator.validateEditionExists(input.editionId(), input.workId());
    this.volumeValidator.validateVolumeExists(input.volumeId(), input.editionId());

    return this.volumeRepository.findById(input.volumeId(), input.editionId());
  }

}
