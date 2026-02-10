package com.geekapps.geeklibrary.domain.port.out.volume;

import java.util.List;
import java.util.UUID;
import com.geekapps.geeklibrary.domain.model.volume.Volume;

public interface VolumeRepository {

  Volume save(Volume volume, UUID editionId);

  List<Volume> findByEditionId(UUID editionId);

  Volume findById(UUID volumeId, UUID editionId);

  void deleteById(UUID volumeId, UUID editionId);

}
