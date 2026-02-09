package com.geekapps.geeklibrary.application.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.model.UpdateWorkCommand;
import com.geekapps.geeklibrary.application.port.in.model.to.PersonTO;
import com.geekapps.geeklibrary.application.port.in.work.UpdateWorkUseCase;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.person.PersonRepository;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;

@Service
public class UpdateWorkUseCaseImpl implements UpdateWorkUseCase {

  private final WorkRepository workRepository;
  private final PersonRepository personRepository;

  public UpdateWorkUseCaseImpl(final WorkRepository workRepository,
      final PersonRepository personRepository) {
    this.workRepository = workRepository;
    this.personRepository = personRepository;
  }

  @Override
  public Work execute(final UpdateWorkCommand input) {
    final var existingWork = this.workRepository.findById(input.id());
    if (existingWork == null) {
      throw new EntityNotFoundException("Work", input.id());
    }

    final var author = this.findOrCreatePerson(input.author());
    final var illustrator =
        input.illustrator() != null ? this.findOrCreatePerson(input.illustrator()) : null;

    final var updatedWork =
        new Work(input.id(), input.type(), input.title(), input.description(), author, illustrator);
    return this.workRepository.save(updatedWork);
  }

  private Person findOrCreatePerson(final PersonTO personCommand) {
    if (personCommand == null) {
      return null;
    }
    return this.personRepository.findOrCreate(personCommand.id(), personCommand.firstName(),
        personCommand.lastName());
  }

}
