package com.geekapps.geeklibrary.application.port.in;

import java.util.List;
import com.geekapps.geeklibrary.application.common.UseCase;
import com.geekapps.geeklibrary.application.port.in.model.QueryWorksCommand;
import com.geekapps.geeklibrary.domain.model.work.Work;

public interface QueryWorksUseCase extends UseCase<QueryWorksCommand, List<Work>> {

}
