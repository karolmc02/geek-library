package com.geekapps.geeklibrary.application.usecase.work;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.model.UpdateWorkCommand;
import com.geekapps.geeklibrary.application.port.in.work.UpdateWorkUseCase;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;

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
      throw new EntityNotFoundException("Work", input.id());
    }

    final var author =
        new Person(input.author().id(), input.author().firstName(), input.author().lastName());
    final var illustrator = input.illustrator() != null ? new Person(input.illustrator().id(),
        input.illustrator().firstName(), input.illustrator().lastName()) : null;

    final var updatedWork =
        new Work(input.id(), input.type(), input.title(), input.description(), author, illustrator);
    return this.workRepository.save(updatedWork);
  }

}
