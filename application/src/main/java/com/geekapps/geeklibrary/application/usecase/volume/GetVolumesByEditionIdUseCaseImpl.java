package com.geekapps.geeklibrary.application.usecase.volume;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.volume.GetVolumesByEditionIdUseCase;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;

@Service
class GetVolumesByEditionIdUseCaseImpl implements GetVolumesByEditionIdUseCase {

  private final VolumeRepository volumeRepository;

  public GetVolumesByEditionIdUseCaseImpl(final VolumeRepository volumeRepository) {
    this.volumeRepository = volumeRepository;
  }

  @Override
  public List<Volume> execute(final UUID editionId) {
    return this.volumeRepository.findByEditionId(editionId);
  }

}
