package com.geekapps.geeklibrary.application.port.in.edition;

import com.geekapps.geeklibrary.application.common.UseCase;
import com.geekapps.geeklibrary.application.port.in.model.edition.CreateEditionCommand;
import com.geekapps.geeklibrary.domain.model.edition.Edition;

public interface CreateEditionUseCase extends UseCase<CreateEditionCommand, Edition> {

}
