package com.geekapps.geeklibrary.application.port.in.work;

import com.geekapps.geeklibrary.application.common.UseCase;
import com.geekapps.geeklibrary.application.port.in.model.CreateWorkCommand;
import com.geekapps.geeklibrary.domain.model.work.Work;

public interface CreateWorkUseCase extends UseCase<CreateWorkCommand, Work> {

}
