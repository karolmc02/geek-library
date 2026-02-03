package port.usecase;

import java.util.UUID;
import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;
import port.in.GetWorkByIdUseCase;

@Service
public class GetWorkByIdUseCaseImpl implements GetWorkByIdUseCase {

  private final WorkRepository workRepository;

  public GetWorkByIdUseCaseImpl(final WorkRepository workRepository) {
    this.workRepository = workRepository;
  }

  @Override
  public Work execute(final UUID id) {
    final var work = this.workRepository.findById(id);
    if (work == null) {
      throw new EntityNotFoundException("Work", id);
    }
    return work;
  }

}
