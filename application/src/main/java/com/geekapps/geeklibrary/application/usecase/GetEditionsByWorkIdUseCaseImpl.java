package com.geekapps.geeklibrary.application.usecase;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.edition.GetEditionsByWorkIdUseCase;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.model.edition.Edition;
import com.geekapps.geeklibrary.domain.port.out.edition.EditionRepository;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;

@Service
class GetEditionsByWorkIdUseCaseImpl implements GetEditionsByWorkIdUseCase {

  private final EditionRepository editionRepository;
  private final WorkRepository workRepository;

  public GetEditionsByWorkIdUseCaseImpl(final EditionRepository editionRepository,
      final WorkRepository workRepository) {
    this.editionRepository = editionRepository;
    this.workRepository = workRepository;
  }

  @Override
  public List<Edition> execute(final UUID workId) {
    final var work = this.workRepository.findById(workId);
    if (work == null) {
      throw new EntityNotFoundException("Work", workId);
    }

    return this.editionRepository.findByWorkId(workId);
  }

}
