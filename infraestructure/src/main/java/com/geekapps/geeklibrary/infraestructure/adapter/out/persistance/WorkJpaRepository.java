package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.WorkEntity;

interface WorkJpaRepository extends JpaRepository<WorkEntity, UUID> {

}
