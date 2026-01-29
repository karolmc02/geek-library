package port.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;
import port.in.UpdateWorkUseCase;
import port.in.model.UpdateWorkCommand;

@Service
public class UpdateWorkUseCaseImpl implements UpdateWorkUseCase {

  private final WorkRepository workRepository;

  public UpdateWorkUseCaseImpl(final WorkRepository workRepository) {
    this.workRepository = workRepository;
  }

  @Override
  public Work execute(final UpdateWorkCommand input) {
    final var existingWork = this.workRepository.findById(input.id());
    if (existingWork == null) {
      return null;
    }

    final var updatedWork = new Work(input.id(), input.type(), input.title(), input.description(),
        input.author(), input.illustrator());
    return this.workRepository.save(updatedWork);
  }

}
