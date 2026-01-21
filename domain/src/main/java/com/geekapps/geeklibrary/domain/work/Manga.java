package com.geekapps.geeklibrary.domain.work;

import java.util.UUID;

import com.geekapps.geeklibrary.domain.common.Person;

public class Manga extends Work {
  public Manga(UUID id, String title, Person autor, Person illustrator, String description) {
    super(id, title, autor, illustrator, description);
  }

}
