package port.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.domain.model.work.ArtBook;
import com.geekapps.geeklibrary.domain.model.work.LightNovel;
import com.geekapps.geeklibrary.domain.model.work.Manga;
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
    return this.workRepository.save(this.createWorkFromCommand(input));
  }

  private Work createWorkFromCommand(final CreateWorkCommand command) {
    return switch (command.type()) {
      case MANGA -> new Manga(command.title(), command.description(), command.author(),
          command.illustrator());
      case ARTBOOK -> new ArtBook(command.title(), command.description(), command.author(),
          command.illustrator());
      case LIGHT_NOVEL -> new LightNovel(command.title(), command.description(), command.author(),
          command.illustrator());
    };
  }

}
