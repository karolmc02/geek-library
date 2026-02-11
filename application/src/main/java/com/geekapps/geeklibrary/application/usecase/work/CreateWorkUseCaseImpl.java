package com.geekapps.geeklibrary.application.usecase.work;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.model.CreateWorkCommand;
import com.geekapps.geeklibrary.application.port.in.work.CreateWorkUseCase;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;

@Service
class CreateWorkUseCaseImpl implements CreateWorkUseCase {

  private final WorkRepository workRepository;

  public CreateWorkUseCaseImpl(final WorkRepository workRepository) {
    this.workRepository = workRepository;
  }

  @Override
  public Work execute(final CreateWorkCommand input) {
    final var author =
        new Person(input.author().id(), input.author().firstName(), input.author().lastName());
    final var illustrator = input.illustrator() != null ? new Person(input.illustrator().id(),
        input.illustrator().firstName(), input.illustrator().lastName()) : null;

    final var work =
        new Work(input.type(), input.title(), input.description(), author, illustrator);
    return this.workRepository.save(work);
  }

}
