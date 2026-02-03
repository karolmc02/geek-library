package port.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.PersonRepository;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;
import port.in.CreateWorkUseCase;
import port.in.model.CreateWorkCommand;
import port.in.model.PersonTO;

@Service
class CreateWorkUseCaseImpl implements CreateWorkUseCase {

  private final WorkRepository workRepository;
  private final PersonRepository personRepository;

  public CreateWorkUseCaseImpl(final WorkRepository workRepository,
      final PersonRepository personRepository) {
    this.workRepository = workRepository;
    this.personRepository = personRepository;
  }

  @Override
  public Work execute(final CreateWorkCommand input) {
    final var author = this.findOrCreatePerson(input.author());
    final var illustrator =
        input.illustrator() != null ? this.findOrCreatePerson(input.illustrator()) : null;

    final var work =
        new Work(input.type(), input.title(), input.description(), author, illustrator);
    return this.workRepository.save(work);
  }

  private Person findOrCreatePerson(final PersonTO personCommand) {
    if (personCommand == null) {
      return null;
    }
    return this.personRepository.findOrCreate(personCommand.id(), personCommand.firstName(),
        personCommand.lastName());
  }

}
