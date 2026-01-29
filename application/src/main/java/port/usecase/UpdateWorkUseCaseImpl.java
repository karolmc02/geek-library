package port.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.domain.model.work.ArtBook;
import com.geekapps.geeklibrary.domain.model.work.LightNovel;
import com.geekapps.geeklibrary.domain.model.work.Manga;
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

    final var updatedWork = this.createWorkFromCommand(input);
    return this.workRepository.save(updatedWork);
  }

  private Work createWorkFromCommand(final UpdateWorkCommand command) {
    return switch (command.type()) {
      case MANGA -> new Manga(command.id(), command.title(), command.description(),
          command.author(), command.illustrator());
      case ARTBOOK -> new ArtBook(command.id(), command.title(), command.description(),
          command.author(), command.illustrator());
      case LIGHT_NOVEL -> new LightNovel(command.id(), command.title(), command.description(),
          command.author(), command.illustrator());
    };
  }

}
