package com.geekapps.geeklibrary.application.usecase;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.model.CreateWorkPreviewCommand;
import com.geekapps.geeklibrary.application.port.in.work.CreateWorkPreviewUseCase;
import com.geekapps.geeklibrary.domain.model.work.Work;

@Service
class CreateWorkPreviewUseCaseImpl implements CreateWorkPreviewUseCase {

  @Override
  public Work execute(final CreateWorkPreviewCommand input) {
    // TODO: Implementar lógica de extracción y preview desde URL
    throw new UnsupportedOperationException("Not implemented yet");
  }

}
