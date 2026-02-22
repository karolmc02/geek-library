package com.geekapps.geeklibrary.application.port.in.volume;

import com.geekapps.geeklibrary.application.common.UseCase;
import com.geekapps.geeklibrary.application.port.in.model.volume.UpdateVolumeCommand;
import com.geekapps.geeklibrary.domain.model.volume.Volume;

public interface UpdateVolumeUseCase extends UseCase<UpdateVolumeCommand, Volume> {

}
