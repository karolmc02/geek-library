package com.geekapps.geeklibrary.application.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.model.volume.DeleteVolumeCommand;
import com.geekapps.geeklibrary.application.port.in.volume.DeleteVolumeUseCase;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;

@Service
class DeleteVolumeUseCaseImpl implements DeleteVolumeUseCase {

  private final VolumeRepository volumeRepository;

  public DeleteVolumeUseCaseImpl(final VolumeRepository volumeRepository) {
    this.volumeRepository = volumeRepository;
  }

  @Override
  public Void execute(final DeleteVolumeCommand input) {
    this.volumeRepository.deleteById(input.volumeId(), input.editionId());
    return null;
  }

}
