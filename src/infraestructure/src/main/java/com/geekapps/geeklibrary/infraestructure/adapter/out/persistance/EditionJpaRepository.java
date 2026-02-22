package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.EditionEntity;

interface EditionJpaRepository extends JpaRepository<EditionEntity, UUID> {

  List<EditionEntity> findByWorkId(UUID workId);

  Optional<EditionEntity> findByIdAndWorkId(UUID id, UUID workId);

  void deleteByIdAndWorkId(UUID id, UUID workId);

}
