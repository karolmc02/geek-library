package com.geekapps.geeklibrary.domain.port.out.edition;

import java.util.List;
import java.util.UUID;
import com.geekapps.geeklibrary.domain.model.edition.Edition;

public interface EditionRepository {

  Edition save(Edition edition, UUID workId);

  List<Edition> findByWorkId(UUID workId);

  Edition findById(UUID editionId, UUID workId);

  void deleteById(UUID editionId, UUID workId);

}
