package com.geekapps.geeklibrary.application.port.in.edition;

import java.util.List;
import java.util.UUID;
import com.geekapps.geeklibrary.application.common.UseCase;
import com.geekapps.geeklibrary.domain.model.edition.Edition;

public interface GetEditionsByWorkIdUseCase extends UseCase<UUID, List<Edition>> {

}
