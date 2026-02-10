package com.geekapps.geeklibrary.application.port.in.volume;

import java.util.List;
import java.util.UUID;
import com.geekapps.geeklibrary.application.common.UseCase;
import com.geekapps.geeklibrary.domain.model.volume.Volume;

public interface GetVolumesByEditionIdUseCase extends UseCase<UUID, List<Volume>> {

}
