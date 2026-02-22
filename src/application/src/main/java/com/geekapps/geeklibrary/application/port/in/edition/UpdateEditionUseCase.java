package com.geekapps.geeklibrary.application.port.in.edition;

import com.geekapps.geeklibrary.application.common.UseCase;
import com.geekapps.geeklibrary.application.port.in.model.edition.UpdateEditionCommand;
import com.geekapps.geeklibrary.domain.model.edition.Edition;

public interface UpdateEditionUseCase extends UseCase<UpdateEditionCommand, Edition> {

}
