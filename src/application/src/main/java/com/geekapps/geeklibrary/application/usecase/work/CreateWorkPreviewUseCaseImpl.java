package com.geekapps.geeklibrary.application.usecase.work;

import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.model.CreateWorkPreviewCommand;
import com.geekapps.geeklibrary.application.port.in.work.CreateWorkPreviewUseCase;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.work.WorkPreviewPort;

@Service
class CreateWorkPreviewUseCaseImpl implements CreateWorkPreviewUseCase {

  private final WorkPreviewPort workPreviewPort;

  public CreateWorkPreviewUseCaseImpl(final WorkPreviewPort workPreviewPort) {
    this.workPreviewPort = workPreviewPort;
  }

  @Override
  public Work execute(final CreateWorkPreviewCommand input) {
    return this.workPreviewPort.previewWork(input.url());
  }

}
