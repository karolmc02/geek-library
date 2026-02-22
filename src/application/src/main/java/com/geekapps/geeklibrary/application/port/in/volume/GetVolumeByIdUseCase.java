package com.geekapps.geeklibrary.application.port.in.volume;

import com.geekapps.geeklibrary.application.common.UseCase;
import com.geekapps.geeklibrary.application.port.in.model.volume.GetVolumeByIdCommand;
import com.geekapps.geeklibrary.domain.model.volume.Volume;

public interface GetVolumeByIdUseCase extends UseCase<GetVolumeByIdCommand, Volume> {

}
