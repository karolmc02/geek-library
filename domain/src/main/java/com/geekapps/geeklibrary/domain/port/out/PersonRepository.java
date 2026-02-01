package com.geekapps.geeklibrary.domain.port.out;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.model.common.Person;

public interface PersonRepository {

  Person findById(UUID id);

  Person save(Person person);

}
