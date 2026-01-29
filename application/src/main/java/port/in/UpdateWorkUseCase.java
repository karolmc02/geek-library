package port.in;

import com.geekapps.geeklibrary.domain.model.work.Work;
import port.common.UseCase;
import port.in.model.UpdateWorkCommand;

public interface UpdateWorkUseCase extends UseCase<UpdateWorkCommand, Work> {

}
