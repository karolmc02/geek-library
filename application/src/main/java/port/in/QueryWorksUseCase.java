package port.in;

import java.util.List;
import com.geekapps.geeklibrary.domain.model.work.Work;
import port.common.UseCase;
import port.in.model.QueryWorksCommand;

public interface QueryWorksUseCase extends UseCase<QueryWorksCommand, List<Work>> {

}
