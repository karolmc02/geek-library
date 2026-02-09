package com.geekapps.geeklibrary.application.port.in.work;

import java.util.UUID;
import com.geekapps.geeklibrary.application.common.UseCase;
import com.geekapps.geeklibrary.domain.model.work.Work;

public interface GetWorkByIdUseCase extends UseCase<UUID, Work> {

}
