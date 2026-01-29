package port.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;
import port.in.CreateWorkUseCase;
import port.in.model.CreateWorkCommand;

@Service
class CreateWorkUseCaseImpl implements CreateWorkUseCase {

  private final WorkRepository workRepository;

  public CreateWorkUseCaseImpl(final WorkRepository workRepository) {
    this.workRepository = workRepository;
  }

  @Override
  public Work execute(final CreateWorkCommand input) {
    final var work = new Work(input.type(), input.title(), input.description(), input.author(),
        input.illustrator());
    return this.workRepository.save(work);
  }

}
