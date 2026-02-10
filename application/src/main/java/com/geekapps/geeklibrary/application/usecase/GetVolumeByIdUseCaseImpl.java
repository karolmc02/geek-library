package com.geekapps.geeklibrary.application.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.model.volume.GetVolumeByIdCommand;
import com.geekapps.geeklibrary.application.port.in.volume.GetVolumeByIdUseCase;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;
import com.geekapps.geeklibrary.domain.service.EditionValidator;
import com.geekapps.geeklibrary.domain.service.VolumeValidator;
import com.geekapps.geeklibrary.domain.service.WorkValidator;

@Service
class GetVolumeByIdUseCaseImpl implements GetVolumeByIdUseCase {

  private final VolumeRepository volumeRepository;
  private final WorkValidator workValidator;
  private final EditionValidator editionValidator;
  private final VolumeValidator volumeValidator;

  public GetVolumeByIdUseCaseImpl(final VolumeRepository volumeRepository,
      final WorkValidator workValidator, final EditionValidator editionValidator,
      final VolumeValidator volumeValidator) {
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
