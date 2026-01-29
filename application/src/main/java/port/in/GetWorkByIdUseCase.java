package port.in;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.model.work.Work;
import port.common.UseCase;

public interface GetWorkByIdUseCase extends UseCase<UUID, Work> {

}
