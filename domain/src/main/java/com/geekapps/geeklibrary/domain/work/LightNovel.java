package com.geekapps.geeklibrary.domain.work;

import java.util.UUID;

import com.geekapps.geeklibrary.domain.common.Person;

public class LightNovel extends Work {

  public LightNovel(UUID id, String title, Person autor, Person illustrator, String description) {
    super(id, title, autor, illustrator, description);
  }

}
