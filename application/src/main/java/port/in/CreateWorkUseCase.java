package port.in;

import com.geekapps.geeklibrary.domain.model.work.Work;
import port.common.UseCase;
import port.in.model.CreateWorkCommand;

public interface CreateWorkUseCase extends UseCase<CreateWorkCommand, Work> {

}
