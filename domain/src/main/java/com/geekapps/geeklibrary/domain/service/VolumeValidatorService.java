package com.geekapps.geeklibrary.domain.service;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;

public class VolumeValidatorService {

  private final VolumeRepository volumeRepository;

  public VolumeValidatorService(final VolumeRepository volumeRepository) {
    this.volumeRepository = volumeRepository;
  }

  public void validateVolumeExists(final UUID volumeId, final UUID editionId) {
    final var volume = this.volumeRepository.findById(volumeId, editionId);
    if (volume == null) {
      throw new EntityNotFoundException("Volume", volumeId);
    }
  }

}
