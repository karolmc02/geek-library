package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.VolumeEntity;

interface VolumeJpaRepository extends JpaRepository<VolumeEntity, UUID> {

  List<VolumeEntity> findByEditionId(UUID editionId);

  Optional<VolumeEntity> findByIdAndEditionId(UUID id, UUID editionId);

  void deleteByIdAndEditionId(UUID id, UUID editionId);

}
