package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.PersonEntity;

interface PersonJpaRepository extends JpaRepository<PersonEntity, UUID> {

}
