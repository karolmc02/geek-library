package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.port.out.PersonRepository;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper.PersonEntityMapper;

@Repository
public class PersonRepositoryAdapter implements PersonRepository {

  private final PersonJpaRepository personJpaRepository;

  private final PersonEntityMapper personEntityMapper;

  public PersonRepositoryAdapter(final PersonJpaRepository personJpaRepository,
      final PersonEntityMapper personEntityMapper) {
    this.personJpaRepository = personJpaRepository;
    this.personEntityMapper = personEntityMapper;
  }

  @Override
  public Person findById(final UUID id) {
    return this.personJpaRepository.findById(id).map(this.personEntityMapper::toDomain)
        .orElse(null);
  }

  @Override
  public Person save(final Person person) {
    final var personEntity = this.personEntityMapper.toEntity(person);
    final var savedEntity = this.personJpaRepository.save(personEntity);
    return this.personEntityMapper.toDomain(savedEntity);
  }

  @Override
  public Person findOrCreate(final UUID id, final String firstName, final String lastName) {
    if (id == null && firstName == null && lastName == null) {
      return null;
    }

    if (id != null) {
      final var existingPerson = this.findById(id);
      if (existingPerson != null) {
        return existingPerson;
      }
    }

    final var newPerson = new Person(firstName, lastName);
    return this.save(newPerson);
  }

}
