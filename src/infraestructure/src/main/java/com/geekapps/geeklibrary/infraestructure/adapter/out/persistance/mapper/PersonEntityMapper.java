package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper;

import org.mapstruct.Mapper;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.PersonEntity;

@Mapper
public interface PersonEntityMapper {

  Person toDomain(PersonEntity personEntity);

  PersonEntity toEntity(Person person);

}
