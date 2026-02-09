package com.geekapps.geeklibrary.domain.service;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.exception.EntityNotFoundException;
import com.geekapps.geeklibrary.domain.port.out.edition.EditionRepository;

public class EditionValidator {

  private final EditionRepository editionRepository;

  public EditionValidator(final EditionRepository editionRepository) {
    this.editionRepository = editionRepository;
  }

  public void validateEditionExists(final UUID editionId, final UUID workId) {
    final var edition = this.editionRepository.findById(editionId, workId);
    if (edition == null) {
      throw new EntityNotFoundException("Edition", editionId);
    }
  }

}
