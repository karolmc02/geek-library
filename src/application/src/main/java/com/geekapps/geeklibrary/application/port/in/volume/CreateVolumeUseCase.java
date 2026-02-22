package com.geekapps.geeklibrary.application.port.in.volume;

import com.geekapps.geeklibrary.application.common.UseCase;
import com.geekapps.geeklibrary.application.port.in.model.volume.CreateVolumeCommand;
import com.geekapps.geeklibrary.domain.model.volume.Volume;

public interface CreateVolumeUseCase extends UseCase<CreateVolumeCommand, Volume> {

}
