package com.geekapps.geeklibrary.domain.model.work;

import java.util.UUID;

import com.geekapps.geeklibrary.domain.model.common.Person;

public class LightNovel extends Work {

  public LightNovel(UUID id, String title, Person autor, Person illustrator, String description) {
    super(id, title, autor, illustrator, description);
  }

}
