package com.geekapps.geeklibrary.application.usecase;

import java.util.UUID;
import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.work.DeleteWorkByIdUseCase;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;

@Service
public class DeleteWorkByIdUseCaseImpl implements DeleteWorkByIdUseCase {

  private final WorkRepository workRepository;

  public DeleteWorkByIdUseCaseImpl(final WorkRepository workRepository) {
    this.workRepository = workRepository;
  }

  @Override
  public Void execute(final UUID id) {
    this.workRepository.deleteById(id);
    return null;
  }

}
