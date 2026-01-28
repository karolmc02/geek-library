package com.geekapps.geeklibrary.domain.model.work;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.annotation.Default;
import com.geekapps.geeklibrary.domain.model.common.Person;

public class Manga extends Work {

  @Default
  public Manga(final UUID id, final String title, final String description, final Person author,
      final Person illustrator) {
    super(id, title, description, author, illustrator);
  }

  public Manga(final String title, final String description, final Person author,
      final Person illustrator) {
    super(title, description, author, illustrator);
  }
}
